package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.SampleMapper;
import org.zerock.mapper.SampleMapper2;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Transactional
public class SampleTxService {

	@Autowired
	private SampleMapper mapper1;
	
	@Autowired
	private SampleMapper2 mapper2;
	
	public void addData(String value) {
		log.info("mappler 1..................");
		mapper1.insertCol1(value);
		
		log.info("mappler 2..................");
		mapper2.insertCol2(value);
		
		log.info("end >>>>>>>>>>>>>>>>>>>>>>>>");
		
	}
}
