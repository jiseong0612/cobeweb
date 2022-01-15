package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
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
	public void uplaodPost(MultipartFile[] uploadFile) throws IllegalStateException, IOException {
		String path = "c:\\upload";
		
		//파일 객체에서 왼쪽은 경로를 오른쪽은 파일을 예기한다.
		File getPath = new File(path, "wltjd\\sara");
		
		if(getPath.exists() == false) {
			getPath.mkdirs();
		}
		for(MultipartFile file : uploadFile	) {
			UUID uuid = UUID.randomUUID();
			File uploadFiles = new File(getPath, uuid.toString()+"_"+ file.getOriginalFilename());
			
			file.transferTo(uploadFiles);
		}
	}
	
	public static String getPath() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return sdf.format(date).replace("/", File.separator);
	}
}
