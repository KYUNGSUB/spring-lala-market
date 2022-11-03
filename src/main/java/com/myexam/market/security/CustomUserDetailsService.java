package com.myexam.market.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import com.myexam.market.domain.MemberVO;
import com.myexam.market.mapper.MemberMapper;
import com.myexam.market.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// security에서 인증이 성공되면 사용자 정보와 권한 정보를 다루어야 하는데
@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;

	// UserDetails : 사용자의 정보와 권한 정보를 포함하는 인터페이스를 반환
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + userName);
		// 회원정보를 가져온다.
		MemberVO vo = memberMapper.read(userName);
		log.info("queried by member mapper: " + vo);
		return vo == null? null : new CustomUser(vo);	// CustomUser를 반환
	}
}