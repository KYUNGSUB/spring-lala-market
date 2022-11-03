package com.myexam.market.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

// 회원정보를 추가하는 Junit 소스 코드
// 비밀번호를 암호화를 수행

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
public class MemberTests {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;

	// 회원정보를 추가하는 테스트 코드
//	@Test
	public void testInsertMember() {
		String sql = "insert into tbl_member(userid, userpw, username, mobile, email)"
				+ " values (?, ?, ?, ?, ?)";
		for(int i = 0; i < 100; i++) { 	// 100명을 추가     
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(2, pwencoder.encode("pw" + i));
				if(i < 80) {	// 일반 사용자 80명을 추가 (userxx)
					pstmt.setString(1, "user"+i);
					pstmt.setString(3,"일반사용자"+i);
					pstmt.setString(5, "user" + i + "@myexam.com");
				}else if (i <90) {	// i : 81~90  운영자(managerxx)
					pstmt.setString(1, "manager"+i);
					pstmt.setString(3,"운영자"+i);
					pstmt.setString(5, "manager" + i + "@myexam.com");
				}else {				// i : 91~100 관리자 (adminxx)
					pstmt.setString(1, "admin"+i);
					pstmt.setString(3,"관리자"+i);
					pstmt.setString(5, "admin" + i + "@myexam.com");
				}
				pstmt.setString(4, "010-2222-00" + i);
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
				if(con != null) { try { con.close();  } catch(Exception e) {} }
			}
		} //end for
	}
	
	// 사용자 접근 권한을 추가
	@Test
	public void testInsertAuth() {
		String sql = "insert into tbl_member_auth (userid, auth) values (?,?)";
		for(int i = 0; i < 100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				if(i <80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(2,"ROLE_USER");
				}else if (i <90) {
					pstmt.setString(1, "manager"+i);
					pstmt.setString(2,"ROLE_MEMBER");
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(2,"ROLE_ADMIN");
				}
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
				if(con != null) { try { con.close();  } catch(Exception e) {} }
			}
		}//end for
	}
}
