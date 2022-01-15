package org.zerock.aop;

import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component // 스프링에서 해당 클래스가 빈으로 인식시키기 위해서 써준다
public class LogAdvice {
	@Before("execution(* org.zerock.service.SampleAOPService*.doAdd(String, String)) && args(str1, str2) ")
	public void logBefore(String str1, String str2) {
		System.out.println("===@Before===========================");
		log.info("str1 > " + str1);
		log.info("str2 > " + str2);
	}

	@Around("execution(* org.zerock.service.SampleAOPService.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		System.out.println("===@Around===========================");
		log.info("target >>> " + pjp.getTarget());
		log.info("params >>> " + Arrays.toString(pjp.getArgs()));

		Object result = null;

		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println("===@Around===========================");
		log.info("TIME : " + (end - start));
		System.out.println("===@Around===========================");
		log.info("result >>> " + result);
		System.out.println("===@Around===========================");
		return result;
	}

	// Before를 먼저 실행하고,, 지정된 대상이 예외를 발생한 후에 동작하여 문제를 찾을 수 있게 도와준다
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleAOPService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		System.out.println("===@AfterThrowing===========================");
		log.info("Exception....!!!!");
		log.info("exception: " + exception);

	}
}
