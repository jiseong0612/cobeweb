package org.zerock.domain;

import lombok.Data;

@Data
public class AttachDTO {
	private  String path, fileName, uuid;
	private boolean image;
}
