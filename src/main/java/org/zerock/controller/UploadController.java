package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachDTO;

@Controller
public class UploadController {
	@GetMapping("/upload")
	public void upload() {
		
	}
	
	@PostMapping("/uploadAjax")
	public @ResponseBody ResponseEntity<List<AttachDTO>> uploadAjax(MultipartFile[] files) throws IllegalStateException, IOException {
		List<AttachDTO> list = new ArrayList<AttachDTO>();
		String path  = "c:\\upload";
		File uploadPath = new File(path, getPath());
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		for(MultipartFile file : files) {
			UUID uuid = UUID.randomUUID();
			AttachDTO attach = new AttachDTO();
			
			attach.setFileName(file.getOriginalFilename());
			attach.setImage(true);
			attach.setUuid(uuid.toString());
			
			File uploadFile = new File(uploadPath, uuid.toString()+"_"+ file.getOriginalFilename());
			
			//파일 저장
			file.transferTo(uploadFile);
			
			list.add(attach);
		}
		return (list.size() != 0) 
				? new ResponseEntity<List<AttachDTO>>(list, HttpStatus.OK)
				: new ResponseEntity<List<AttachDTO>>(HttpStatus.BAD_GATEWAY);
	}
	
	private String getPath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date).replace("-",File.separator);
	}
}
