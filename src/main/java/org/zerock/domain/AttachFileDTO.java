package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	private String uuid, uploadPath, fileName;
	
	private boolean image;
}
