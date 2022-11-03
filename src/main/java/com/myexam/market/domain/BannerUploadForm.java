package com.myexam.market.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BannerUploadForm {
	private int bid;		// Banner id
	private int info_id;	// BannerInfo id
	private int kind;
	private int position;
	private String location;
	private String url;
	private String alt;
	private String target;
	private String login;
	
	private MultipartFile bannerImg;

	public BannerVO toBanner() {
		BannerVO banner = new BannerVO();
		banner.setBid(bid);
		banner.setKind(kind);
		banner.setPosition(position);
		if(location.equals("slide")) {
			banner.setLocation(1);
		} else if(location.equals("random")) {
			banner.setLocation(2);
		} else if(location.equals("login")) {
			banner.setLocation(3);
		} else {
			banner.setLocation(4);
		}
		
		return banner;
	}

	public BannerInfo toBannerInfo() {
		BannerInfo info = new BannerInfo();
		info.setBid(bid);
		info.setUrl(url);
		info.setAlt(alt);
		info.setTarget(target);
		if(login.equals("default")) {
			info.setLoginBefore(0);
		} else if(login.equals("before")) {
			info.setLoginBefore(1);
		} else {
			info.setLoginBefore(2);
		}
		return info;
	}
}