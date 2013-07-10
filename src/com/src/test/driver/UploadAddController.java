package com.src.test.driver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gogofindit.dto.Tag;
import com.gogofindit.utils.SimpleDateFormatEnum;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.query.GeneralQueries;
import com.src.test.RemoteApiSetup;

@Controller
public class UploadAddController {

	private Entity entity;
	private Date date = new Date();
	private SimpleDateFormat dt = new SimpleDateFormat(SimpleDateFormatEnum.DATE.getSdfType());
	private SimpleDateFormat time = new SimpleDateFormat(SimpleDateFormatEnum.TIME.getSdfType());

	public String redirect(@RequestParam String singleTag, @RequestParam String url, @RequestParam String summary,
			@RequestParam String isprod , @RequestParam String multipleTags) {

		try {
			new RemoteApiSetup(new Boolean(true));

		
		/**
		 * Replaces > 1 whitespace with 1 whitespace
		 */
		singleTag = singleTag.replaceAll("\\s+", " ");
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		multipleTags = multipleTags.toLowerCase();
			
		String tagList = this.getTagList(summary);
		if(!tagList.equalsIgnoreCase("toadd")){
			String tagArray[] = tagList.split(" ");
			List<String> singleTagList = Arrays.asList(tagArray);
			for(String s : singleTagList){
				entity = new Entity("searchdetail");
				entity.setProperty("tag", s);
				entity.setProperty("tagList", tagList);
				entity.setProperty("url", url);
				entity.setProperty("date", dt.format(date));
				entity.setProperty("time", time.format(date));
				entity.setProperty("summary", summary);
				datastoreService.put(entity);
			}
		}
		else {
			entity = new Entity("searchdetail");
			entity.setProperty("tag", "toadd");
			entity.setProperty("tagList", "toadd");
			entity.setProperty("url", url);
			entity.setProperty("date", dt.format(date));
			entity.setProperty("time", time.format(date));
			entity.setProperty("summary", summary);
			datastoreService.put(entity);
		}

		return "added";

		}
		catch(java.lang.IllegalStateException jle){
			System.out.println("Remote API alread installed - caught at new RemoteApiSetup(new Boolean(isprod)); in class AddController");
			return "exceptionthrown";
		}
		catch(Exception e){
			e.printStackTrace();
			return "generalexecptionthrown";
		}
	}

	private String getTagList(String summary){
		
		String userTagName;
		
		String tagsToAdd = "";
		summary = summary.trim();
		GeneralQueries genQueries = new GeneralQueries();
		Set<Tag> userTagSet = genQueries.getUserTags();
		
		String documentHeader[] = summary.split(" |-|\\.|:|'");
		List<String> docHeaderTagList = Arrays.asList(documentHeader);

		for (String docHeaderTag : docHeaderTagList) {
			for(Tag userTag : userTagSet){
				docHeaderTag = docHeaderTag.trim().toLowerCase();
				/**
				 * Some of tags in data store are empty strings so check for this : 
				 */
				userTagName = userTag.getTagName().trim();
				if(!userTagName.equalsIgnoreCase("") && (userTag.getTagName().trim().equalsIgnoreCase(docHeaderTag))){
					tagsToAdd += userTagName + " ";
				}
			}
		}
		
		if(tagsToAdd.equalsIgnoreCase("")){
			return "toadd";
		}
		else {
			return tagsToAdd;
		}

	}
	
	public static void main(String args[]){
		UploadAddController u = new UploadAddController();
		
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode("http://gogofindit.appspot.com/", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String ret = u.redirect("toadd", encodedString, "test", "true", "toadd");
		
		System.out.println(ret);
		
		//javascript:(function(){window.open('http://gogofindit.appspot.com/servlets/add?singleTag=toadd&url=test&summary=test&isprod=true&multipleTags=toadd')})();
	}
}
