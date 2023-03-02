package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form...");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormAction(MultipartFile[] uploadFile) throws IllegalStateException, IOException {
        for(MultipartFile file : uploadFile) {
            log.info("-------------------------");
            log.info(file.getOriginalFilename());
            log.info(file.getSize());
            File saveFiles = new File("C://upload", file.getOriginalFilename());
            file.transferTo(saveFiles);
        }

    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
    }

    @PostMapping("/uploadAjaxAction")
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[]uploadFile) {
        List<AttachFileDTO> list = new ArrayList();
        String uploadFolder = "C:\\upload";

        String uploadFolderPath = getFolder();

        File uploadPath = new File(uploadFolder, uploadFolderPath);
        log.info("uploadPath : " + uploadPath);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        for(MultipartFile multipartFile : uploadFile) {
            AttachFileDTO attachDTO = new AttachFileDTO();
            String uploadFileName = multipartFile.getOriginalFilename();
            log.info("---------------------------------------");
            log.info("upload file name : " + uploadFileName);
            log.info("upload file size : " + multipartFile.getSize());

            String uuid = UUID.randomUUID().toString();

            attachDTO.setFileName(uploadFileName);
            attachDTO.setUuid(uuid);
            attachDTO.setUploadPath(uploadFolderPath);

            uploadFileName = uuid + "_" + uploadFileName;
            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile); //일단 원본 저장

                if(checkImageType(saveFile)) {
                    attachDTO.setImage(true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);//이미지인 경우 섬네일 저장
                    thumbnail.close();
                }

                list.add(attachDTO);

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName){
        log.info("fileName : " + fileName);

        File file = new File("C:\\upload\\" + fileName);

        log.info("file : " + file);

        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> download(String fileName) throws UnsupportedEncodingException{
        log.info("fileName : " + fileName);

        Resource resource = new FileSystemResource("C:\\upload\\"  + fileName);

        log.info("resource : " +  resource);

        String rosourceName = resource.getFilename();
        String resourceOriginalName = rosourceName.substring(rosourceName.indexOf("_") +1);
        String downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");

        HttpHeaders header = new HttpHeaders();
        header.add("content-disposition", "attachment; filename=" + downloadName);

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile (String fileName, String type){
        log.info("deleteFile : " + fileName);

        File file = null;

        try {
            file = new File("C:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
            file.delete();

            if("image".equals(type)) {	//이미지일 경우 섬네일 제거 한 뒤,
                String largeFileName = file.getAbsolutePath().replace("s_", "");

                log.info("largeFileName : " + largeFileName);

                file = new File(largeFileName); //원본 이미지 파일객체 생성 후 삭제

                file.delete();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
             return contentType.startsWith("image");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
