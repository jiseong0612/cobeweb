package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

@Service
public class ReplyService {
	@Autowired
	private ReplyMapper mapper;

	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}

	public List<ReplyVO> getListWithPagaing(Criteria cri, Long bno) {
		return mapper.getList(cri, bno);
	}

	public int register(ReplyVO vo) {
		return mapper.insert(vo);
	}

	public int remove(Long rno) {
		return mapper.delete(rno);
	}

	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}
}
