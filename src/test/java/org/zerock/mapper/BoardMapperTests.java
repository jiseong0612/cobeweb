package org.zerock.mapper;

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
public class BoardMapperTests {

	@Autowired
	BoardMapper mapper;
	
	@Test
	public void getListWithPagingTest() {
		Criteria cri = new Criteria();
		cri.setKeyword("ti");
		System.out.println("test >>> " +cri);
		mapper.getListWithPaging(cri);
	}

	@Test
	public void getListTest() {
		log.info(mapper.getList());
	}

	@Test
	public void insertTest() {
		BoardVO board = new BoardVO();
		board.setWriter("jiseong");
		board.setTitle("title...");
		board.setContent("content........");

		int result = mapper.insertSelectKey(board);

		System.out.println("글작성 성공 (1) == " + result);
		System.out.println("select key bno >> " + board.getBno());
	}

	@Test
	public void readTest() {
		System.out.println(mapper.read(235L));
	}

	@Test
	public void updateTest() {
		BoardVO board = new BoardVO();
		board.setBno(5L);
		board.setTitle("업데이트 타이틀");
		board.setContent("업데이트 콘텐츠.123.");
		board.setWriter("sara..");
		int result = mapper.update(board);
		System.out.println("result >>> " + result);
	}

	@Test
	public void deleteTest() {
		int result = mapper.delete(1L);
		System.out.println("result >>> " + result);
	}
}
