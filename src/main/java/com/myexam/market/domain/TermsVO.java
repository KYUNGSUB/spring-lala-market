package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TermsVO {
	private Long tid;
	private String title;
	private String content;
	private boolean expose;
	private boolean mandatory;
	private Date createdAt;
	private Date modifiedAt;
}