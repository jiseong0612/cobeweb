package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;
//spring 5버전 이상이라면  
//패스워드 암호화를 하고싶지 않다면
//직접 암호화가 없는 PasswordEncoder를 구현해야함
//빈 등록 꼭 해야 함
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("rawPassword >>"+rawPassword);
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String endcodePassword) {
		System.out.println("rawPassword >>"+rawPassword);
		System.out.println("endcodePassword >>"+endcodePassword);
		return rawPassword.toString().equals(endcodePassword);
	}

}
