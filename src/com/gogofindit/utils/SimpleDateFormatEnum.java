package com.gogofindit.utils;

public enum SimpleDateFormatEnum {
	
	DATE("yyyy-MM-dd"), TIME("HH:mm:ss");
	
	private String sdfType;
	
	private SimpleDateFormatEnum(String sdfType){
		this.sdfType = sdfType;
	}

	public String getSdfType(){
		return this.sdfType;
	}
}
