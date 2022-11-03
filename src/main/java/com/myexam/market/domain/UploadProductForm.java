package com.myexam.market.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadProductForm {
	private Long pid;
	private String category1;
	private String category2;
	private String name;
	private int price;
	private int salePrice;
	private int maxPurchase;
	private String point;
	private int deposit;
	private String fee;
	private int delivery;
	private String[] feature;
	private String infoBtn;
	private String[] iname;
	private String[] idescription;
	private String optionBtn;
	private String[] oname;
	private String[] odescription;
	private String[] oprice;
	private String userid;
	private int readCount;
	private String introduction;
	private String pc_detail;
	private String mobile_detail;
	private String dguide;
	private String pc_delivery;
	private String mobile_delivery;
	private String exchange;
	private String pc_exchange;
	private String mobile_exchange;
	private String memo;
	private String expose;
	
	private List<MultipartFile> imgList;
	
	public ProductVO toProductVO() {
		ProductVO product = new ProductVO();
		if(pid != null) {
			product.setPid(pid);
		}
		product.setCategory1(category1);
		product.setCategory2(category2);
		product.setName(name);
		product.setPrice(price);
		product.setSalePrice(salePrice);
		product.setMaxPurchase(maxPurchase);
		if(point.equals("basic")) {
			product.setDeposit(-1);			// 기본 포인트 적용
		} else if(point.equals("apart")) {
			product.setDeposit(deposit);	// 개별 포인트 적용
		} else {
			product.setDeposit(0);			// 포인트 없음
		}
		if(fee.equals("basic")) {
			product.setDelivery(-1);		// 기본 배송비
		} else if(fee.equals("apart")) {
			product.setDelivery(delivery);	// 개별 배송비
		} else {
			product.setDelivery(0);	// 무료 배송비
		}
		for(String f : feature) {
			if(f.equals("newp")) {
				product.setNewp(true);
			} else if(f.equals("best")) {
				product.setBest(true);
			} else {
				product.setDiscount(true);
			}
		}
		if(infoBtn.equals("no")) {
			product.setInfo(false);
		} else {
			product.setInfo(true);
		}
		if(optionBtn.equals("no")) {
			product.setOpt(false);
		} else {
			product.setOpt(true);
		}
		product.setUserid(userid);
		product.setIntroduction(introduction);
		product.setPc_detail(pc_detail);
		product.setMobile_detail(mobile_detail);
		if(dguide.equals("indivisual")) {	// 개별 배송 안내
			product.setPc_delivery(pc_delivery);
			product.setMobile_delivery(mobile_delivery);
		}
		if(exchange.equals("indivisual")) {
			product.setPc_exchange(pc_exchange);
			product.setMobile_exchange(mobile_exchange);
		}
		product.setMemo(memo);
		product.setExpose(expose);
		return product;
	}

	public List<ProductInfoVO> toProductInfo() {
		List<ProductInfoVO> list = new ArrayList<>();
		for(int i = 0;i < iname.length;i++) {
			ProductInfoVO info = new ProductInfoVO();
			info.setName(iname[i]);
			info.setDescription(idescription[i]);
			info.setPid(pid);
			list.add(info);
		}
		return list;
	}

	public List<ProductOptionVO> toProductOption() {
		List<ProductOptionVO> list = new ArrayList<>();
		String name = null;
		int gid = -1;
		for(int i = 0;i < oname.length;i++) {
			ProductOptionVO option = new ProductOptionVO();
			if(!oname[i].equals(name)) {
				gid++;
				name = oname[i];
			}
			option.setGid(gid);
			option.setName(oname[i]);
			option.setDescription(odescription[i]);
			option.setPrice(Integer.parseInt(oprice[i]));
			option.setPid(pid);
			list.add(option);
		}
		return list;
	}
}