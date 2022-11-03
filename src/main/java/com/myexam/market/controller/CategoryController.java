package com.myexam.market.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.myexam.market.domain.CategoryVO;
import com.myexam.market.domain.MoveRequest;
import com.myexam.market.domain.OptionInfo;
import com.myexam.market.service.CategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
@Log4j
public class CategoryController {
	private CategoryService service;
	
	@GetMapping("show")
	public String show(Model model) {
		model.addAttribute("list", service.getList());
		return "/category/view";
	}
	
	@PostMapping("show")
	public String showPost(Model model) {
		List<CategoryVO> list = service.getList();	// 카테고리 목록 정보를 가져온다.
		Gson gson = new Gson();
		JsonElement userObj = gson.toJsonTree(list);
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("result", "success");
		jsonObj.add("data", userObj);
		model.addAttribute("check", jsonObj.toString());	// 카테고리 목록 정보를 뷰로 전달
		return "/member/idCheck";
	}
	
	@PostMapping("/add")
	public String add(CategoryVO category) {
		if(category.getParent() == null) {
			category.setStep(1);
		} else {
			category.setStep(2);	// 2차
			category.setParent(category.getParent());
		}
		service.add(category);
		return "redirect:/category/show";
	}
	
	@PostMapping("/check")
	public @ResponseBody String check(String code) {
		log.info("category check: code=" + code);
		return service.codeCheck(code);
	}
	
	@PostMapping("/move")
	public String move(MoveRequest mr) {
		service.moveCatgory(mr);
		return "redirect:/category.show";
	}
	
	@PostMapping("/modify")
	public String modify(String code, @Param("expose") String exposeStr, @Param("gnb") String gnbStr) {
		boolean expose = exposeStr.equals("yes") ? true: false;
		if(gnbStr == null) {	// 2차 카테고리 정보 변경 -> 노출 여부 변경
			service.modify(code, expose);	// 메소드 오버로딩 적용
		} else {	// 1차 카테고리 정보 변경 -> 노출 여부 및 GNB 노출여부 변경
			boolean gnb = gnbStr.equals("yes") ? true: false;
			service.modify(code, expose, gnb);
		}
		return "redirect:/category.show";
	}
	
	@PostMapping("/remove")
	public String remove(String code) {
		service.remove(code);
		return "redirect:/category.show";
	}
	
	@PostMapping("/getOption")
	public String getOption(String code, Model model) {
		OptionInfo option = service.getOptionInfo(code);
		Gson gson = new Gson();	// Gson 라이브러리를 사용
		JsonObject jsonObj = new JsonObject();	// JsonObject 객체를 생성
		jsonObj.addProperty("expose", option.isExpose());	// 필드로 저장
		jsonObj.addProperty("gnb", option.isGnb());
		model.addAttribute("check", gson.toJson(jsonObj));	// request 속성에 JSON string 형태로 변환하여 저장
		return "/member/idCheck";
	}
	
	@PostMapping("/getSeed")
	public @ResponseBody String getSeed(String code, Model model) {
		return String.valueOf(service.getSeed(code));
	}
}