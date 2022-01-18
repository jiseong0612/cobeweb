package org.zerock.domain;

import lombok.Data;

@Data
public class AttachDTO {
	private  String uloadPath, fileName, uuid;
	private boolean image;
}
