/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: HttpContent.java
 */

package com.task4java.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class HttpStringContent extends HttpContent {
	
	protected final byte[] content;
	
	public HttpStringContent(final String content, final String charsetName) throws UnsupportedEncodingException {

        super();

        this.content = content.getBytes(charsetName);
    }

	@Override
	public void writeTo(OutputStream stream) throws IOException {
			
		stream.write(this.content);
		stream.flush();
	}
}
