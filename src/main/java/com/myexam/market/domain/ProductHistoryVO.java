package com.myexam.market.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductHistoryVO {
	public static final int REGISTER = 1;
	public static final int MODIFY = 2;
	public static final int REMOVE = 3;
	
	private Long hid;
	private int item;	// 1:상품등록, 2:상품수정, 3:상품삭제
	private Date timeAt;
	private String userid;
	private Long pid;
}