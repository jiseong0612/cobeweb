package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class ReplyController {
	@Autowired
	private ReplyService service;
	
	//등록
	//@RequestBody 생략시 모델어트리뷰트로 들어가서 그냥 생략하지 말아라... 스트링이던 객체던
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>create(@RequestBody ReplyVO reply){
		 
		return service.register(reply) == 1
			? new ResponseEntity<String>("success", HttpStatus.OK)
			: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//조회
	@GetMapping(value = "/{rno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReplyVO> get (@PathVariable("rno") Long rno) {
		return new ResponseEntity<ReplyVO>(service.read(rno), HttpStatus.OK);
	}
	
	//목록조회
	@GetMapping(value = "/pages/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReplyPageDTO>getList(@PathVariable Long bno, @PathVariable int page){
		Criteria cri = new Criteria(page, 10);
		
		return new ResponseEntity<ReplyPageDTO>(service.getListPage(cri, bno), HttpStatus.OK);
	}
	
	//수정
	@PreAuthorize("principal.username == #reply.replyer")
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PATCH, RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>modify(@PathVariable Long rno, @RequestBody ReplyVO reply){
		reply.setRno(rno);
		
		return service.modify(reply) == 1 
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//삭제
	@PreAuthorize("principal.username == #vo.replyer")
	@DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable Long rno, @RequestBody ReplyVO vo){
		
		return service.remove(rno) == 1 
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
