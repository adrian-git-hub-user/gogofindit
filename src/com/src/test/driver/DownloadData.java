package com.src.test.driver;

import com.gogofindit.dto.Tag;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.query.GeneralQueries;
import com.src.test.RemoteApiSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DownloadData {
	public static void main(String[] args) throws IOException {

		new RemoteApiSetup(true);

	
		  List<String> l = getDetails();
		  
		  for(String s : l){
		  System.out.println(s); 
		  }

		/*
		 * Set<Tag> l = getUserTagList(); for(Tag s : l){
		 * System.out.println(s.getTagName()); }
		 */

		//getEntityToUpdateList();

		System.exit(0);

	}

	public static List<String> getDetails() {
		List<String> detailsList = new ArrayList<String>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter nameFilter = new FilterPredicate("URL",
				Query.FilterOperator.EQUAL, "testerurl");

		Query q = new Query("searchdetail");
		q.setFilter(nameFilter);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			detailsList.add((String) result.getProperty("URL"));
		}

		System.out.println("Finished!");
		return detailsList;

	}


}
