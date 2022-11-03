package com.myexam.market.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.myexam.market.domain.MemberVO;

import lombok.Getter;

// 사용자가 정의한 회원 정보와 권한 정보를 가지는 클래스
// security를 적용하기 위하여 User 클래스를 상속해서 만들어야 한다.

@Getter
public class CustomUser extends User {
	private static final long serialVersionUID = 1L;

	// 필드
	private MemberVO member;

	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);	// User 클래스의 생성자를 호출
	}

	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		this.member = vo;
	}
}