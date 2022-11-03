package com.myexam.market.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductVO {
	private Long pid;
	private String category1;
	private String category2;
	private String name;
	private int price;
	private int salePrice;
	private int maxPurchase;
	private int deposit;
	private int delivery;
	private boolean newp;
	private boolean best;
	private boolean discount;
	private boolean info;		// no(미사용:false), yes(사용:true)
	private List<ProductInfoVO> infoList;
	private boolean opt;		// no(미사용:false), yes(사용:true)
	private List<ProductOptionVO> optionList;
	private String userid;
	private int readCount;
	private String introduction;
	private BoardAttachVO pc_list;
	private List<BoardAttachVO> pc_main;
	private BoardAttachVO pc_expose;
	private BoardAttachVO mobile_list;
	private List<BoardAttachVO> mobile_main;
	private BoardAttachVO mobile_expose;
	private String pc_detail;
	private String mobile_detail;
	private String pc_delivery;
	private String mobile_delivery;
	private String pc_exchange;
	private String mobile_exchange;
	private List<ProductHistoryVO> historyList;
	private String memo;
	private String expose;
	private Date createdAt;
	private Date modifiedAt;
}