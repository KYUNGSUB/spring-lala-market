package com.myexam.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myexam.market.domain.BannerInfo;
import com.myexam.market.domain.BannerVO;

public interface BannerMapper {
	BannerVO getBanner(@Param("kind") int kind, @Param("position") int position);
	List<BannerInfo> getBannerInfoList(int bid);
	void addBanner(BannerVO banner);
	void addBannerSelectKey(BannerVO banner);
	void updateBanner(BannerVO banner);
	void addBannerInfo(BannerInfo bannerInfo);
	void addBannerInfoSelectKey(BannerInfo bannerInfo);
	void updateBannerInfo(BannerInfo bannerInfo);
	void deleteBannerInfo(int info_id);
	void deleteBanner(int bid);
}