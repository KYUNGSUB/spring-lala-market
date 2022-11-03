package com.myexam.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.NoticeVO;
import com.myexam.market.mapper.NoticeMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class NoticeServiceImpl implements NoticeService {
	private NoticeMapper mapper;

	@Override
	public List<NoticeVO> getList(Criteria cri) {
		log.info("getList...");
		
		return mapper.getList(cri);
	}

	@Override
	public int getCriteriaTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
}