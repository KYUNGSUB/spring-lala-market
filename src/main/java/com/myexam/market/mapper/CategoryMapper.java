package com.myexam.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myexam.market.domain.CategoryVO;
import com.myexam.market.domain.MaxCount;
import com.myexam.market.domain.OptionInfo;

public interface CategoryMapper {
	// 1차 카테고리 정보 목록을 가져온다.
	List<CategoryVO> getListFirst();
	// 2차 카테고리 정보를 가져온다.
	List<CategoryVO> getListSecond(String code);
	// 카테고리 코드로 카테고리 정보를 가져온다.
	CategoryVO selectByCode(String code);
	// 카테고리 정보를 추가
	int insert(CategoryVO category);
	// 게시 순서(seq)를 사용하여 1차 카테고리 정보를 가져온다.
	CategoryVO getFirstCategoryBySeq(int seq);
	// 카테고리의 게시 순서를 변경
	int updateCategorySeq(@Param("code") String code, @Param("seq") int seq);
	// 게시글 순서(seq)를 사용하여 2차 카테고리 정보를 가져온다.
	CategoryVO getSecondCategoryBySeq(@Param("parent") String parent, @Param("seq") int seq);
	// 카테고리의 노출여부를 변경
	int updateCategory1(@Param("code") String code, @Param("expose") boolean expose);
	// 카테고리의 노출여부와 GNB 메뉴 노출여부를 변경
	int updateCategory2(@Param("code") String code, @Param("expose") boolean expose, @Param("gnb") boolean gnb);
	// 카테고리 정보를 삭제
	int deleteCategory(String code);
	// 지정 카테고리 코드에 대한 노출여부 정보를 가져온다.
	OptionInfo getOptionInfo(String code);
	// 카테고리 삭제에 따라 영향을 주는 다른 카테고리 순서를 변경
	int updateSequence1(int seq);
	int updateSequence2(@Param("parent") String parent, @Param("seq") int seq);
	// 1차 카테고리에 속하는 2차 카테고리들을 삭제
	int deleteSecondCategory(String parent);
	// 카테고리의 배치 순서를 가져온다.
	int getSeq1(int step);
	int getSeq2(@Param("step") int step, @Param("parent") String parent);
	// 2차 카테고리 코드를 생성하기 위하여 필요한 현재 카테고리들의 최대값, 갯수를 구한다. 
	MaxCount getMaxCount(String parent);
	// 2차 카테고리 코드를 생성하기 위하여 필요한 현재 카테고리들의 코드값을 구한다.
	String[] getCodes(@Param("parent") String parent, @Param("count") int count);
}