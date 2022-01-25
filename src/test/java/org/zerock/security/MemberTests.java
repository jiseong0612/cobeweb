package org.zerock.security;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.UserDTO;
import org.zerock.mapper.MemberInsert;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {
	@Setter(onMethod_ = @Autowired)
	private MemberInsert insert;
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder encoder;

	@Setter(onMethod_ = @Autowired)
	private DataSource ds;

	@Test
	public void testInsertMember() {
		for(int i = 0; i < 100; i++) {
			UserDTO user = new UserDTO();
		
			user.setUserpw(encoder.encode("pw"+i));	//비밀번호는 중복적으로 if else에 넣을 필요없이 초기에 셋팅해서 저장
			
			if(i < 80) {
				user.setUserid("user"+i);
				user.setUsername("일반사용자"+i);
			}else if(i < 90) {
				user.setUserid("user"+i);
				user.setUsername("운영자"+i);
			}else {
				user.setUserid("user"+i);
				user.setUsername("관리자"+i);
			}
			
			insert.insert(user);
		}
	}
	
	@Test
	public void testInsertMemberAuth() {
		UserDTO user = new UserDTO();
		
		for(int i = 0; i < 100; i++) {
			if(i <80) {
				user.setUserid("user"+i);
				user.setAuth("ROLE_USER");
			}else if(i < 90) {
				user.setUserid("user"+i);
				user.setAuth("ROLE_MEMBER");
			}else {
				user.setUserid("user"+i);
				user.setAuth("ROLE_ADMIN");
			}
			
			insert.authInsert(user);
		}
	}
	
}
