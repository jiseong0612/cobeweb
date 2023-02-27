
package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Autowired
	private BoardMapper mapper;

	@Test
	public void get() {
		Long bno = 7L;
		log.info(mapper.get(bno));
	}

	@Test
	public void getListTest() {
		mapper.getList().forEach(board -> log.info(board));
	}

	@Test
	public void getListWithPagingTest() {
		Criteria cri = new Criteria(1, 10);
		System.out.println(mapper.getTotalCount(cri));
		mapper.getListWithPaging(cri).forEach(board -> log.info(board));
	}

	@Test
	public void insert() {
		BoardVO board = new BoardVO();
		board.setTitle("title test");
		board.setContent("content test");
		board.setWriter("user01");

		for(int i= 0; i<200; i++) {
			board.setWriter("users"+i);
			mapper.insert(board);
		}
		
	}

	@Test
	public void insertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("title test");
		board.setContent("content test");
		board.setWriter("user03");
		mapper.insertSelectKey(board);

		log.info("insert test  >>>> " + board.getBno());
	}

	@Test
	public void delete() {
		Long bno = 6L;

		log.info(mapper.delete(bno));
	}

	@Test
	public void update() {
		BoardVO board = new BoardVO();
		board.setBno(3L);
		board.setTitle("update TItle");
		board.setContent("updateed contetner");
		board.setWriter("new Writer");

		mapper.update(board);
	}
	
	@Test
	public void updateReplyCount() {
		
		mapper.updateReplyCnt(235L, 1);
	}
}
