package org.zerock.domain;

import lombok.Data;

@Data
public class AttachDTO {
	private  String uploadPath, fileName, uuid;
	private boolean image;
}
