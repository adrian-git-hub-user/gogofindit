package com.gogofindit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.gogofindit.dto.SuggestionDetailBean;
import com.gogofindit.dto.Tag;
import com.memcache.store.service.TagStore;
import com.query.GeneralQueries;


@Controller
public class QueryController {
	  
	  @RequestMapping(value="getsuggestions", method = RequestMethod.GET)
	  public MappingJacksonJsonView redirect(@RequestParam String term, final ModelMap argModelMap) {		
		             
		  GeneralQueries generalQueries = new GeneralQueries();
		  List<SuggestionDetailBean> detailsList = generalQueries.getSuggestions(term);
		  Collections.sort(detailsList);
		  Set<SuggestionDetailBean> sortedSet = new HashSet<SuggestionDetailBean>();

		  sortedSet.addAll(detailsList);
		  
		  Set<SuggestionDetailBean> s = new TreeSet<SuggestionDetailBean>(sortedSet);
		  
		  argModelMap.addAttribute("jsonResponse", s);
		  
		  return new MappingJacksonJsonView();
	  }
	  
	  @RequestMapping(value="getusertags")
	  public MappingJacksonJsonView redirect(final ModelMap argModelMap) {		
		        
		  TagStore t = new TagStore();
		  argModelMap.addAttribute("userTagsJson", t.getTagList());
		  
		  return new MappingJacksonJsonView();
	  }

}
