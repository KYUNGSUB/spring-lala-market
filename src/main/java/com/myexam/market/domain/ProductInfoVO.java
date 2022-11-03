package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductInfoVO {
	private int pi_id;
	private String name;
	private String description;
	private Long pid;
	private Date createdAt;
	private Date modifiedAt;
}