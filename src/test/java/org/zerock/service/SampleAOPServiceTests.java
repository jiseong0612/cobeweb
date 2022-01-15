package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleAOPServiceTests {
	@Autowired
	private SampleAOPService serivce;
	
	@Test
	public void AOPTest() throws Exception {
		log.info("service >>> "+serivce);
		log.info(serivce.doAdd("111", "222"));
	}
	
	@Test
	public void errorTest() throws Exception {
		log.info(serivce.doAdd("123", "ABC"));
	}
}
