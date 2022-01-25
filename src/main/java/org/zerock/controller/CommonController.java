package org.zerock.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	
	/**
	 * 로그인 실패시
	 * 
	 * @param auth
	 * @param model
	 */
	@GetMapping("/accessError")
	public void accessError(Authentication auth, Model model) {
		System.out.println("access Denied : " + auth);
		model.addAttribute("msg", "Access Demied");
	}

	/**
	 * 로그인
	 * 
	 * @param error
	 * @param logout
	 * @param model
	 */
	@GetMapping("/customLogin")
	public void customLogin(String error, String logout, Model model) {
		log.info("로그인을 하던 안 하던 우선 여기로 온다");
		log.info("error : " + error);
		log.info("logout : " + logout);

		if (error != null) {
			model.addAttribute("error", "Login Error Check your Account");
		}

		if (logout != null) {
			model.addAttribute("logout", "LogOut!!!");
		}
	}

	/**
	 * 로그아웃
	 */
	@GetMapping("/customLogout")
	public void customLogout() {
		log.info("@GetMapping custom logout");
	}
}
