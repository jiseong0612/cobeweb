package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid, uploadPath, fileName, fileType;

	private Long bno;

}
