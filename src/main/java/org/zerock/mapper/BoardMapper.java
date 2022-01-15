package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
