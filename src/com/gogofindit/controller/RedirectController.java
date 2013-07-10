package com.gogofindit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogofindit.dao.Search;

@Controller
public class RedirectController {
	  
	@RequestMapping(value="/getsearch")
	public String displaySearch(Model model) {
		model.addAttribute("searchobj" , new Search());
		
		FirstTest f = new FirstTest();
		return f.getString();
		
		//return "search";
	}
	
	  @RequestMapping(value="redirect", method = RequestMethod.GET)
	  public ModelAndView redirect(Search search) {		

		  ModelAndView modelAndView = new ModelAndView("redirect");
		  
		  modelAndView.addObject("searchButton", search.getSearchButton());
		  modelAndView.addObject("searchParam", search.getParam());
		  modelAndView.addObject("isWikipedia", search.getIsWikipedia());
		  modelAndView.addObject("isGoogle", search.getIsGoogle());
		  modelAndView.addObject("isDuckDuckGo", search.getIsDuckDuckGo());
		  
		  return modelAndView;
	  }

}
