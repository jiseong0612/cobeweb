package org.zerock.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component	//스프링에서 해당 클래스가 빈으로 인식시키기 위해서 써준다
public class LogAdvice {
	@Before("execution(* org.zerock.service.SampleAOPService.*(..))")
	public void logBefore() {
		log.info("----------------");
	}

}
