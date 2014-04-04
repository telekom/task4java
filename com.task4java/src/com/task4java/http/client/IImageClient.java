/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IImageClient.java
 */
package com.task4java.http.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface IImageClient {

	public int getOperations();
	public void ignoreCertificateErrors();

	public void setProxy(String hostname, Integer port);
	
	public ImageResponse getImage(URL action) throws MalformedURLException, IOException, URISyntaxException;
}
