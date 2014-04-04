/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: URLBuilder.java
 */
package com.task4java.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.task4java.KeyValuePair;
import com.task4java.StringUtils;

public class URLBuilder {

	private URL url;
	private List<KeyValuePair> queryParts = new ArrayList<KeyValuePair>();

	public URLBuilder(URL currentUrl) throws UnsupportedEncodingException {

		url = currentUrl;

		String query = url.getQuery();

		if ((query != null) && (query != "")) {

			StringTokenizer st = new StringTokenizer(url.getQuery(), "&", false);
			while (st.hasMoreElements()) {
				
				// First Pass to retrieve the "paramName=paramValue" combo
				String paramValueToken = st.nextElement().toString();

				StringTokenizer stParamVal = new StringTokenizer(paramValueToken, "=", false);

				String paramName = null;
				String paramValue = null;
				int i = 0;
				
				while (stParamVal.hasMoreElements()) {
					// Second pass to separate the "paramName" and "paramValue".
					// 1st token is paramName
					// 2nd token is paramValue
					String separatedToken = stParamVal.nextElement().toString();

					if (i == 0) {
						// This indicates that it is the paramName
						paramName = separatedToken;
					} else {
						// This will hold paramValue
						paramValue = separatedToken;
					}
					i++;
				}

				if (paramName != null) {
					addQueryPart(paramName, paramValue);
				}
			}
		}

	}

	public void addQueryParts(List<KeyValuePair> parameters) {

		if (parameters != null) {
			for (KeyValuePair parameter : parameters) {
				addQueryPart(parameter);
			}
		}
	}

	public void addQueryPart(KeyValuePair parameter) {

		addQueryPart(parameter.getKey(), parameter.getValue());
	}

	public void addQueryPart(String paramName, String paramValue) {

		KeyValuePair part = new KeyValuePair(paramName, paramValue);

		queryParts.add(part);
	}

	public void removeQueryParts(String paramName) {

		List<KeyValuePair> partsRemove = new ArrayList<KeyValuePair>();
		
		for (KeyValuePair part : queryParts) {
			
			if (part.getKey().equals(paramName))
			{
				partsRemove.add(part);
			}
		}
		
		queryParts.removeAll(partsRemove);
	}
	
	public static String getQuery(List<KeyValuePair> queryParts) throws UnsupportedEncodingException {
		
		StringBuilder queryBld = new StringBuilder();
		
		for (KeyValuePair part : queryParts) {
			if (!StringUtils.IsNullOrEmpty(part.getKey()) && part.getValue() != null)  {
				queryBld.append(queryBld.length() == 0 ? "" : "&");
				queryBld.append(part.getKey());
				queryBld.append("=");
				queryBld.append(part.getValue());
			}
		}
		
		return queryBld.toString();
	}

	public URL getURL() throws MalformedURLException, UnsupportedEncodingException {

		String query = getQuery(queryParts);
		String file = url.getPath();
		
		if (!StringUtils.IsNullOrEmpty(query)) {
			file += "?" + query;
		}

		URL result = new URL(url.getProtocol(), url.getHost(), url.getPort(), file);

		return result;
	}
}
