package com.myexam.market.mapper;

import java.util.List;

import com.myexam.market.domain.PolicyVO;

public interface PolicyMapper {
	List<PolicyVO> getList();
	int removeAll(int code);
	int add(PolicyVO policy);
	PolicyVO getPolicy(int code);
	int update(PolicyVO newPolicy);
	String getValue(String category, String name);
}