package com.myexam.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myexam.market.domain.PolicyUpdateForm;
import com.myexam.market.domain.PolicyVO;
import com.myexam.market.mapper.PolicyMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PolicyServiceImpl implements PolicyService {
	private PolicyMapper mapper;

	@Override
	public String getValue(String category, String name) {
		return mapper.getValue(category, name);	// 정책 파라미터 목록을 가져온다.
	}

	@Override
	public List<PolicyVO> getList() {
		return mapper.getList();
	}

	@Override
	public void modify(PolicyUpdateForm form) {
		// code=1
		processing(1, "배송정책", "기본배송료", form.getShopping());
		processing(2, "배송정책", "무료 배송", form.getFree());
		processing(3, "포인트 정책", "가입 포인트", form.getSubscription());
		processing(4, "포인트 정책", "구매 포인트", form.getPursuit());
		processing(5, "주문 취소 정책", "무통장 입금 시", form.getPeriod());
		processing(6, "공통 배송 안내 등록", "PC", form.getDpc());
		processing(7, "공통 배송 안내 등록", "Mobile", form.getDmobile());
		processing(8, "공통 교환 및 반품 안내 등록", "PC", form.getEpc());
		processing(9, "공통 교환 및 반품 안내 등록", "Mobile", form.getEmobile());
		processing2(10, "배송정책", "배송불가 지역", form.getPost());
	}

	private void processing(int code, String category, String name, String value) {
		PolicyVO newPolicy = new PolicyVO();
		newPolicy.setCode(code);
		newPolicy.setCategory(category);
		newPolicy.setName(name);
		newPolicy.setValue(value);
		PolicyVO oldPolicy = mapper.getPolicy(code);
		if(oldPolicy == null) {	// 신규 -> 추가
			mapper.add(newPolicy);
		} else {				// 기존 -> 수정
			mapper.update(newPolicy);
		}
	}

	// 배송 불가 지역 처리
	private void processing2(int code, String category, String name, String[] values) {
		mapper.removeAll(code);
		if(values == null) {
			return;
		}
		PolicyVO policy = new PolicyVO();
		policy.setCode(code);
		policy.setCategory(category);
		policy.setName(name);
		for(String value : values) {
			policy.setValue(value);
			mapper.add(policy);
		}
	}
}