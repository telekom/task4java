/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StringUtils.java
 */
package com.task4java;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {

	public static boolean IsNullOrEmpty(String string) {

		return (string == null || string.length() == 0);

	}

	public static String normalizeString(String text) {

		return text.replaceFirst("^ä", "a").replaceFirst("^ö", "o").replaceFirst("^ü", "u").replaceFirst("^Ä", "A").replaceFirst("^Ö", "O")
				.replaceFirst("^Ü", "U").toUpperCase();

	}
	
	public static String join(Collection<?> s, String delimiter) {
		return join(s, delimiter, null);
	}
	
	public static String join(Collection<?> s, String delimiter, String prefix) {
		
	     StringBuilder builder = new StringBuilder();
	     Iterator<?> iter = s.iterator();
	     
	     while (iter.hasNext()) {
	    	 
	    	 if (!IsNullOrEmpty(prefix)) {
	    		 builder.append(prefix);
	    	 }
	         builder.append(iter.next());
	    	
	         if (!iter.hasNext()) {
	           break;                  
	         }
	         builder.append(delimiter);
	     }
	     return builder.toString();
	 }
}
