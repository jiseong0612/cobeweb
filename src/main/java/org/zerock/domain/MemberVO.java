package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String userid, userpw, userName;
	private boolean enabled;
	private Date regdate, updateDate;
	private List<AuthVO>authList;
}
