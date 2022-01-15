package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j

public class SampleTxServiceTests {
	@Autowired
	private SampleTxService service;
	
	@Test
	public void TxTests() {
		String str ="2222222222222222fdsafdsafsafsafdsfds하하하하 22222222222222음아하하하하하하 몇바이트가 2222222222222222넘는것이냐 음ㅇ하하하하하하 나도 몰른다 음하하하하핳하";
		System.out.println("getBytes()  >>> "+str.getBytes().length);
		service.addData(str);
	}
}
