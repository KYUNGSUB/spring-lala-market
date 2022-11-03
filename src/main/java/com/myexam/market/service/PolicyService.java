package com.myexam.market.service;

import java.util.List;

import com.myexam.market.domain.PolicyUpdateForm;
import com.myexam.market.domain.PolicyVO;

public interface PolicyService {
	String getValue(String category, String name);	// 정책 파라미터 값을 검색
	List<PolicyVO> getList();
	void modify(PolicyUpdateForm form);
}