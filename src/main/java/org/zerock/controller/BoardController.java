package org.zerock.controller;

import java.util.List;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j
public class BoardController {
	private final BoardService service;
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		List<BoardVO> list = service.getList(cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotalCount(cri)));
	}
	
	@GetMapping("/register")
	public void registerGet() {
		
	}
	
	@GetMapping({"/get", "/modify"})
	public void get(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		model.addAttribute("board", service.get(bno));
	}
	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		Long bno = service.register(board);
		
		log.info("bno >>> "+ bno);
		
		rttr.addFlashAttribute("result", bno);
		
		return "redirect:/board/list";
	}
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri,RedirectAttributes rttr) {
		int result = service.modify(board);
		
		if(result == 1	) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(Long bno, Criteria cri,RedirectAttributes rttr) {
		int result = service.remove(bno);
		
		if(result == 1	) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
	}
	
	
}
