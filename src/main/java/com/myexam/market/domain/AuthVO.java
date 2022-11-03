package com.myexam.market.domain;

import lombok.Data;

// tbl_member_auth 테이블의 정보를 저장하는 VO

@Data
public class AuthVO {
	private String userid;
	private String auth; 
}