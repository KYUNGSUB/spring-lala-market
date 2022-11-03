package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BannerInfo {
	private int info_id;
	private int bid;
	private String url;
	private String alt;
	private String target;
	private int loginBefore;	// default(0), 로그인 전(before:1), 로그인 후(after:2)
	private Date createdAt;
	private Date modifiedAt;
	private BoardAttachVO pds;
}