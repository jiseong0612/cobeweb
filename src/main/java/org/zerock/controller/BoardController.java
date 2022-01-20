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
	
	/**
	 * 게시판 목록 조회
	 * 
	 * @param model
	 * @param cri
	 */
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		List<BoardVO> list = service.getList(cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotalCount(cri)));
	}

	/**
	 * 조회 및 수정화면 이동
	 * 
	 * @param bno
	 * @param model
	 * @param cri
	 */
	@GetMapping({ "/get", "/modify" })
	public void get(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		model.addAttribute("board", service.get(bno));
	}

	/**
	 * 작성화면 이동
	 */
	@GetMapping("/register")
	public void registerGet() {
	}
	
	/**
	 * 글 작성
	 * 
	 * @param board
	 * @param rttr
	 * @return
	 */
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		Long bno = service.register(board);

		log.info("bno >>> " + bno);

		rttr.addFlashAttribute("result", bno);

		return "redirect:/board/list";
	}

	/**
	 * 글 수정
	 * 
	 * @param board
	 * @param cri
	 * @param rttr
	 * @return
	 */
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

	/**
	 * 글 삭제
	 * 
	 * @param bno
	 * @param cri
	 * @param rttr
	 * @return
	 */
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
