/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ImageResponse.java
 */
package com.task4java.http.client;

public class ImageResponse
{
    private byte[] responseData;
    private int statusCode;

    public ImageResponse() {
    }
    
    public ImageResponse(int statusCode) {
    	this.statusCode = statusCode;
    }

    public ImageResponse(byte[] responseData, int statusCode) {
        this.responseData = responseData;
        this.statusCode = statusCode;
    }

    public byte[] getResponseData() {
        return responseData;
    }

    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
