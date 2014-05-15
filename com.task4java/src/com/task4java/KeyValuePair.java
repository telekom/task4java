/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: KeyValuePair.java
 */
package com.task4java;

import java.util.AbstractMap;

public class KeyValuePair extends AbstractMap.SimpleEntry<String, String> {

	private static final long serialVersionUID = 1L;

	public KeyValuePair(String key, String value) {
		super(key, value);
	}
}
