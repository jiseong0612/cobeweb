package org.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board")
@Log4j
public class BoardController {
	@Autowired
	private BoardService service;
	
	@GetMapping("/TestJsp")
	public void TestJsp() {
		//자바스크립트 if(조건절) 안에 익명함수 명을 넣으면 
		//true false 있는지 없는지 체크해 준다.
		System.out.println("있다 없다");
		
	}
	
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		List<BoardVO> list = service.getList(cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotalCount(cri)));
	}

	@GetMapping("/register")
	public void registerGet() {

	}

	@GetMapping({ "/get", "/modify" })
	public void get(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		Long bno = service.register(board);

		log.info("bno >>> " + bno);

		rttr.addFlashAttribute("result", bno);

		return "redirect:/board/list";
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		int result = service.modify(board);

		if (result == 1) {
			rttr.addFlashAttribute("result", "success");
		}

		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}

	@PostMapping("/remove")
	public String remove(Long bno, Criteria cri, RedirectAttributes rttr) {
		int result = service.remove(bno);

		if (result == 1) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}

}
