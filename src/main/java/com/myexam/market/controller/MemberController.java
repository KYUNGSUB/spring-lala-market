package com.myexam.market.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myexam.market.domain.MemberVO;
import com.myexam.market.service.MemberService;
import com.myexam.market.service.TermsService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
@Log4j
public class MemberController {
	private TermsService service;
	private MemberService memberService;
	
	@GetMapping("/login")
	public void login() {
		log.info("/login...");
	}
	
	@GetMapping("/terms")
	public void terms(Model model) {
		model.addAttribute("list", service.getList());	// 뷰로 약관 목록을 전달
	}
	
	@PostMapping("/terms")
	public String termsPro(String[] agreement, HttpServletResponse response) {
		Cookie cookie = new Cookie("terms", agreement[0]);
		response.addCookie(cookie);
		return "redirect:/member/join";
	}
	
	@GetMapping("/join")
	public String join(@CookieValue String terms, HttpServletResponse response, Model model) {
		log.info("join: cookie=" + terms);
		if(terms != null) {
			Cookie newCookie = new Cookie("terms", terms);
			newCookie.setMaxAge(0);
			response.addCookie(newCookie);
			model.addAttribute("marketing", terms);
			return "/member/join";
		} else {
			return "redirect:/index";
		}
	}
	
	@PostMapping("/idCheck")
	public @ResponseBody String idCheck(String userid) {
		return memberService.idCheck(userid);
	}
	
	@PostMapping("/join")
	public String joinPro(MemberVO member, RedirectAttributes rttr) {
		log.info("join: " + member);
		
		memberService.join(member);
		rttr.addFlashAttribute("result", member.getUserid());
		return "redirect:/member/login";
	}
}