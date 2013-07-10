package com.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gogofindit.dto.SuggestionDetailBean;
import com.gogofindit.dto.Tag;
import com.gogofindit.utils.DateUtils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;

public class GeneralQueries {

	public List<SuggestionDetailBean> getSuggestions(String searchParam) {
		List<SuggestionDetailBean> detailsList = new ArrayList<SuggestionDetailBean>();

		searchParam = searchParam.toLowerCase().trim();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		
		/**
		 * Replaces > 1 whitespace with 1 whitespace
		 */
		searchParam = searchParam.replaceAll("\\s+", " ");

		String tags[] = searchParam.split(" ");
		List<String> tagList = Arrays.asList(tags);

		Query query = new Query("searchdetail");
		Filter filter = null;		
		List<Filter> filterList = new ArrayList<Filter>();

		/**
		 * all-all-all indicates that a search for all entries (initial page load)
		 */
		if (!searchParam.equalsIgnoreCase("all-all-all")) {

			if (tagList.size() == 1) {
				filter = new FilterPredicate("tag", Query.FilterOperator.EQUAL,
						searchParam);
			} else {
				for (String tag : tagList) {
					filterList.add(new FilterPredicate("tag",Query.FilterOperator.EQUAL, tag));
				}
				
				filter = new CompositeFilter(CompositeFilterOperator.OR,filterList);
			}

			query.setFilter(filter);
		}
		else {
			Filter dateFilter = new FilterPredicate("date",Query.FilterOperator.GREATER_THAN_OR_EQUAL, DateUtils.getPastDate());
			query.setFilter(dateFilter);
		}
		
		SuggestionDetailBean suggestionDetailBean;
		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			
			suggestionDetailBean = new SuggestionDetailBean();
			suggestionDetailBean.setTruncatedUrl((String) result.getProperty("url"));
			suggestionDetailBean.setUrl((String) result.getProperty("url"));
			suggestionDetailBean.setTagList((String) result.getProperty("tagList"));
			suggestionDetailBean.setDate((String) result.getProperty("date"));
			suggestionDetailBean.setTime((String) result.getProperty("time"));
			suggestionDetailBean.setSummary((String) result.getProperty("summary"));
			System.out.println("Adding to object "+ suggestionDetailBean.getUrl());
			
			if(searchParam.equalsIgnoreCase("all-all-all") || isAllTagsInTagList(tagList , suggestionDetailBean.getTagList())){
				detailsList.add(suggestionDetailBean);
			}
		}

		System.out.println("Finished!");
		return detailsList;

	}

	private boolean isAllTagsInTagList(List<String> tagList, String tagString) {
		
		String tags[] = tagString.split(" ");
		List<String> tagList2 = Arrays.asList(tags);
		
		for(String s : tagList){
			if(!tagList2.contains(s)){
				return false;
			}
		}
		return true;
	}

	public Set<Tag> getUserTags() {
		Set<Tag> detailsList = new HashSet<Tag>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query("searchdetail");
		PreparedQuery pq = datastore.prepare(query);
		Tag tag;
		for (Entity result : pq.asIterable()) {

			tag = new Tag();
			tag.setTagName((String) result.getProperty("tag"));
			detailsList.add(tag);
		}

		return detailsList;

	}

	public List<Entity> getBatchEntityToUpdateList() {
		List<Entity> detailsList = new ArrayList<Entity>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		
		Query q = new Query("searchdetail");
		Filter nameFilter = new FilterPredicate("tag",
				Query.FilterOperator.EQUAL, "toadd");
		q.setFilter(nameFilter);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			detailsList.add(result);
		}

		return detailsList;

	}

	public List<Entity> getEntityToUpdateList(String tagList, String url) {
		List<Entity> detailsList = new ArrayList<Entity>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query("searchdetail");
		Filter tagListFilter = new FilterPredicate("tagList",
				Query.FilterOperator.EQUAL, tagList);
		Filter urlFilter = new FilterPredicate("url",
				Query.FilterOperator.EQUAL, url);

		Filter filter = new com.google.appengine.api.datastore.Query.CompositeFilter(
				CompositeFilterOperator.AND, Arrays.asList(tagListFilter,
						urlFilter));
		query.setFilter(filter);

		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			detailsList.add(result);
		}

		return detailsList;

	}

	public List<Entity> getEntityToDeleteList(String tagList, String url, String date, String time) {
		List<Entity> detailsList = new ArrayList<Entity>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query("searchdetail");
		Filter tagListFilter = new FilterPredicate("tagList",
				Query.FilterOperator.EQUAL, tagList);
		Filter urlFilter = new FilterPredicate("url",
				Query.FilterOperator.EQUAL, url);
		Filter dateFilter = new FilterPredicate("date",
				Query.FilterOperator.EQUAL, date);
		Filter timeFilter = new FilterPredicate("time",
				Query.FilterOperator.EQUAL, time);

		List<Filter> deleteEntityFilter = new ArrayList<Filter>();
		deleteEntityFilter.add(tagListFilter);
		deleteEntityFilter.add(urlFilter);
		deleteEntityFilter.add(dateFilter);
		deleteEntityFilter.add(timeFilter);
		
		Filter filter = new com.google.appengine.api.datastore.Query.CompositeFilter(CompositeFilterOperator.AND, deleteEntityFilter);
		query.setFilter(filter);

		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			detailsList.add(result);
		}

		return detailsList;

	}
	
}
