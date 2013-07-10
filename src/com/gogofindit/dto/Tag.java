package com.gogofindit.dto;

import java.io.Serializable;

public class Tag implements Comparable<Object>, Serializable{
	
	private String tagName;

	public String getTagName() {
		if(this.tagName == null){
			return "";
		}
		else {
			return tagName;
		}		
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

    public int hashCode() {
    	return 1;
    }

    @Override
    public boolean equals(Object obj) {

    	Tag tag = (Tag) obj;
    	if(obj == null || this.tagName == null){
    		return false;
    	}
    	else {
    		return this.tagName.equals(tag.getTagName());
    	}
 
    }

	@Override
	public int compareTo(Object o) {
		
		Tag tag = (Tag)o;
		return this.getTagName().compareTo(tag.getTagName());

	}
}
