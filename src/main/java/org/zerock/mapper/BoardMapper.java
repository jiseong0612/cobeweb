package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);

	public int insert(BoardVO board);

	public int insertSelectKey(BoardVO board);

	public BoardVO get(Long bno);

	public int delete(Long bno);

	public int update(BoardVO board);

	public int getTotalCount(Criteria cri);
	
	public int updateReplyCnt(
		@Param(value = "bno") Long bno,
		@Param(value = "amount") int amount
	);
}
