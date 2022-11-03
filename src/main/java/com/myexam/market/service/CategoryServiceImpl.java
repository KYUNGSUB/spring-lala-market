package com.myexam.market.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myexam.market.domain.CategoryVO;
import com.myexam.market.domain.MaxCount;
import com.myexam.market.domain.MoveRequest;
import com.myexam.market.domain.OptionInfo;
import com.myexam.market.mapper.CategoryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {	// 싱글톤으로 동작
	private CategoryMapper mapper;
	
	@Transactional
	@Override
	public List<CategoryVO> getList() {	// 카테고리 목록을 데이터베이스로부터 가져온다.
		List<CategoryVO> list = mapper.getListFirst();	// 1차 카테고리 목록을 가져온다.
		for(CategoryVO category : list) {	// 2차 카테고리 정보를 가져온다.
			category.setList(mapper.getListSecond(category.getCode()));
		}
		return list;
	}
	
	@Override
	public List<CategoryVO> getSecondList(String parent) {	// 카테고리 목록을 데이터베이스로부터 가져온다.
		return mapper.getListSecond(parent);	// 2차 카테고리 목록을 가져온다.
	}
	
	@Override
	public String codeCheck(String code) {
		CategoryVO category = null;
		category = mapper.selectByCode(code);
		if(category != null) {
			return "using";
		} else {
			return "not using";
		}
	}
	
	@Transactional
	@Override
	public int add(CategoryVO category) {
		int result = -1;
		if(category.getParent() == null) {
			category.setSeq(mapper.getSeq1(category.getStep()) + 1);
		} else {
			category.setSeq(mapper.getSeq2(category.getStep(), category.getParent()) + 1);
		}
		
		result = mapper.insert(category);
		return result;
	}
	
	@Transactional
	@Override
	public int moveCatgory(MoveRequest mr) {
		int result = -1;
		int direction;	// 배치순서(seq)값을 1 증가(down), 1 감소(-1, up) 나타낸다.
		if(mr.getDirection().equals("up")) {	// upward
			direction = -1;	// up
		} else {
			direction = 1;	// down
		}
		CategoryVO other = null;	// 이동할 상대 카테고리
		if(mr.getStep() == 1) {	// 1차 카테고리
			other = mapper.getFirstCategoryBySeq(mr.getSeq() + direction);
		} else {				// 2차 카테고리
			other = mapper.getSecondCategoryBySeq(mr.getParent(), mr.getSeq() + direction);
		}
		// 현재 카테고리의 배치 순서를 변경
		result = mapper.updateCategorySeq(mr.getCode(), mr.getSeq() + direction);
		// 상대 카테고리의 배치 순서를 변경
		result = mapper.updateCategorySeq(other.getCode(), mr.getSeq());
		return result;
	}
	
	// 2차 카테고리 정보 변경 : 노출 여부(expose)
	@Override
	public int modify(String code, boolean expose) {
		return mapper.updateCategory1(code, expose);
	}

	// 1차 카테고리 정보 변경 : 노출 여부(expose), GNB 표시 여부(gnb)
	@Override
	public int modify(String code, boolean expose, boolean gnb) {
		return mapper.updateCategory2(code, expose, gnb);
	}
	
	// code값을 가지는 상품 카테고리 정보를 삭제한다.
	@Transactional
	@Override
	public int remove(String code) {
		int result = -1;
		// 카테고리 정보를 검색한다.
		CategoryVO category = mapper.selectByCode(code);	// 카테고리 정보 가져오기
		// 지정 카테고리 정보를 삭제
		result = mapper.deleteCategory(code);			// 카테고리 삭제
		// 영향을 받은 카테고리에 대하여 seq 갱신
		if(category.getParent() == null) {
			result = mapper.updateSequence1(category.getSeq());
		} else {
			result = mapper.updateSequence2(category.getParent(), category.getSeq());
		}
		
		// 삭제하는 카테고리가 1차 카테고리이면, 산하의 2차 카테고리들도 삭제
		if(category.getStep() == 1) {	// 1차 카테고리면, 산하 2차 카테고리를 삭제
			result = mapper.deleteSecondCategory(category.getCode());
		}
		return result;
	}
	
	// 카테고리 노출 여부(expose)와 GNB 노출 여부(gnb)를 데이터베이스로부터 가져온다.
	@Override
	public OptionInfo getOptionInfo(String code) {
		return mapper.getOptionInfo(code);
	}
	
	@Transactional
	@Override
	public int getSeed(String parent) {
		MaxCount mc = mapper.getMaxCount(parent);
		if(mc.getV1() == mc.getV2()) {
			return mc.getV1() + 1;
		}
		String[] codes = mapper.getCodes(parent, mc.getV2());
		int[] codesNum = new int[mc.getV2()];
		for(int i = 0;i < codes.length;i++) {
			codesNum[i] = Integer.parseInt(codes[i]);
		}
		Arrays.sort(codesNum);	// 오름차순으로 정렬
		for(int i = 0;i < codes.length;i++) {
			if((i+1) == codesNum[i])
				continue;
			return i + 1;
		}
		return -1;
	}
}