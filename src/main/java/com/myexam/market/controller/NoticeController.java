package com.myexam.market.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.NoticeVO;
import com.myexam.market.domain.PageDTO;
import com.myexam.market.service.NoticeService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/notice/*")
@AllArgsConstructor
@Log4j
public class NoticeController {
	private NoticeService service;
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list... " + cri);
		
		List<NoticeVO> list = service.getList(cri);
		model.addAttribute("list", list);
		int criTotal = service.getCriteriaTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, criTotal));
	}
}