package com.src.test;

import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class RemoteApiSetup {

	private static CredentialDetail credentialDetail;
	private static RemoteApiInstaller installer;
	
	public RemoteApiSetup(Boolean isProd) {

		if(installer == null){
		try {
			
			if(isProd){
				credentialDetail = new ProdCredentialImpl();
			}
			else {
				credentialDetail = new TestCredentialImpl();
			}
			
			RemoteApiOptions options = new RemoteApiOptions().server(
					credentialDetail.getServer(), credentialDetail.getPort()).credentials(credentialDetail.getUserName(),
					credentialDetail.getPassword());
			installer = new RemoteApiInstaller();
			installer.install(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	public int getMaxRows() {
		return credentialDetail.getMaxRows();
	}
}
