package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	private String userid, userpw, username, auth;
	private Date regdate, updateDate;
	private int enabled;
	
}
