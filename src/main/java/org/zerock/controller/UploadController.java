package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
	}
	
	@PostMapping("/uploadAction")
	public void upload(MultipartFile[] uploadFile) throws IllegalStateException, IOException {
		String path = "c:\\upload";
		
		for(MultipartFile file : uploadFile) {
			// 업로드가 될 파일 클래스의 인스턴스는 경로와 파일명을 함께 저장한다.
			File uploadFiles = new File(path, file.getOriginalFilename());
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			
			file.transferTo(uploadFiles);	//여기서 저장됨
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
	}
	
	@PostMapping("/uplaodPost")
	public void uplaodPost(MultipartFile[] uploadFile) {
		String path = "c:\\upload";
		
		for(MultipartFile file : uploadFile	) {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
		}
	}
	
	public static String getPath() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return sdf.format(date).replace("/", File.separator);
	}
}
