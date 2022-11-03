package com.myexam.market.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myexam.market.domain.TermsVO;
import com.myexam.market.service.TermsService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@RequestMapping("/terms/*")
@Log4j
public class TermsController {
	private TermsService service;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void list(Model model) {
		model.addAttribute("list", service.getList());
	}
	
	@PostMapping("add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String add(TermsVO terms, RedirectAttributes rttr) {
		service.add(terms);
		rttr.addFlashAttribute("result", terms.getTid());
		return "redirect:/terms/list";
	}
	
	@PostMapping("modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String modify(TermsVO terms, RedirectAttributes rttr) {
		if(service.modify(terms)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/terms/list";
	}
	
	@PostMapping("remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String remove(Long tid, RedirectAttributes rttr) {
		if(service.remove(tid)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/terms/list";
	}
}