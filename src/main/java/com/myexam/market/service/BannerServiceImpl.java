package com.myexam.market.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myexam.market.domain.BannerInfo;
import com.myexam.market.domain.BannerUploadForm;
import com.myexam.market.domain.BannerVO;
import com.myexam.market.domain.BoardAttachVO;
import com.myexam.market.mapper.BannerMapper;
import com.myexam.market.mapper.BoardAttachMapper;
import com.myexam.market.utils.Common;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Service
@Log4j
public class BannerServiceImpl implements BannerService {
	@Setter(onMethod_ = @Autowired)
	private BannerMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private AttachService attachService;

	private File uploadPath;
	private String uploadFolderPath;

	@Override
	public BannerVO getBanner(int kind, int position) {
		BannerVO banner = mapper.getBanner(kind, position);
		if(banner != null) {
			banner.setInfoList(mapper.getBannerInfoList(banner.getBid()));
			for(BannerInfo bannerInfo : banner.getInfoList()) {
				List<BoardAttachVO> list = attachMapper.findByBno("banner", new Long(bannerInfo.getInfo_id()));
				bannerInfo.setPds(list.get(0));
			}
		}
		return banner;
	}

	@Transactional
	@Override
	public BannerVO addBanner(BannerUploadForm form) {
		// banner 정보 처리
		BannerVO banner = form.toBanner();
		BannerVO exists = mapper.getBanner(banner.getKind(), banner.getPosition());
		if(exists == null) {	// 존재하지 않음(추가 필요)
			mapper.addBannerSelectKey(banner);
		} else if (banner.getLocation() != exists.getLocation()) {				// 기존에 존재함
			mapper.updateBanner(banner);
			banner.setBid(exists.getBid());
		} else {
			banner.setBid(exists.getBid());
		}
					
		// bannerInfo 정보 처리
		form.setBid(banner.getBid());
		BannerInfo bannerInfo = form.toBannerInfo();
		mapper.addBannerInfoSelectKey(bannerInfo);
		form.setInfo_id(bannerInfo.getInfo_id());
		storeImageFiles(form);
		return banner;
	}

	private void storeImageFiles(BannerUploadForm form) {
		String uploadFolder = Common.UPLOAD_PATH;
		uploadFolderPath = Common.getFolder();

		// 업로드 폴더 생성
		uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		if (uploadPath.exists() == false) { // 폴더가 존재하지 않을 때만 생성
			uploadPath.mkdirs(); // 중간 경로에 없는 폴더가 있을 경우, 그것까지도 생성해 준다.
		} // make yyyy/MM/dd folder
		
		// MultipartFile pclist 저장
		MultipartFile imgFile = form.getBannerImg();
		if(imgFile.getSize() > 0) {
			BoardAttachVO attach = storeImageFileAndTable(imgFile);
			attach.setKind("banner");
			attach.setBno(new Long(form.getInfo_id()));
			attach.setFileType("1");
			attachMapper.insert(attach);
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
	public BannerVO updateBanner(BannerUploadForm form) {
		// banner 정보 처리
		BannerVO banner = form.toBanner();
		BannerVO exists = mapper.getBanner(banner.getKind(), banner.getPosition());
		if(banner.getLocation() != exists.getLocation()) {
			mapper.updateBanner(banner);
		}
							
		// bannerInfo 정보 처리
		BannerInfo bannerInfo = form.toBannerInfo();
		mapper.updateBannerInfo(bannerInfo);
		updateImageFiles(form);
		return banner;
	}

	private void updateImageFiles(BannerUploadForm form) {
		MultipartFile imgFile = form.getBannerImg();
		if(imgFile.getSize() > 0) {	// 새로운 배너 이미지가 추가된 경우
			BoardAttachVO oldPds = attachMapper.findByBno("banner", new Long(form.getInfo_id())).get(0);
			attachMapper.delete(oldPds.getUuid());
			deleteImageFile(oldPds);
			storeImageFiles(form);	// 새로운 배너 이미지 추가
		}
	}

	private void deleteImageFile(BoardAttachVO oldPds) {
		String uploadFolder = Common.UPLOAD_PATH;
		String filePath = uploadFolder + File.separator + oldPds.getFileCallPath();
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
		filePath = uploadFolder + File.separator + oldPds.getThumbnailFileCallPath();
		file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}

	@Transactional
	@Override
	public void removeBannerInfo(int info_id) {
		BoardAttachVO oldPds = attachMapper.findByBno("banner", new Long(info_id)).get(0);
		attachMapper.delete(oldPds.getUuid());
		deleteImageFile(oldPds);
		mapper.deleteBannerInfo(info_id);
	}

	@Override
	public void deleteBanner(int bid) {
		mapper.deleteBanner(bid);
	}
}