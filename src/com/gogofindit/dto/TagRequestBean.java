package com.gogofindit.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagRequestBean {
	
	private Set<Tag> tagList = new HashSet<Tag>();

	public Set<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<Tag> tagList) {
		this.tagList = tagList;
	}

}
