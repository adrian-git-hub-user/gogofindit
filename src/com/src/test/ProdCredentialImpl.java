package com.src.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Do not commit this file!!!!!!
 * 
 * 
 * @author Adrian
 *
 */
public class ProdCredentialImpl implements CredentialDetail{

	@Override
	//TODO read this from a file
	public String getPassword() {
		return this.readPassword();
	}

	@Override
	public String getUserName() {
		return "adrian.ronayne@gmail.com"; 
	}

	@Override
	public String getServer() {
		return "gogofindit.appspot.com";
	}

	@Override
	public int getPort() {
		return 443;
	}

	@Override
	public int getMaxRows() {
		return 2;
	}

	private String readPassword(){
		
		String str = "";
	try {
		
	    BufferedReader in = new BufferedReader(new FileReader("c:\\password\\file.txt"));
	    
	    while ((str = in.readLine()) != null){	    	
	    	in.close();
	    }
	} catch (IOException e) {
	}
	
	return str;
	}
}
