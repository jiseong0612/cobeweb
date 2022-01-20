package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//파일 첨부의 경우 수정이라는 개념이 존재하지 않는다.
	//완전 생으로 삭제후 등록을 하는게 즉 수정
	public void insert(BoardAttachVO vo);

	public void delete(String uuid);

	public List<BoardAttachVO> findByBno(Long bno);
}
