package com.gogofindit.dao;

public class Search {
	
	private String searchButton;
	
	public String getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(String searchButton) {
		this.searchButton = searchButton;
	}

	private String param;
	private String isGoogle;
	private String isDuckDuckGo;
	private String isWikipedia;
	
	public String getIsDuckDuckGo() {
		return isDuckDuckGo;
	}

	public void setIsDuckDuckGo(String isDuckDuckGo) {
		this.isDuckDuckGo = isDuckDuckGo;
	}

	public String getIsWikipedia() {
		return isWikipedia;
	}

	public void setIsWikipedia(String isWikipedia) {
		this.isWikipedia = isWikipedia;
	}
	
	public String getIsGoogle() {
		return isGoogle;
	}

	public void setIsGoogle(String isGoogle) {
		this.isGoogle = isGoogle;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param.toLowerCase();
	}
	

}
