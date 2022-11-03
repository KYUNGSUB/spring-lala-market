package com.myexam.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myexam.market.domain.TermsVO;
import com.myexam.market.mapper.TermsMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TermsServiceImpl implements TermsService {
	private TermsMapper mapper;

	@Override
	public List<TermsVO> getList() {
		return mapper.getList();
	}

	@Override
	public void add(TermsVO terms) {
		mapper.insertSelectKey(terms);
	}

	@Override
	public boolean modify(TermsVO terms) {
		return mapper.update(terms) == 1;
	}

	@Override
	public boolean remove(Long tid) {
		return mapper.delete(tid) == 1;
	}
}