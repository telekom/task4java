/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IRestClient.java
 */
package com.task4java.http.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.task4java.KeyValuePair;

public interface IRestClient {

	public int getOperations();
	public void ignoreCertificateErrors();
	public void activateContentLog();

	public void setProxy(String hostname, Integer port);

	public RestResponse get(URL action, List<KeyValuePair> requestHeaders,
			List<KeyValuePair> requestParams) throws IOException,
			URISyntaxException;

	public RestResponse delete(URL action, List<KeyValuePair> requestHeaders,
			List<KeyValuePair> requestParams) throws IOException,
			URISyntaxException;

	public RestResponse post(URL action, List<KeyValuePair> requestHeaders,
			List<KeyValuePair> requestParams, HttpContent requestBody)
			throws IOException, URISyntaxException;

	public RestResponse put(URL action, List<KeyValuePair> requestHeaders,
			List<KeyValuePair> requestParams, HttpContent requestBody)
			throws IOException, URISyntaxException;
}
