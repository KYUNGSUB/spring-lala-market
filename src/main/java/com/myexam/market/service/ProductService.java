package com.myexam.market.service;

import java.util.List;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.ProductVO;
import com.myexam.market.domain.UploadProductForm;

public interface ProductService {
	public void register(UploadProductForm form);
	public List<ProductVO> getList(Criteria cri);
	public int getTotal();
	public int getCriteriaTotal(Criteria cri);
}