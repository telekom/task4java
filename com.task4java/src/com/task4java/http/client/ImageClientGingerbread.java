/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ImageClientGingerbread.java
 */
package com.task4java.http.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.task4java.Stopwatch;
import com.task4java.Tuple;
import com.task4java.http.HttpStatusCodes;
import com.task4java.net.URLBuilder;
import com.task4java.util.log.Logger;

public class ImageClientGingerbread implements IImageClient
{
	private static final String DID_NOT_DEFINE_AN_ACTION = "You did not define an action! Image call canceled!";
	
    private static final String TAG = ImageClient.class.getName();
    private static AtomicInteger _operations = new AtomicInteger(0);
    private static AtomicInteger _connects = new AtomicInteger(0);
    
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
    
    public ImageResponse getImage(URL action) throws MalformedURLException, IOException
    {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        
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
            
            Logger.instance.d(TAG, "Enter getImage, connects: " + connects + ", url: " + action);
                        
            if (_proxySettings == null)
            {
            	connection = (HttpURLConnection) new URLBuilder(action).getURL().openConnection(); 
            }
            else
            {
            	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(_proxySettings.left, _proxySettings.right));
            	connection = (HttpURLConnection) new URLBuilder(action).getURL().openConnection(proxy); 
            }
            
            connection.setReadTimeout(60000);
			connection.setConnectTimeout(60000);
            
            connection.connect();
            
            ImageResponse response = new ImageResponse(connection.getResponseCode());
                        
            if (response.getStatusCode() < HttpStatusCodes.OK || response.getStatusCode() >= HttpStatusCodes.BadRequest)
            {
                Logger.instance.d(TAG, "Leave getImage with status code: " + response.getStatusCode());
                
                return response;
            }
            
            inputStream = connection.getInputStream();
            
            if (inputStream != null)
            {
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	
				byte[] buffer = new byte[1024 * 4];
				int n = 0;
				
				while (-1 != (n = inputStream.read(buffer))) {
					bos.write(buffer, 0, n);
				}
	
	            response.setResponseData(bos.toByteArray());
	            
	            responseLength = response.getResponseData().length;
            }
            
            return response;
        }
        finally
        {
        	int connects = _connects.decrementAndGet();
            
            watch.stop();
            
            Logger.instance.d(TAG, "Leave getImage, connects: " + connects + ", time: " + watch.getElapsedMilliseconds() + ", length: " + responseLength  + ", url: " + action);
        	
            if (inputStream != null)
            {
                inputStream.close();
                inputStream = null;
            }
            
            if (connection != null)
            {
                connection.disconnect();
                connection = null;
            }
        }
    }
}
