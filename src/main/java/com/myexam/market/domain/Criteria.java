package com.myexam.market.domain;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;		// 현재 페이지 번호
	private int amount;			// 한 페이지에 보여줄 상품 수
	
	private String type;		// 검색 유형 : name(상품명), regid(등록자)
	private String keyword;		// 검색어
	
	private String category1;	// 1차 카테고리
	private String category2;	// 2차 카테고리
	private int priceFrom;		// 판매가격 하한가
	private int priceTo;		// 판매가격 상한가
	private String regFrom;		// 상품 등록일 하한일
	private String regTo;		// 상품 등록일 상한일
	private String[] exposeArr;	// 진열여부 : 진열(show), 품절(out), 숨김(hide)
	private String detail;		// 상세 검색 여부 : yes(상세 검색), no

	// 생성자
	public Criteria() {	// 기본적으로 1 페이지, 10개
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {	// 정해준 페이지, 갯수
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public int getPageStart() {	// 페이징을 위해 테이블에서 가져올 시작점
		return (pageNum - 1) * amount;
	}
}