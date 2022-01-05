package org.zerock.domain;

import java.util.Date;

import lombok.Data;
@Data
public class ReplyVO {
	private Long bno, rno;
	private String reply, replyer;
	private Date replyDate, updateDate;
}
