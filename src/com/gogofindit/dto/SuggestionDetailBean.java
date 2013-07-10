package com.gogofindit.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.datanucleus.util.StringUtils;

import com.gogofindit.utils.SimpleDateFormatEnum;


public class SuggestionDetailBean implements Comparable<Object> {

	private String url;
	private String tagList;
	private String numberOfRecommendations;
	private String date;
	private String time;
	private String summary;
	private String truncatedUrl;


	public void setTruncatedUrl(String truncatedUrl) {
		
		if(truncatedUrl.length() > 20){
			truncatedUrl = truncatedUrl.substring(0, 20)+"...";
		}
		
		this.truncatedUrl = truncatedUrl;
	}

	public String getSummary() {
		if(summary == null){
			return "";
		}
		else {
			return summary;
		}
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public String getTruncatedUrl() {
		return this.truncatedUrl;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTagList() {
		if(tagList == null){
			return "";
		}
		else {
			return tagList;
		}
	}

	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNumberOfRecommendations() {
		return numberOfRecommendations;
	}

	public void setNumberOfRecommendations(String numberOfRecommendations) {
		this.numberOfRecommendations = numberOfRecommendations;
	}

	@Override
	public int compareTo(Object o) {

	    DateFormat formatter;
	    Date date1 = null;
	    Date date2 = null;  
	    SuggestionDetailBean other = (SuggestionDetailBean) o;

	    if(this.date == null || other.date == null){
	    	return 0;
	    }	
	    formatter = new SimpleDateFormat(SimpleDateFormatEnum.DATE.getSdfType()+" "+SimpleDateFormatEnum.TIME.getSdfType());
	    try {
	        date1 = (Date) formatter.parse(this.date + " " + this.time);
	        date2 = (Date) formatter.parse(other.date + " " + other.time);
	    } catch (ParseException e) {
	    	System.out.println("Exception thrown in"+this.getClass().getName()+", compareTo method");
	        e.printStackTrace();
	    }
	    catch(NullPointerException npe){
	        System.out.println("Exception thrown "+npe.getMessage()+" date1 is "+date1+" date2 is "+date2);
	    }

	     return date2.compareTo(date1);

	}
	
	@Override
	   public int hashCode() {
			return this.url.hashCode();
	    }
	   
    @Override
    public boolean equals(Object obj) {

    	SuggestionDetailBean suggestionDetailBean = (SuggestionDetailBean) obj;
    	
    	/**
    	 * Taglist is not available for all entries so if not available just compare the URL
    	 */
    	if(StringUtils.isEmpty(this.getTagList())){
    		return this.getUrl().equals(suggestionDetailBean.getUrl());
    	}
    	else {
    		return (this.getTagList().equals(suggestionDetailBean.getTagList())) &&
    				(this.getUrl().equals(suggestionDetailBean.getUrl()));
    	}
 
    }
    
}
