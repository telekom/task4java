/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: HandlerExecutor.java
 */

package com.task4java.android.util.concurrent;

import java.util.concurrent.Executor;

import android.os.Handler;

public class HandlerExecutor implements Executor {

	private static Handler mHandler;
	private static Thread mUiThread;
	
	public static void init()
	{
		mHandler = new Handler();
		mUiThread = Thread.currentThread();
	}
	
	@Override
	public void execute(Runnable command) {

		if (Thread.currentThread() != mUiThread)
		{
			mHandler.post(command);
		}
		else
		{
			command.run();
		}
	}
}
