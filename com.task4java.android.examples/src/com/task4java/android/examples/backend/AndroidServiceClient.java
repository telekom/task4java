/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: AndroidServiceClient.java
 */

package com.task4java.android.examples.backend;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

import com.task4java.Tuple;
import com.task4java.android.examples.App;
import com.task4java.data.backend.IServiceClient;
import com.task4java.data.backend.model.MainMenuAnnotation;
import com.task4java.util.concurrent.Task;
import com.task4java.util.concurrent.TaskFactory;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class AndroidServiceClient implements IServiceClient {

	@Override
	public Task<List<Tuple<MainMenuAnnotation, Class<?>>>> getMainMenu() {

		return TaskFactory.startNew(new Callable<List<Tuple<MainMenuAnnotation, Class<?>>>>() {

			@Override
			public List<Tuple<MainMenuAnnotation, Class<?>>> call() throws Exception {

				String packageName = App.getAppContext().getPackageName();

				String apkName = App.getAppContext().getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
				DexFile dexFile = new DexFile(apkName);

				PathClassLoader classLoader = new PathClassLoader(apkName, Thread.currentThread().getContextClassLoader());

				List<Tuple<MainMenuAnnotation, Class<?>>> classes = new ArrayList<Tuple<MainMenuAnnotation, Class<?>>>();
				Enumeration<String> entries = dexFile.entries();

				while (entries.hasMoreElements()) {

					String entry = entries.nextElement();

					if (entry.startsWith(packageName)) {

						Class<?> entryClass = classLoader.loadClass(entry);

						if (entryClass != null) {

							Annotation[] annotations = entryClass.getAnnotations();

							for (Annotation annotation : annotations) {
								if (annotation instanceof MainMenuAnnotation) {
									classes.add(new Tuple<MainMenuAnnotation, Class<?>>((MainMenuAnnotation) annotation, entryClass));
								}
							}
						}
					}
				}

				return classes;
			}
		});
	}

}
