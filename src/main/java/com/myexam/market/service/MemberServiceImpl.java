package com.myexam.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myexam.market.domain.AuthVO;
import com.myexam.market.domain.MemberVO;
import com.myexam.market.mapper.MemberAuthMapper;
import com.myexam.market.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {
	@Setter(onMethod_ = {@Autowired})
	private MemberMapper mapper;
	
	@Setter(onMethod_ = {@Autowired})
	private MemberAuthMapper authMapper;
	
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;


	@Override
	public String idCheck(String userid) {
		if(mapper.idCheck(userid) == null) {
			return "not using";
		} else {
			return "using";
		}
	}

	@Transactional
	@Override
	public void join(MemberVO member) {
		log.info("MemberService:join()");
		
		String encPw = pwencoder.encode(member.getUserpw());
		member.setUserpw(encPw);
		
		mapper.insert(member);
		// 디폴트로 ROLE_USER 부여 : 추후 권한 부여에 대한 기능 검토 필요
		AuthVO auth = new AuthVO();
		auth.setUserid(member.getUserid());
		auth.setAuth("ROLE_USER");
		authMapper.insert(auth);
	}
}