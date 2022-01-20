package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

	/**
	 * uploadForm : 폼타입 파일업로드 화면
	 * uploadAjax : AJAX 파일업로드 화면
	 */
	@GetMapping(value = {"/uploadForm", "/uploadAjax"})
	public void uploadForm() {
	}
	
	/**
	 * 폼타입 파일업로드
	 * 
	 * @param uploadFile
	 * @throws IllegalStateException
	 * @throws IOException
	 */
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
	
	/**
	 * 파일 업로드 (AJAX)
	 * 
	 * @param uploadFile (클라이언트에서 넘어온 var fd = new FormData();)
	 * @return ResponseEntity<List<AttachDTO>>
	 */
	@PostMapping(value = "/uploadAjaxAction", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<List<AttachDTO>> uploadAjaxAction(MultipartFile[] uploadFile) throws IllegalStateException, IOException {
		List<AttachDTO> list = new ArrayList<AttachDTO>();
		String path = "c:\\upload";
		
		//파일 객체에서 왼쪽은 부모디렉토리 오른쪽은 자식디렉토리를 뜻함
		File uploadPath = new File(path, getPath());
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile file : uploadFile	) {
			AttachDTO attach = new AttachDTO();
			
			UUID uuid = UUID.randomUUID();
			String uploadFileName = uuid.toString()+"_"+file.getOriginalFilename();
			
			attach.setUuid(uuid.toString());
			attach.setUploadPath(getPath());
			attach.setFileName(uploadFileName);
			
			//저장할 파일객체 생성! (경로, 파일명)
			File saveFile = new File(uploadPath, uploadFileName);
			
			//파일 업로드!(원본 파일은 원본대로 올라가고 이미지일경우 섬네일도 생성하여 같이 올린다)
			file.transferTo(saveFile);
			
			//만약 이미지 파일이라면? 섬네일 생성
			if(checkImageType(saveFile)) {
				attach.setImage(true);
				
				//섬네일용 파일 객체를 생성한다.	섬네일 파일명 : s_ + uuid.toString + getOriginalFilename();
				FileOutputStream fos = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
				
				//FileOutputStream : 서버에서 실제 파일을 생성
				//FileInputStream : 실제 파일을 서버에서 읽고 업데이트 작업을 할 떄
				//File : 파일객체를 서버로 가져올떈
				Thumbnailator.createThumbnail(file.getInputStream(), fos, 100 ,100);
				
				fos.close();
			}else {
				attach.setImage(false);
			}
			
			//첨부파일 객체를 리스트에 담고 리스트를 리턴해준다.
			list.add(attach);
		}// for() end
		
		return (list.size() != 0)
				? new ResponseEntity<List<AttachDTO>>(list, HttpStatus.OK)
				: new ResponseEntity<List<AttachDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 파일 다운로드
	 * 
	 * @param fileName :상세경로(/년/월/일)+uuid.toString_파일원본이름.확장자
	 * @return ResponseEntity<byte[]>
	 */
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		//uuid 제거 순수 파일명만 리턴!!
		String resourceOriginalName = resourceName.substring(resourceName.lastIndexOf("_")+1);
		
		HttpHeaders header = new HttpHeaders();
		try {
			String downLoadName = "";
			
			//IE browser
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downLoadName = URLEncoder.encode(resourceOriginalName, "utf-8");
			}
			//chrome browser
			else {
				downLoadName = new String(resourceOriginalName.getBytes("utf-8"), "ISO-8859-1");
			}
			//content-disposition : 컨텐츠가 웹페이지의 일부인지, 혹은 첨부파일인지 알려주는 헤더
			header.add("content-disposition", "attachment; filename="+downLoadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

	/**
	 * 업로드 후 파일 노출
	 * 
	 * @param  fileName :상세경로(/년/월/일)+uuid.toString_파일원본이름.확장자
	 * @return ResponseEntity<byte[]>
	 */
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName) throws IOException{
		log.info("fileName: " + fileName);

		File file = new File("C:\\upload\\"+fileName);
		log.info("file.getName: " + file.getName());
		
		//이건 스프링프레임워크의 라이브러리가 아닌 자바 자체의 라이브러리.
		byte[] buf = Files.readAllBytes(file.toPath());
		System.out.println(file.toPath());
		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(buf, header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName, String type){
		try {
			File file = new File("c:\\upload\\"+URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			if("image".equals(type)) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				System.out.println(file.getAbsolutePath());
				
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted!", HttpStatus.OK);
	}
	
	/**
	 * 파일 저장경로 날짜별로 생성
	 * 
	 * @return String
	 */
	private String getPath() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return sdf.format(date).replace("/", File.separator);
	}
	
	/**
	 * 파일 확장자 체크
	 * 
	 * @param file 객체
	 * @return boolean
	 */
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			System.out.println(file.toPath());
			System.out.println(contentType);
			return contentType.startsWith("image");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
