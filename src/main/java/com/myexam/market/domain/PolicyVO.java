package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PolicyVO {
	private int code;
	private String category;
	private String name;
	private String value;
	private Date createdAt;
	private Date modifiedAt;
}