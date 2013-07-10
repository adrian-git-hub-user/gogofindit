package com.gogofindit.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.gogofindit.dto.SuggestionDetailBean;
import com.gogofindit.dto.SuggestionRequestBean;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.query.GeneralQueries;
import com.src.test.RemoteApiSetup;

@Controller
public class UpdateTagListController {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	@RequestMapping(value = "/updatetaglist" , method = RequestMethod.POST)
	@ResponseBody
	public String updateTagList(@RequestParam String newvalue , @RequestParam String elementid) {


		GeneralQueries generalQueries = new GeneralQueries();
		elementid = elementid.replaceAll("\\s+", " ");
		String tags[] = elementid.split(",");
		List<Entity> detailsList = generalQueries.getEntityToUpdateList(tags[0], tags[1]);

		String tagSeperatedWhiteSpace = newvalue.trim();
		tagSeperatedWhiteSpace = tagSeperatedWhiteSpace.replaceAll("\\s+", " ");
		String tagArray[] = tagSeperatedWhiteSpace.split(" ");
		List<String> tagList = Arrays.asList(tagArray);
		
		//jsonResponseObjectInner.tagList+","+jsonResponseObjectInner.url+","+jsonResponseObjectInner.date+","+jsonResponseObjectInner.time+","+jsonResponseObjectInner.summary+","+jsonResponseObjectInner.tag

		for (Entity entity : detailsList) {
			System.out.println("Deleting "+entity.getProperty("url"));
			datastore.delete(entity.getKey());
		}
		for(String tag : tagList){
			Entity entity = new Entity("searchdetail");
			entity.setProperty("url", tags[1]);
			entity.setProperty("tagList", newvalue);
			entity.setProperty("date", tags[2]);
			entity.setProperty("time", tags[3]);
			entity.setProperty("summary", tags[4]);
			entity.setProperty("tag", tag);
			
			datastore.put(entity);
		}

		
		 return newvalue;

	}
							   
	@RequestMapping(value = "/deleteentry" , method = RequestMethod.POST)
	@ResponseBody
	public String deleteEntry(@RequestParam String json) throws JsonParseException, JsonMappingException, IOException {

		GeneralQueries generalQueries = new GeneralQueries();
/*		elementid = elementid.replaceAll("\\s+", " ");
		String tags[] = elementid.split(",");*/
		
		ObjectMapper objectMapper = new ObjectMapper();
		SuggestionRequestBean srBean = objectMapper.readValue(json, SuggestionRequestBean.class);
		
		if(srBean.getSuggestionDetailBean().size() > 1){
			System.out.println("Warning : more than one entry found for deletion, entry should be unique");
		}
		SuggestionDetailBean sdb = srBean.getSuggestionDetailBean().get(0);
		
		/**
		 * tags[0] - the url
		 * tags[1] - the tag list
		 */
		List<Entity> detailsList = generalQueries.getEntityToDeleteList(sdb.getTagList(), sdb.getUrl(), sdb.getDate(),sdb.getTime());
		
		for (Entity entity : detailsList) {
			System.out.println("Deleting "+entity.getProperty("url"));
			datastore.delete(entity.getKey());
		}
		
		return "deleteSuccessful";
	}

	private List<Entity> getTagsToUpdate(String elementid) {
		
		GeneralQueries generalQueries = new GeneralQueries();
		elementid = elementid.replaceAll("\\s+", " ");
		String tags[] = elementid.split(",");
		
		return generalQueries.getEntityToUpdateList(tags[0], tags[1]);
		
	}
}
