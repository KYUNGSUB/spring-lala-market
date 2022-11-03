package com.myexam.market.domain;

import lombok.Data;

@Data
public class OptionInfo {
	private boolean expose;	// 카테고리 노출 여부
	private boolean gnb;	// GNB 노출 여부
}