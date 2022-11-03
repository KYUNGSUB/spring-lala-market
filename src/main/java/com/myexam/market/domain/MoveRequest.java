package com.myexam.market.domain;

import lombok.Data;

@Data
public class MoveRequest {
	private int step;
	private String direction;
	private int seq;
	private String code;
	private String parent;
}