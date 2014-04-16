/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: InitSetup.java
 */

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

		// At this point an HTTP proxy can be configured, such as Fiddler by Telerik
		// RestClient.instance.setProxy("localhost", 8888);

		ImageClient.instance = new ImageClientGingerbread();
		ImageClient.instance.ignoreCertificateErrors();

		// At this point an HTTP proxy can be configured, such as Fiddler by Telerik
		// ImageClient.instance.setProxy("localhost", 8888);
	}
}
