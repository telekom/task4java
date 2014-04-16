/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: RestClientTest.java
 */

package com.task4java.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.task4java.KeyValuePair;
import com.task4java.http.HttpStatusCodes;
import com.task4java.http.client.HttpStringContent;
import com.task4java.http.client.RestClient;
import com.task4java.http.client.RestResponse;
import com.task4java.test.common.InitSetup;
import com.task4java.util.concurrent.CallableValue;
import com.task4java.util.concurrent.TaskException;
import com.task4java.util.concurrent.TaskFactory;
import com.task4java.util.concurrent.TaskResultException;
import com.task4java.util.log.Logger;

public class RestClientTest {

	private static final String TAG = RestClientTest.class.getName();

	@BeforeClass
	public static void setUpClass() throws Exception {

		InitSetup.initLogger();
		InitSetup.initServiceClient();

		Logger.instance.d(TAG, "setUpClass");

		TaskFactory.unhandledExceptions = new CallableValue<Void, TaskException>() {

			@Override
			public Void call(TaskException value) {

				if (value instanceof TaskResultException) {
					Logger.instance.d(TAG, "unhandledException: " + ((TaskResultException) value).getTask().toString());
				}

				return null;
			}
		};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {

		Logger.instance.d(TAG, "tearDownClass");

		TaskFactory.shutdown();
	}

	@Test
	public void testRestClientSimple01() throws MalformedURLException, IOException, URISyntaxException {

		RestResponse response;

		response = RestClient.instance.get(new URL("http://www.google.com/"), null, null);
		Assert.assertTrue(response.getStatusCode() == HttpStatusCodes.OK);

		List<KeyValuePair> headers = new ArrayList<KeyValuePair>();
		headers.add(new KeyValuePair("x-test1", "value1"));
		headers.add(new KeyValuePair("x-test2", "value2"));

		response = RestClient.instance.get(new URL("http://www.google.com/"), headers, null);
		Assert.assertTrue(response.getStatusCode() == HttpStatusCodes.OK);

		List<KeyValuePair> params = new ArrayList<KeyValuePair>();
		params.add(new KeyValuePair("q", "java"));

		response = RestClient.instance.get(new URL("http://www.google.com/"), null, params);
		Assert.assertTrue(response.getStatusCode() == HttpStatusCodes.OK);
	}

	@Test
	public void testRestClientSimple02() throws MalformedURLException, IOException, URISyntaxException {

		RestResponse response;

		HttpStringContent content = new HttpStringContent("Testdata", "UTF-8");

		response = RestClient.instance.post(new URL("http://www.bing.com/"), null, null, content);
		Assert.assertTrue(response.getStatusCode() == HttpStatusCodes.OK);
	}
}
