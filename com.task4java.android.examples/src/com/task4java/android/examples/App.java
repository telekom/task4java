/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: App.java
 */

package com.task4java.android.examples;

import com.task4java.android.examples.backend.AndroidServiceClient;
import com.task4java.android.util.concurrent.HandlerExecutor;
import com.task4java.data.backend.ServiceClient;

import android.app.Application;
import android.content.Context;

public class App extends Application {

	private static Context applicationContext = null;

	public static Context getAppContext() {

		return applicationContext;
	}

	@Override
	public void onCreate() {

		super.onCreate();

		applicationContext = this.getApplicationContext();

		HandlerExecutor.init();

		ServiceClient.instance = new AndroidServiceClient();

	}
}
