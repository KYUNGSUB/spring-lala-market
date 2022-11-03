package com.myexam.market.service;

import java.util.List;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.NoticeVO;

public interface NoticeService {
	List<NoticeVO> getList(Criteria cri);
	int getCriteriaTotal(Criteria cri);
}