package com.memcache.store.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.gogofindit.dto.Tag;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.query.GeneralQueries;

public class TagStore {
	
	@SuppressWarnings("unchecked")
	public List<Tag> getTagList(){
		
		 MemcacheService mservice = MemcacheServiceFactory.getMemcacheService();
		 
		 List<Tag> returnList;
	
		List<Tag> l = (List<Tag>)mservice.get("myList");
		 if(l == null){
			 
			 GeneralQueries generalQueries = new GeneralQueries();
			 Set<Tag> detailsList = generalQueries.getUserTags();
			 List<Tag> userTagList = new ArrayList<Tag>();
			 userTagList.addAll(detailsList);
			  
			 Collections.sort(userTagList);
			 //Set expiration to one day
			 mservice.put("myList", userTagList, Expiration.onDate(new Date(System.currentTimeMillis() + 86400000)));
			 returnList = userTagList;
		 }
		 else {
			 returnList = (List<Tag>)mservice.get("myList");
		 }
		 
		 return returnList;
		 
	}

}
