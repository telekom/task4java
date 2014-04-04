/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: RestClientGingerbread.java
 */
package com.task4java.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.task4java.KeyValuePair;
import com.task4java.Stopwatch;
import com.task4java.Tuple;
import com.task4java.http.HttpHeaders;
import com.task4java.http.HttpMimeTypes;
import com.task4java.http.HttpRequestMethods;
import com.task4java.http.HttpStatusCodes;
import com.task4java.net.URLBuilder;
import com.task4java.util.log.Logger;

public class RestClientGingerbread implements IRestClient {

	private static final String DID_NOT_DEFINE_AN_ACTION = "You did not define an action! REST call canceled!";

	private static final String TAG = RestClientGingerbread.class.getName();
	private static AtomicInteger _operations = new AtomicInteger( 0 );
	private static AtomicInteger _connects = new AtomicInteger(0);
	private static boolean _activateContentLog = false;

	private static Tuple<String, Integer> _proxySettings = null;
	
	public int getOperations()
	{
	    return _operations.get();
	}
	
	public void ignoreCertificateErrors()
    {
		// Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };

        // Install the all-trusting trust manager
        try {
            
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
        } 
        catch (Exception e) {
        }
    }
	
	public void activateContentLog()
	{
		_activateContentLog = true;
	}
	
	public void setProxy(String hostname, Integer port)
    {
		if (hostname == null)
        {
            _proxySettings = null;
        }
        else
        {
            _proxySettings = new Tuple<String, Integer>(hostname, port);
        }
    }
	
	public RestResponse get(URL action, List<KeyValuePair> requestHeaders, List<KeyValuePair> requestParams) throws IOException {

		return execute(HttpRequestMethods.GET, action, requestHeaders, requestParams, null);
	}

	public RestResponse post(URL action, List<KeyValuePair> requestHeaders, List<KeyValuePair> requestParams, HttpContent requestBody) throws IOException {

		return execute(HttpRequestMethods.POST, action, requestHeaders, requestParams, requestBody);
	}
	
	public RestResponse delete(URL action, List<KeyValuePair> requestHeaders, List<KeyValuePair> requestParams) throws IOException, URISyntaxException {
		
		return execute(HttpRequestMethods.DELETE, action, requestHeaders, requestParams, null);
	}

	public RestResponse put(URL action, List<KeyValuePair> requestHeaders, List<KeyValuePair> requestParams, HttpContent requestBody) throws IOException, URISyntaxException {
		
		return execute(HttpRequestMethods.PUT, action, requestHeaders, requestParams, requestBody);
	}
	
