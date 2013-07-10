package com.gogofindit.dto;

import java.util.List;

public class SuggestionRequestBean {
	
	private List<SuggestionDetailBean> suggestionDetailBean;

	public List<SuggestionDetailBean> getSuggestionDetailBean() {
		return suggestionDetailBean;
	}

	public void setSuggestionDetailBean(
			List<SuggestionDetailBean> suggestionDetailBean) {
		this.suggestionDetailBean = suggestionDetailBean;
	}



}
