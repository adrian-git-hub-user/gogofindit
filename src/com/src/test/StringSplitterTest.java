package com.src.test;

import java.util.Arrays;
import java.util.List;

public class StringSplitterTest {
	
	public static void main(String args[]){
		
		String tag = "1 test 4    5   newtag";
		
		/**
		 * Replaces > 1 whitespace with 1 whitespace
		 */
		tag = tag.replaceAll("\\s+", " ");
		
		String tags[] = tag.split(" ");
		List<String> l = Arrays.asList(tags);
		
		for(String l2 : l){
			System.out.println(l2);
		}

}
	
}
