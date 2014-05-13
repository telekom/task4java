package com.task4java.android.examples;

import com.task4java.android.util.concurrent.HandlerExecutor;

import android.app.Application;

public class App extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		HandlerExecutor.init();
	}
}
