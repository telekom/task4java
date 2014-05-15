/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ApplicationClient.java
 */

package com.task4java.data.frontend;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.task4java.Tuple;
import com.task4java.data.backend.ServiceClient;
import com.task4java.data.backend.model.MainMenuAnnotation;
import com.task4java.data.frontend.model.MainMenuItem;
import com.task4java.data.frontend.model.MainMenuItemGroup;
import com.task4java.data.frontend.model.MainMenuItemGroupList;
import com.task4java.util.concurrent.CallableTask;
import com.task4java.util.concurrent.Task;

public class ApplicationClient {

	public static Task<MainMenuItemGroupList> getMainMenu() {

		return ServiceClient.instance
				.getMainMenu()
				.continueWith(new CallableTask<MainMenuItemGroupList, List<Tuple<MainMenuAnnotation, Class<?>>>>() {

			@Override
			public MainMenuItemGroupList call(Task<List<Tuple<MainMenuAnnotation, Class<?>>>> task) throws Exception {

				MainMenuItemGroupList result = new MainMenuItemGroupList();

				for (Tuple<MainMenuAnnotation, Class<?>> item : task.get()) {

					String groupName = item.left.group();

					MainMenuItemGroup group = result.getItem(groupName, null);

					if (group == null) {
						group = new MainMenuItemGroup(groupName);
						result.add(group);
						result.refresh();
					}

					group.items.add(new MainMenuItem(item.left.title(), item.right, item.left.priority()));
				}

				for (MainMenuItemGroup group : result) {

					Collections.sort(group.items, new Comparator<MainMenuItem>() {

						@Override
						public int compare(MainMenuItem item1, MainMenuItem item2) {

							return ((Integer) item1.priority).compareTo(item2.priority);
						}

					});
				}

				return result;
			}

		});

	}
}