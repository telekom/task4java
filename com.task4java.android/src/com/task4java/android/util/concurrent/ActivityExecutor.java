/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ActivityExecutor.java
 */

package com.task4java.android.util.concurrent;

import java.util.concurrent.Executor;

import android.app.Activity;

public class ActivityExecutor implements Executor {

	private Activity activity;
	
	public ActivityExecutor(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void execute(Runnable command) {

		if (activity != null)
		{
			activity.runOnUiThread(command);
		}
	}
}
