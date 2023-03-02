package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	@Autowired
	private BoardAttachMapper mapper;
	
	@Scheduled(cron = "0 * * * * *")
	public void checkFiles() throws Exception {
		log.warn("file check task run.................");
		log.warn(new Date());
		
		//어제 업로드된 파일 목록
		List<BoardAttachVO> fileList = mapper.getOldFiles();
		
		if(fileList == null || fileList.size() == 0 ) {
			return;
		}
		
		//어제 업로드된 파일 경로
		List<Path> fileListPaths = fileList
				.stream()
				.map(vo -> Paths.get("c:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());		
		
		//파일 목록 중에 이미지 파일인 경우 썸네일 경로도 추가!
		fileList.stream()
		.filter(vo ->vo.isFileType() == true)
		.map(vo -> Paths.get("c:\\upload", vo.getUploadPath(), "s_"+vo.getUuid() + "_" + vo.getFileName()))
		.forEach(p -> fileListPaths.add(p));
		
		//어제날짜의 폴더 파일객체 생성
		File targetDir = Paths.get("c:\\upload", getFolderYesterDay()).toFile();
		
		//삭제할 파일들 = 어제날짜 폴더의 파일 목록들중에 디비에서 조회한 어제날짜 파일들 비교 후, 'contains = false' 인 것을 조회
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("파일 삭제 진행...............................");
		
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());	//삭제할 파일 경로 조회
			file.delete();						//삭제...
		}
		
		log.warn("==========================================");
	}
	
	
	
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
}
