package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.extern.log4j.Log4j;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	@Autowired
	private BoardService service;
	
	@Test
	public void testWithPaging() {
		Criteria cri = new Criteria();
		cri.setType("TCW");
		cri.setKeyword("ttt");
		log.info(service.getList(cri));
	}
	
	
	@Test
	public void testPrint() {
		log.info("sssssssssssss >>> " +service);
	}
	
	@Test
	public void getListTest() {
		service.getList().forEach(v ->log.info(v));
	}
	
	@Test
	public void inserSelectKeyTest() {
		BoardVO board = new BoardVO();
		board.setWriter("jiji");
		board.setTitle("tt....");
		board.setContent("con.t......");
		Long result = service.register(board);
		System.out.println("등록된 글 번호 === "+board.getBno());
		log.info("result >>> "+ result);
		if(result == 0 ) {
			System.out.println("등록 실패!..");
		}else {
			System.out.println("등록 성공!...");
		}
	}
	
}
