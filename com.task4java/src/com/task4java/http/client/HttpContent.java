/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: HttpContent.java
 */

package com.task4java.http.client;

import java.io.IOException;
import java.io.OutputStream;

public abstract class HttpContent {

	public abstract void writeTo(OutputStream stream) throws IOException;
}
