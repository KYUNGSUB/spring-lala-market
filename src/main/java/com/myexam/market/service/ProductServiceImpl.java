package com.myexam.market.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myexam.market.domain.BoardAttachVO;
import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.ProductHistoryVO;
import com.myexam.market.domain.ProductInfoVO;
import com.myexam.market.domain.ProductOptionVO;
import com.myexam.market.domain.ProductVO;
import com.myexam.market.domain.UploadProductForm;
import com.myexam.market.mapper.BoardAttachMapper;
import com.myexam.market.mapper.ProductHistoryMapper;
import com.myexam.market.mapper.ProductInfoMapper;
import com.myexam.market.mapper.ProductMapper;
import com.myexam.market.mapper.ProductOptionMapper;
import com.myexam.market.utils.Common;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Service
@Log4j
public class ProductServiceImpl implements ProductService {
	private static final int PRODUCT_REGISTRATION = 1;
	private static final int PRODUCT_UPDATE = 2;
	private static final int PC_LIST_TYPE = 2;
	private static final int MOBILE_EXPOSE_TYPE = 7;
	
	@Setter(onMethod_ = @Autowired)
	private ProductMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ProductHistoryMapper historyMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ProductOptionMapper optionMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ProductInfoMapper infoMapper;

	private File uploadPath;
	private String uploadFolderPath;

	@Transactional
	@Override
	public void register(UploadProductForm form) {
		ProductVO product = form.toProductVO();
		log.info("product..." + product);
		mapper.insertSelectKey(product);
		form.setPid(product.getPid());
		
		// 상품 이력 관리 : 등록 추가
		ProductHistoryVO history = new ProductHistoryVO();
		history.setItem(PRODUCT_REGISTRATION);
		history.setUserid(form.getUserid());
		history.setPid(product.getPid());
		historyMapper.insert(history);

		List<ProductInfoVO> infoList = form.toProductInfo();
		for(ProductInfoVO info : infoList) {
			infoMapper.insert(info);
		}
		
		List<ProductOptionVO> optionList = form.toProductOption();
		for(ProductOptionVO option : optionList) {
			optionMapper.insert(option);
		}
		storeImageFiles(form);
	}

	private void storeImageFiles(UploadProductForm form) {
		String uploadFolder = Common.UPLOAD_PATH;
		uploadFolderPath = Common.getFolder();

		// 업로드 폴더 생성
		uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		if (uploadPath.exists() == false) { // 폴더가 존재하지 않을 때만 생성
			uploadPath.mkdirs(); // 중간 경로에 없는 폴더가 있을 경우, 그것까지도 생성해 준다.
		} // make yyyy/MM/dd folder
		
		// MultipartFile pclist 저장
		for(int i = 0;i < form.getImgList().size();i++) {
			MultipartFile imgFile = form.getImgList().get(i);
			if(imgFile.getSize() > 0) {
				BoardAttachVO attach = storeImageFileAndTable(imgFile);
				attach.setKind("product");
				attach.setBno(form.getPid());
				attach.setFileType(getFileType(i));
				attachMapper.insert(attach);
			}
		}
	}
	
	private String getFileType(int i) {
		if(i == 0) {
			return Common.PC_LIST_TYPE;
		} else if(i >= 1 && i <= 4) {
			return Common.PC_MAIN_TYPE;
		} else if(i == 5) {
			return Common.PC_EXPOSE_TYPE;
		} else if(i == 6) {
			return Common.MOBILE_LIST_TYPE;
		} else if(i >= 7 && i <= 10) {
			return Common.MOBILE_MAIN_TYPE;
		} else {
			return Common.MOBILE_EXPOSE_TYPE;
		}
	}

	private BoardAttachVO storeImageFileAndTable(MultipartFile multipartFile) {
		BoardAttachVO attach = new BoardAttachVO();
		log.info("------------------------------");
		log.info("upload file name: " + multipartFile.getOriginalFilename());
		log.info("upload File Size: " + multipartFile.getSize());
		
		String uploadFileName = multipartFile.getOriginalFilename();
		// IE has file path -> 경로 자르기 (전체 경로 중에 파일 이름만 잘라낸다.)
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
		log.info("only file name: " + uploadFileName);
		
		attach.setFileName(uploadFileName);
		// 파일이름 중복 방지
		// 원래 파일 이름도 보존할 수 있다.
		UUID uuid = UUID.randomUUID();
		uploadFileName = uuid.toString() + "_" + uploadFileName;

		try {
			File saveFile = new File(uploadPath, uploadFileName);
			multipartFile.transferTo(saveFile);
			attach.setUuid(uuid.toString());
			attach.setUploadPath(uploadFolderPath);
			// 이미지 파일 유형인지 검사
			if (Common.checkImageType(saveFile)) {
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
				Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return attach;
	}

	@Override
	public List<ProductVO> getList(Criteria cri) {
		List<ProductVO> list = mapper.getList(cri);
		for(ProductVO product: list) {
			List<BoardAttachVO> attachList = attachMapper.findByBno("product", product.getPid());
			List<BoardAttachVO> pcMainList = new ArrayList<>();
			List<BoardAttachVO> mobileMainList = new ArrayList<>();
			for(BoardAttachVO attach : attachList) {
				if(attach.getFileType().equals("2")) {
					product.setPc_list(attach);
				} else if(attach.getFileType().equals("3")) {
					pcMainList.add(attach);
				} else if(attach.getFileType().equals("4")) {
					product.setPc_expose(attach);
				} else if(attach.getFileType().equals("5")) {
					product.setMobile_list(attach);
				} else if(attach.getFileType().equals("6")) {
					mobileMainList.add(attach);
				} else {
					product.setMobile_expose(attach);
				}
			}
			product.setPc_main(pcMainList);
			product.setMobile_main(mobileMainList);
		}
		return list;
	}

	@Override
	public int getTotal() {
		return mapper.selectCount();
	}

	@Override
	public int getCriteriaTotal(Criteria cri) {
		return mapper.selectCriteriaCount(cri);
	}
}