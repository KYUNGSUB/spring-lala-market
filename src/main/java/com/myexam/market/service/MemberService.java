package com.myexam.market.service;

import com.myexam.market.domain.MemberVO;

public interface MemberService {
	String idCheck(String userid);
	void join(MemberVO member);
}