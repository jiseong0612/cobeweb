package org.zerock.domain;

import lombok.Data;

@Data
public class PageDTO{
	private boolean prev, next;
	private int startPage, endPage;
	
	private Criteria cri;
	private int total;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0) * 10);
		this.startPage = this.endPage - 9;
		
		int realEnd = (int)(Math.ceil(total * 1.0 /cri.getAmount()));
		
		this.endPage = realEnd < endPage ? realEnd : endPage;
		
		this.prev = startPage > 1;
		this.next = realEnd > endPage;
	}
	
	
}
