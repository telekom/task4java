/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: MainMenuItem.java
 */

package com.task4java.data.frontend.model;

public class MainMenuItem {

	public int priority;
	public String title;
	public Class<?> targetClass;

	public MainMenuItem(String title, Class<?> targetClass, int priority) {

		this.title = title;
		this.targetClass = targetClass;
		this.priority = priority;
	}

}
