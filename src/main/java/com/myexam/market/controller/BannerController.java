package com.myexam.market.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myexam.market.domain.BannerUploadForm;
import com.myexam.market.domain.BannerVO;
import com.myexam.market.service.BannerService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/banner")
@AllArgsConstructor
@Log4j
public class BannerController {
	private BannerService service;
	
	@GetMapping("gnb")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void gnb(@RequestParam(value="kind", defaultValue="1") int kind,
			@RequestParam(value="position", defaultValue="1") int position,
			Model model) {
		log.info("gnb...");
		BannerVO banner = service.getBanner(kind, position);
		model.addAttribute("banner", banner);
	}
	
	@PostMapping("/gnb")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String add(BannerUploadForm form) {
		BannerVO banner = service.addBanner(form);
		return "redirect:/banner/gnb?" + banner.getBannerLink();
	}
	
	@PostMapping("gnbMod")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String update(BannerUploadForm form) {
		BannerVO banner = service.updateBanner(form);
		return "redirect:/banner/gnb?" + banner.getBannerLink();
	}
	
	@PostMapping("gnbDel")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String remove(int kind, int position, int info_id) {
		service.removeBannerInfo(info_id);
		BannerVO banner = service.getBanner(kind, position);
		if(banner.getInfoList().size() == 0)
			service.deleteBanner(banner.getBid());
		return "redirect:/banner/gnb?kind=" + kind + "&position=" + position;
	}
}