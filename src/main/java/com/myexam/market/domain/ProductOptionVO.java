package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductOptionVO {
	private Long po_id;
	private int gid;
	private String name;
	private String description;
	private int price;
	private Long pid;
	private Date createdAt;
	private Date modifiedAt;
}