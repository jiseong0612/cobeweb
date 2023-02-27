package org.zerock.controller;

public class JavaTeest {
	public static void main(String[] args) {
		int count = 0;
		
		for(int i=0; i < 1000000000; i++) {
			int random = 1000000000 + (int)(Math.random()*1000000000);
			count ++;
			System.out.println(random + ">>>>>> "+ count);
			
		}
	}
}
