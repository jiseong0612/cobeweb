package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyControllre {
	@Autowired
	private ReplyService service;
	
	/**
	 * 댓글 작성
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/new", consumes = "application/json; charset=utf-8", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		System.out.println("enter.......");
		int result = service.register(vo);
		return (result == 1)
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 댓글 1개 상세조회
	 * 
	 * @param rno
	 * @return
	 */
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		ReplyVO vo = service.get(rno);
		return (vo != null)
				? new ResponseEntity<ReplyVO>(vo, HttpStatus.OK)
				: new ResponseEntity<ReplyVO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 댓글 목록 조회
	 * 
	 * @param bno
	 * @param page
	 * @return
	 */
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyPageDTO> getList (@PathVariable("bno") Long bno, @PathVariable("page") int page){
		Criteria cri = new Criteria(page, 10);
		return new ResponseEntity<ReplyPageDTO>(service.getListPage(cri, bno) , HttpStatus.OK);
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param rno
	 * @return
	 */
	@DeleteMapping(value = "/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		return (service.remove(rno) == 1)
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 댓글 수정
	 * 
	 * @param vo
	 * @param rno
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT} ,value = "/{rno}", consumes="application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String>modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		vo.setRno(rno);
		return (service.modify(vo) == 1)
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
