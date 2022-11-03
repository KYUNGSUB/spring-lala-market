package com.myexam.market.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myexam.market.domain.Criteria;
import com.myexam.market.domain.PageDTO;
import com.myexam.market.domain.ProductVO;
import com.myexam.market.domain.UploadProductForm;
import com.myexam.market.service.CategoryService;
import com.myexam.market.service.ProductService;
import com.myexam.market.utils.Common;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/product/*")
@AllArgsConstructor
@Log4j
public class ProductController {
	private CategoryService categoryService;
	
	private ProductService productService;
	
//	private PolicyService policyService;

	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void register(Model model) {
		log.info("/product/register...");
		model.addAttribute("cList", categoryService.getSecondList(Common.STYLE_SHOP_CATEGORY));
	}
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')") 	// 관리자만 상품 등록 가능
	public String registerAction(UploadProductForm form, RedirectAttributes rttr) {
		log.info("registerAction..." + form);
		productService.register(form);
		rttr.addFlashAttribute("result", form.getPid()); // 추가된 상품 번호를 출력
		return "redirect:/product/list";
	}
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list: " + cri);
		List<ProductVO> list = productService.getList(cri);
		for(ProductVO product : list) {
//			if(product.getDeposit() == -1) {	// 기본 적립금
//				product.setDeposit(product.getSalePrice() * Integer.parseInt(
//						policyService.getValue("포인트 정책", "구매 포인트")) / 100);
//			} else {
				product.setDeposit(product.getSalePrice() * product.getDeposit() / 100);
//			}
		}
		model.addAttribute("list", list);
		int total = productService.getTotal();
		int criTotal = productService.getCriteriaTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total, criTotal));
	}
}