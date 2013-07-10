package com.src.test.driver;

import com.gogofindit.utils.SimpleDateFormatEnum;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.src.test.RemoteApiSetup;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadData {
    public static void main(String[] args) throws IOException {
    	
    	SimpleDateFormat dt = new SimpleDateFormat(SimpleDateFormatEnum.DATE.getSdfType());
    	SimpleDateFormat time = new SimpleDateFormat(SimpleDateFormatEnum.TIME.getSdfType());
    	Date date = new Date();
    	
    	new RemoteApiSetup(true);
        
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

            Entity entity = new Entity("searchdetail");

			entity = new Entity("searchdetail");
			entity.setProperty("tag", "toadd");
			entity.setProperty("tagList", "toadd");
			entity.setProperty("url", "www.google.com");
			entity.setProperty("date", dt.format(date));
			entity.setProperty("time", time.format(date));
			entity.setProperty("summary", "summary");

            System.out.println("Key of new entity is " + ds.put(entity));
            
            System.exit(0);

    }
}
