/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: BaseActivity.java
 */

package com.task4java.android.activity;

import android.app.Activity;

public abstract class BaseActivity extends Activity {

	public void initializeView() {

		ActivityUtils.InitializeView(this);
	}

}
