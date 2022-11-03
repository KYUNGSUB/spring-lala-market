package com.myexam.market.mapper;

import java.util.List;

import com.myexam.market.domain.TermsVO;

public interface TermsMapper {
	List<TermsVO> getList();
	void insert(TermsVO terms);
	int update(TermsVO terms);
	void insertSelectKey(TermsVO terms);
	int delete(Long tid);
}