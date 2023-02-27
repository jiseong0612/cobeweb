package org.zerock.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;


@Transactional
@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;
	
	@Autowired 
	private BoardAttachMapper attachMapper;

	public void register(BoardVO board) {
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach ->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	public BoardVO get(Long bno) {
		return mapper.get(bno);
	}

	public int modify(BoardVO board) {
		return mapper.update(board);
	}

	public int remove(Long bno) {
		return mapper.delete(bno);
	}

	public List<BoardVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}

	public int getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
}
