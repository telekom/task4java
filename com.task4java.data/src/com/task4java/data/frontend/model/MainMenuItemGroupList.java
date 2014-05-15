/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: MainMenuItemGroupList.java
 */

package com.task4java.data.frontend.model;

import com.task4java.IndexedList;
import com.task4java.Tuple;

public class MainMenuItemGroupList extends IndexedList<String, MainMenuItemGroup, MainMenuItemGroup> {

	private static final long serialVersionUID = 1L;

	@Override
	protected Tuple<String, MainMenuItemGroup> extractKeyValue(MainMenuItemGroup item) {

		return new Tuple<String, MainMenuItemGroup>(item.groupName, item);
	}

}
