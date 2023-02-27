package org.zerock.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

@RestController
@RequestMapping("/simple")
public class SimpleController {
	@Autowired
	BoardMapper mapper;
	
	@GetMapping(value = "/getText", produces = "text/plain;charset=utf-8")
	public String getText() {
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getValue")
	public List<BoardVO> getValue(@RequestParam Map<String, String>inMap) {
		System.out.println(inMap);
		return mapper.getList();
	}
}
