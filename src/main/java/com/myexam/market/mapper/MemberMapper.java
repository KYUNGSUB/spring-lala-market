package com.myexam.market.mapper;

import com.myexam.market.domain.MemberVO;

// tbl_member 테이블을 억세스하는 Mapper 메소드를 정의

public interface MemberMapper {
	// 해당 userid에 대한 회원정보를 가져온다.
	public MemberVO read(String userid);
	public void insert(MemberVO member);   // 추가됨
	public String idCheck(String userid);	// 아이디 중복 체크
}