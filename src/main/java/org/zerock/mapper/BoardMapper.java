package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	List<BoardVO> getList();
	
	List<BoardVO> getListWithPaging(Criteria cri);

	void insert(BoardVO board);
	
	int insertSelectKey(BoardVO board);

	BoardVO read(Long bno);
	
	int update(BoardVO board);
	
	int delete(Long bno);
	
	int getTotalCount(Criteria cri);
}
