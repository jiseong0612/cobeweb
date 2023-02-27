package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		PageDTO pageDTO = new PageDTO(cri, service.getTotalCount(cri));
		
		model.addAttribute("list", service.getList(cri));
		model.addAttribute("pageMaker", pageDTO);
		
		log.info(pageDTO);
		return "/board/list";
	}

	@GetMapping("/register")
	public String register() {
		return "/board/register";
	}

	@GetMapping({"/get", "/modify"})
	public void get(Long bno, Criteria cri, Model model) {
		model.addAttribute("board", service.get(bno));
		model.addAttribute("cri", cri);
	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info("attach : "  + attach));
		}
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		if (service.modify(board) == 1) {
			rttr.addFlashAttribute("result", "success");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyWord", cri.getKeyWord());
		return "redirect:/board/list" + cri.getListLink();
	}

	@PostMapping("/remove")
	public String remove(Long bno, Criteria cri, RedirectAttributes rttr) {
		if (service.remove(bno) == 1) {
			rttr.addFlashAttribute("result", "success");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyWord", cri.getKeyWord());
		return "redirect:/board/list" + cri.getListLink();
	}

}