	private static RestResponse execute(HttpRequestMethods requestMethod, URL action, List<KeyValuePair> requestHeaders, List<KeyValuePair> requestParams, HttpContent requestEntity)
			throws IOException {

		HttpURLConnection connection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		int responseLength = -1;
		
		Stopwatch watch = new Stopwatch();
        watch.start();

		try {

		    if (action == null) {
				Logger.instance.e(TAG, DID_NOT_DEFINE_AN_ACTION);

				throw new IllegalArgumentException(DID_NOT_DEFINE_AN_ACTION);
			}
    
		    int connects = _connects.incrementAndGet();
            _operations.incrementAndGet();
            		    		    
            if (_proxySettings == null)
            {
            	connection = (HttpURLConnection) attachParameters(action, requestParams).openConnection();
            }
            else
            {
            	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(_proxySettings.left, _proxySettings.right));
            	connection = (HttpURLConnection) attachParameters(action, requestParams).openConnection(proxy);
            }
            
            Logger.instance.d(TAG, "Enter execute, method: " + requestMethod.toString() + ", connects: " + connects + ", url: " + connection.getURL());
			
			connection.setReadTimeout(60000);
			connection.setConnectTimeout(60000);

			switch (requestMethod) {

			case CONNECT:
				break;

			case DELETE:
				connection.setRequestMethod("DELETE");
				
				attachHeaders(connection, requestHeaders);
				connection.setRequestProperty(HttpHeaders.AcceptEncoding, HttpMimeTypes.Encoding_Gzip);
				
                break;
				
			case GET:
				
				attachHeaders(connection, requestHeaders);
				connection.setRequestProperty(HttpHeaders.AcceptEncoding, HttpMimeTypes.Encoding_Gzip);
				
				connection.connect();

				break;

			case HEAD:
				break;

			case OPTIONS:
				break;

			case PATCH:
				break;

			case POST:
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);

                attachHeaders(connection, requestHeaders);
                connection.setRequestProperty(HttpHeaders.AcceptEncoding, HttpMimeTypes.Encoding_Gzip);
                
                connection.connect();
                
                // get the output stream to force a content length header
                outputStream = connection.getOutputStream();
                
				if (requestEntity != null){
				    requestEntity.writeTo(outputStream);
				}

				break;

			case PUT:
				connection.setRequestMethod("PUT");
				connection.setDoOutput(true);

                attachHeaders(connection, requestHeaders);
                connection.setRequestProperty(HttpHeaders.AcceptEncoding, HttpMimeTypes.Encoding_Gzip);
                
                connection.connect();
                
                // get the output stream to force a content length header
                outputStream = connection.getOutputStream();
                
				if (requestEntity != null){
				    requestEntity.writeTo(outputStream);
				}
								
				break;

			case TRACE:
				break;

			case UNDEFINED:
                break;
			}

			if (outputStream != null)
			{
				outputStream.close();
				outputStream = null;
			}
			
			if (_activateContentLog)
			{
				for(Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
					
					for(String value : header.getValue())
					{
						Logger.instance.d(TAG, header.getKey() + ":" + value);
					}
	            }
			}
									
			RestResponse response = new RestResponse(connection.getResponseCode());
								
            if (response.getStatusCode() < HttpStatusCodes.OK || response.getStatusCode() >= HttpStatusCodes.BadRequest)
            {
            	inputStream = connection.getErrorStream();
            }
            else
            {
            	inputStream = connection.getInputStream();
            }
		            
			if (inputStream != null) {
				
				BufferedReader rd = null;

				String contentEncodingHeader = connection.getHeaderField(HttpHeaders.ContentEncoding);

				if ((contentEncodingHeader != null)	&& (contentEncodingHeader.contains(HttpMimeTypes.Encoding_Gzip))) {

					rd = new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream)));
					
				} else {
					
					rd = new BufferedReader(new InputStreamReader(inputStream));
				}

				StringWriter sw = new StringWriter();

				char[] buffer = new char[1024 * 4];
				int n = 0;

				while (-1 != (n = rd.read(buffer))) {
					sw.write(buffer, 0, n);
				}

				response.setResponseData(sw.toString());
				responseLength = response.getResponseData().length();
			}
            
			if (_activateContentLog)
			{
				Logger.instance.d(TAG, "RestResponse: " + response.toString());
			}
			
			return response;

		} finally {

			int connects = _connects.decrementAndGet();
            
            watch.stop();
            
            if (connection != null)
            {
            	Logger.instance.d(TAG, "Leave execute, connects: " + connects + ", time: " + watch.getElapsedMilliseconds() + ", length: " + responseLength + ", url: " + connection.getURL());
            }
                
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}

			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}

	private static void attachHeaders(HttpURLConnection connection, List<KeyValuePair> requestHeaders) {

		if (requestHeaders != null) {
			for (KeyValuePair header : requestHeaders) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}
		}
	}

	private static URL attachParameters(URL action, List<KeyValuePair> requestParams) throws MalformedURLException, UnsupportedEncodingException {

		URLBuilder urlBuilder = new URLBuilder(action);

		urlBuilder.addQueryParts(requestParams);

		return urlBuilder.getURL();

	}
}
