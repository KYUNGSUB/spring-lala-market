package com.myexam.market.service;

import java.util.List;

import com.myexam.market.domain.CategoryVO;
import com.myexam.market.domain.MoveRequest;
import com.myexam.market.domain.OptionInfo;

public interface CategoryService {
	List<CategoryVO> getList();			// 카테고리 목록 정보 가져오기
	String codeCheck(String code);		// 카테고리 코드 중복 확인
	int add(CategoryVO category);			// 카테고리 추가
	int moveCatgory(MoveRequest mr);	// 카테고리 전시 순서 변경
	int modify(String code, boolean expose);	// 카테고리 노출여부 수정
	int modify(String code, boolean expose, boolean gnb);	// 카테고리 노출여부 수정
	int remove(String code);			// 카테고리 삭제
	OptionInfo getOptionInfo(String code);	// 카테고리 노출여부 정보 가져오기
	int getSeed(String parent);			// 2차 카테고리 코드를 생성하기 위한 값 가져오기
	List<CategoryVO> getSecondList(String parent);	// 2차 카테고리 목록 가져오기
}