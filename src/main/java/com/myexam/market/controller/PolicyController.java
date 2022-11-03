package com.myexam.market.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myexam.market.domain.PolicyUpdateForm;
import com.myexam.market.service.PolicyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/policy/*")
@Log4j
@AllArgsConstructor
public class PolicyController {
	private PolicyService service;
	
	@GetMapping("/manage")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void manage(Model model) {
		log.info("/policy/manage");
		
		model.addAttribute("list", service.getList());
	}
	
	@PostMapping("/manage")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String manage(PolicyUpdateForm form) {
		service.modify(form);
		return "redirect:/policy/manage";
	}
}