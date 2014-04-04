/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: RestResponse.java
 */
package com.task4java.http.client;


public class RestResponse {

	private String responseData;
	private int statusCode;

	public RestResponse() {
	}

	public RestResponse(String responseData, int statusCode) {
		this.responseData = responseData;
		this.statusCode = statusCode;
	}

	public RestResponse(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setResponseData(String responseData)
	{
		this.responseData = responseData;
	}
	
	public String getResponseData() {
		return responseData;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public String toString()
	{
		return "StatusCode: " + ((Integer)statusCode).toString() + "; Data: " + responseData;
	}
}
