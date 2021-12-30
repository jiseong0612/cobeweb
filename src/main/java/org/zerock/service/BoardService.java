package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	public Long register(BoardVO board);

	public BoardVO get(long bno);
	

	public int modify(BoardVO board);

	public int remove(Long bno);

	public List<BoardVO> getList();

	List<BoardVO> getList(Criteria cri);
	
	int getTotalCount(Criteria cri);
}
