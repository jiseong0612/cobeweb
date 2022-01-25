package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.log4j.Log4j;
@Log4j
//LogoutSuccessHandler : 로그아웃 성공 이후에 특정한 동작을 하도록 제어
public class CustomLogoutHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		
		request.getSession().invalidate();
		log.info("세션 만료 시킵니다 requset의 sessioin객체에서 입니다~~");
		System.out.println("세션 만료 시킵니다 requset의 sessioin객체에서 입니다~~");
		response.sendRedirect("/customLogin");
	}

}
