package com.task4java.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

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

public class RestClientTest extends TestCase {
	
	private static final String TAG = RestClientTest.class.getName();
	
	@Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        InitSetup.initLogger();
        InitSetup.initServiceClient();
        
        TaskFactory.unhandledExceptions = new CallableValue<Void, TaskException>()
        {
            @Override
            public Void call(TaskException value)
            {
                if (value instanceof TaskResultException)
                {
                    Logger.instance.d(TAG, "unhandledException: " + ((TaskResultException)value).getTask().toString());
                }
                
                return null;
            }
        };
    }
	
	@Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        
        Logger.instance.d(TAG, "tearDown");
        
        TaskFactory.shutdown();
    }

	public void testRestClientSimple01() throws MalformedURLException, IOException, URISyntaxException
	{
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
	
	public void testRestClientSimple02() throws MalformedURLException, IOException, URISyntaxException
	{
		RestResponse response;
		
		HttpStringContent content = new HttpStringContent("Testdata", "UTF-8");
		
		response = RestClient.instance.post(new URL("http://www.bing.com/"), null, null, content);
		Assert.assertTrue(response.getStatusCode() == HttpStatusCodes.OK);
	}
}
