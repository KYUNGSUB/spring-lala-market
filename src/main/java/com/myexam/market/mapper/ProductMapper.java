package com.myexam.market.mapper;

import java.util.List;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.ProductVO;

public interface ProductMapper {
	void insert(ProductVO product);
	void insertSelectKey(ProductVO product);
	List<ProductVO> getList(Criteria cri);
	int selectCount();
	int selectCriteriaCount(Criteria cri);
}