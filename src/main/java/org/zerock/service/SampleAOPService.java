package org.zerock.service;

import org.springframework.stereotype.Service;

@Service
public class SampleAOPService {
	public Integer doAddd(String str1, String str2) throws Exception{
		return Integer.parseInt(str1)+ Integer.parseInt(str2);
	}
}
