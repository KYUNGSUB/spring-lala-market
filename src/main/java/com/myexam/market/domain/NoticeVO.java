package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeVO {
	private Long nid;
	private String title;
	private String content;
	private boolean important;
	private Date regDate;
	private Date modDate;
}
