package com.myexam.market.mapper;

import java.util.List;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.NoticeVO;

public interface NoticeMapper {
	List<NoticeVO> getList(Criteria cri);
	int getTotal(Criteria cri);
}