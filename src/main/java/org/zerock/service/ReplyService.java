package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

@Service
public class ReplyService {
	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	//추가
	@Transactional
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		return mapper.insert(vo);
	}

	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}

	public List<ReplyVO> getList(Criteria cri, Long bno) {
		return mapper.getList(cri, bno);
	}

	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		//댓글 페이징을 위해 댓글 갯수와 cri, bno를 한번에 전달
		return new ReplyPageDTO(
				mapper.getCountByBno(bno), 
				mapper.getList(cri, bno)
		);
	}
	
	//삭제
	@Transactional
	public int remove(Long rno) {
		ReplyVO vo = mapper.read(rno);

		boardMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}
}
