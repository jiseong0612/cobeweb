package org.zerock.domain;

import lombok.Data;

@Data
public class PageDTO {
	private int startPage, endPage, total;
	private boolean prev, next;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		this.endPage = (int)Math.ceil(cri.getPageNum() / 10.0)*10;
		this.startPage = endPage - 9;
		this.prev = startPage > 1;
		
		int realEnd = (int)Math.ceil(total * 1.0 / cri.getAmount());
		this.endPage = realEnd <= endPage ? realEnd : endPage;
		this.next = endPage < realEnd;
	}
}