package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

@Service
@Transactional
public class ReplyService {

	@Autowired
	private ReplyMapper mapper;

	@Autowired
	private BoardMapper boardMapper;
	
	public int register(ReplyVO reply) {
		int result = boardMapper.updateReplyCnt(reply.getBno(), 1);		//댓글 추가시 게시판테이블 댓글수 컬럼 +1
		
		System.out.println("result >>>  " + result);
		
 		return mapper.insert(reply); 
	}
	
	public ReplyVO read(Long rno) {
		return mapper.get(rno);
	}

	public int modify(ReplyVO reply) {
		return mapper.update(reply);
	}

	public int remove(Long rno) {
		ReplyVO reply = mapper.get(rno);
		int result = boardMapper.updateReplyCnt(reply.getBno(), -1);		//댓글 삭제시 게시판테이블 댓글수 컬럼 -1
		
		System.out.println("result >>>  " + result);
		
		return mapper.delete(rno);
	}

	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(
				mapper.getTotalCount(bno),
				mapper.getListWithPaging(cri, bno)
		);
	}
}
