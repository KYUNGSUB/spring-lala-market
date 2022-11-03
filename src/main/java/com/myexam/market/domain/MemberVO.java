package com.myexam.market.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

// tbl_member에 대한 정보를 저장하는 VO

@Data
public class MemberVO {
	private String userid;
	private String userpw;
	private String username;
	private boolean enabled;
	private String mobile;
	private String email;
	private String marketing;
	private String rcv;
	private int grade;
	private int visited;
	private Long pursuit;
	private Long accum;

	private Date regDate;
	private Date updateDate;
	private List<AuthVO> authList;
}