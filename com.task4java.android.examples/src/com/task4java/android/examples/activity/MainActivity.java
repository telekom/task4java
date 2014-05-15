/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: MainActivity.java
 */

package com.task4java.android.examples.activity;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;

import com.task4java.android.activity.BaseActivity;
import com.task4java.android.annotation.Annotations.ViewMapping;
import com.task4java.android.examples.R;
import com.task4java.android.util.concurrent.HandlerExecutor;
import com.task4java.data.frontend.ApplicationClient;
import com.task4java.data.frontend.model.MainMenuItem;
import com.task4java.data.frontend.model.MainMenuItemGroupList;
import com.task4java.util.concurrent.CallableTask;
import com.task4java.util.concurrent.Task;

public class MainActivity extends BaseActivity {

	@ViewMapping(resourceId = R.id.expandableListView1)
	private ExpandableListView expandableListView;

	@ViewMapping(resourceId = R.id.busy)
	private LinearLayout busyIndicator;

	ExpandableListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initializeView();

		if (expandableListView != null) {

			expandableListView.setOnChildClickListener(new OnChildClickListener() {
				
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
					MainMenuItem item = ((ExpandableListAdapter)parent.getExpandableListAdapter()).getChild(groupPosition, childPosition);
					
					Intent itemIntent = new Intent(MainActivity.this, item.targetClass);
					startActivity(itemIntent);
					
					return false;
				}
			});
		}

		loadData();
	}

	private void loadData() {

		Task.<Void> fromResult(null)
		.continueWith(taskPreLoadData(), HandlerExecutor.getCurrent())
		.continueWith(taskLoadData())
		.unwrap()
		.continueWith(taskPostLoadData(), HandlerExecutor.getCurrent())
		.continueWith(MainActivity.<Void> taskHandleError(), HandlerExecutor.getCurrent());

	}

	private static <VResult> CallableTask<VResult, VResult> taskHandleError() {

		return new CallableTask<VResult, VResult>() {

			@Override
			public VResult call(Task<VResult> task) throws Exception {

				try {
					return task.get();
				} catch (ExecutionException ex) {
					Exception innerEx = Task.findExceptionExcept(ex, ExecutionException.class);

					if (innerEx != null) {

						innerEx.printStackTrace();
					} else {

						ex.printStackTrace();
					}
				}

				return task.get();
			}
		};
	}

	private CallableTask<Void, Void> taskPostLoadData() {

		return new CallableTask<Void, Void>() {

			@Override
			public Void call(Task<Void> task) throws Exception {

				busyIndicator.setVisibility(View.GONE);
				expandableListView.setVisibility(View.VISIBLE);

				// for exception handling
				task.get();

				return null;
			}
		};
	}

	private CallableTask<Task<Void>, Void> taskLoadData() {

		return new CallableTask<Task<Void>, Void>() {

			@Override
			public Task<Void> call(Task<Void> task) throws Exception {

				// for exception handling
				task.get();

				Thread.sleep(2000);

				return ApplicationClient
						.getMainMenu()
						.continueWith(new CallableTask<Void, MainMenuItemGroupList>() {

					@Override
					public Void call(Task<MainMenuItemGroupList> task) throws Exception {

						listAdapter = new ExpandableListAdapter(MainActivity.this, task.get());

						expandableListView.setAdapter(listAdapter);

						return null;
					}
				}, HandlerExecutor.getCurrent());
			}
		};
	}

	private CallableTask<Void, Void> taskPreLoadData() {

		return new CallableTask<Void, Void>() {

			@Override
			public Void call(Task<Void> task) throws Exception {

				// for exception handling
				task.get();

				expandableListView.setVisibility(View.GONE);
				busyIndicator.setVisibility(View.VISIBLE);

				return null;
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
