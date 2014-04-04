package com.task4java.test.common;

import com.task4java.http.client.ImageClient;
import com.task4java.http.client.ImageClientGingerbread;
import com.task4java.http.client.RestClient;
import com.task4java.http.client.RestClientGingerbread;
import com.task4java.util.log.ConsoleLogger;
import com.task4java.util.log.Logger;

public class InitSetup {

	public static void initLogger() {
        
        Logger.instance = new ConsoleLogger(true);
    }
	
	 public static void initServiceClient() {
	        
	        RestClient.instance = new RestClientGingerbread();
	        RestClient.instance.ignoreCertificateErrors();
	        RestClient.instance.setProxy("localhost", 8888);
	        
	        ImageClient.instance = new ImageClientGingerbread();
	        ImageClient.instance.ignoreCertificateErrors();
	        ImageClient.instance.setProxy("localhost", 8888);
	    }
}
