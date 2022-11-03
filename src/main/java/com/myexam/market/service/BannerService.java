package com.myexam.market.service;

import com.myexam.market.domain.BannerUploadForm;
import com.myexam.market.domain.BannerVO;

public interface BannerService {
	BannerVO getBanner(int kind, int position);
	BannerVO addBanner(BannerUploadForm form);
	BannerVO updateBanner(BannerUploadForm form);
	void removeBannerInfo(int info_id);
	void deleteBanner(int bid);
}