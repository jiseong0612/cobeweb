
package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {

	@Autowired
	private ReplyMapper mapper;
	
	@Test
	public void insert() {
		ReplyVO reply = new ReplyVO();
		reply.setBno(236L);
		reply.setReply("content test");
		
		for(int i = 10 ; i< 30; i++) {
			reply.setReplyer("user"+i);
			
			mapper.insert(reply);
		}
		log.info("insert test  >>>> " + mapper.insert(reply));
	}

	@Test
	public void get() {
		Long rno = 2L;
		log.info(mapper.get(rno));
	}
	
	@Test
	public void deleteTest() {
		log.info(mapper.delete(8L));
	}
	
	@Test
	public void update() {
		ReplyVO reply = new ReplyVO();
		reply.setRno(1L);
		reply.setReply("update Reply");
		mapper.update(reply);
	}

//	@Test
//	public void getListTest() {
//		mapper.getList().forEach(board -> log.info(board));
//	}
//
	@Test
	public void getListWithPagingTest() {
		Criteria cri = new Criteria(1, 10);
		mapper.getListWithPaging(cri, 146L).forEach(board -> log.info(board));
	}
//
//
//	@Test
//	public void insertSelectKey() {
//		BoardVO board = new BoardVO();
//		board.setTitle("title test");
//		board.setContent("content test");
//		board.setWriter("user03");
//		mapper.insertSelectKey(board);
//
//		log.info("insert test  >>>> " + board.getBno());
//	}
//
//	@Test
//	public void delete() {
//		Long bno = 6L;
//
//		log.info(mapper.delete(bno));
//	}
//

}
