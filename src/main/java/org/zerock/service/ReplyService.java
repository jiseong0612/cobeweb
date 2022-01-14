package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

@Service
public class ReplyService {
	@Autowired
	private ReplyMapper mapper;
	
	public int register(ReplyVO vo) {
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
	public int remove(Long rno) {
		return mapper.delete(rno);
	}

	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}
}
