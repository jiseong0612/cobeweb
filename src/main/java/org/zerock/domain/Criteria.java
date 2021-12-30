package org.zerock.domain;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String type; //t/c/w/tw/tc/twc/cw
	private String keyword;
	
	
	//아무런 값이 들어오지 않으면 초기값으로 1과 10을 준다.
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split("");
	}
}
