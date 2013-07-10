package com.gogofindit.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.query.GeneralQueries;
import com.src.test.RemoteApiSetup;

@Controller
public class UpdateBatchTagListController {

	@RequestMapping(value = "/updatebatchtaglist" , method = RequestMethod.GET)
	public String updateTagList(@RequestParam String isprod) {

		new RemoteApiSetup(new Boolean(isprod));
		
		GeneralQueries generalQueries = new GeneralQueries();
		List<Entity> detailsList = generalQueries.getBatchEntityToUpdateList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		for (Entity e : detailsList) {
			String tagListStr = (String) e.getProperty("tagList");
			tagListStr = tagListStr.replaceAll("\\s+", " ");

			String tags[] = tagListStr.split(" ");
			List<String> tagList = Arrays.asList(tags);

			for (String t : tagList) {
				Entity entity = new Entity("searchdetail");

				entity.setProperty("url", e.getProperty("url"));
				entity.setProperty("date", e.getProperty("date"));
				entity.setProperty("summary", e.getProperty("summary"));
				entity.setProperty("tag", t);
				entity.setProperty("tagList", e.getProperty("tagList"));
				entity.setProperty("time", e.getProperty("time"));

				System.out.println("Key of new entity is " + datastore.put(entity));
		
			}

			System.out.println("Removing " + tagListStr);
			datastore.delete(e.getKey());
		}


		return "finished";

	}

}
