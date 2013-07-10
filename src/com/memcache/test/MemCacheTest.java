package com.memcache.test;

import java.util.Collections;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;


        
public class MemCacheTest extends Application{
	
	public static void main(String args[]){
		
		println("Setting up API");
		
		 MemcacheService mservice = MemcacheServiceFactory.getMemcacheService();
		 
		new RemoteApiSetup(true);
		
        Cache cache = null;

        println("Setting up cache");
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
        	e.printStackTrace();
        }


        String key = "test";    
        String value = "testv";

        if(cache.containsKey(key)){
        	value = (String) cache.get(key);
        	System.out.println("Got value "+value);
        }
        else {
        	System.out.println("Adding value");
        	cache.put(key, value);
        }   
        
        System.exit(0);
        
	}

}
