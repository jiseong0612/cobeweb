package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
//AccessDeniedHandler : 접근이 제한된 경우 다양한 처리를 직접 구현
public class CustomAccessDeniedhandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessException)
			throws IOException, ServletException {
		log.info("Access Denied Handler.......");
		log.info("Redirect.......");
		response.sendRedirect("/accessError");
	}

}
