package com.myexam.market.service;

import java.util.List;

import com.myexam.market.domain.TermsVO;

public interface TermsService {
	List<TermsVO> getList();
	void add(TermsVO terms);
	boolean modify(TermsVO terms);
	boolean remove(Long tid);
}