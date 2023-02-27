package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	@Autowired
	private BoardService service;
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("title test22");
		board.setContent("content test22");
		board.setWriter("user0122");
		
		service.register(board);
	}
	
	@Test
	public void testGetList() {
		Criteria cri = new Criteria();
		service.getList(cri).forEach(board -> log.info(board));
	}
}
