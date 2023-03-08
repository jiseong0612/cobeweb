package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.security.domain.CustomUser;
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
    @PreAuthorize("isAuthenticated()")
    public String register() {
        return "/board/register";
    }

    @GetMapping({ "/get", "/modify" })
    public void get(Long bno, Criteria cri, Model model) {
        model.addAttribute("board", service.get(bno));
        model.addAttribute("cri", cri);
    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO board, RedirectAttributes rttr) {
    	log.info("register : " + board);
    	
        if (board.getAttachList() != null) {
            board.getAttachList().forEach(attach -> log.info("attach : " + attach));
        }

        service.register(board);
        rttr.addFlashAttribute("result", board.getBno());

        return "redirect:/board/list";
    }
    
    @PreAuthorize("principal.username == #board.writer")
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

	/* @PreAuthorize("principal.username == #writer") */
    @PostMapping("/remove")
    public String remove(Long bno, String writer, Criteria cri, RedirectAttributes rttr, Authentication authentication) {
    	log.info("===================================");
    	log.info("writer: " + writer);
    	log.info("===================================");
    	log.info(authentication);
    	
    	CustomUser user = (CustomUser)authentication.getPrincipal();

    	log.info("===================================");
    	log.info(user.getUsername());
    	log.info("===================================");
    	

    	log.info(authentication.getPrincipal());
        log.info("remove : " + bno);

        List<BoardAttachVO> attachList = service.getAttachList(bno);

        if(service.remove(bno) == 1) {
            deleteFiles(attachList);

            rttr.addFlashAttribute("result", " success");
        }
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyWord", cri.getKeyWord());
        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
        log.info("getAttachList bno : " + bno);
        return new ResponseEntity<List<BoardAttachVO>>(service.getAttachList(bno), HttpStatus.OK);
    }

    private void deleteFiles(List<BoardAttachVO> attachList) {
        if(attachList == null || attachList.size() == 0) {
            return;
        }

        log.info("deleteFiles..........");
        log.info(attachList);

        attachList.forEach(attach ->{
            try {
                Path file = Paths.get("c:\\upload\\"+attach.getUploadPath()+"/"+attach.getUuid()+"_"+attach.getFileName());
                Files.deleteIfExists(file);

                if(Files.probeContentType(file).startsWith("image")) {
                    Path thumbNail = Paths.get("c:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());

                    Files.delete(thumbNail);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
