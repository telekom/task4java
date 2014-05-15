/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: MainMenuItemGroup.java
 */

package com.task4java.data.frontend.model;

import java.util.ArrayList;
import java.util.List;

public class MainMenuItemGroup {

	public String groupName;
	public List<MainMenuItem> items;

	public MainMenuItemGroup(String groupName) {

		this.groupName = groupName;
		this.items = new ArrayList<MainMenuItem>();
	}
}
