/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: ExpandableListAdapter.java
 */

package com.task4java.android.examples.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.task4java.android.examples.R;
import com.task4java.data.frontend.model.MainMenuItem;
import com.task4java.data.frontend.model.MainMenuItemGroup;
import com.task4java.data.frontend.model.MainMenuItemGroupList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private MainMenuItemGroupList _listItems;

	public ExpandableListAdapter(Context context, MainMenuItemGroupList itemList) {

		this._context = context;
		this._listItems = itemList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {

		return this._listItems.get(groupPosition).items.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = ((MainMenuItem) getChild(groupPosition, childPosition)).title;

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return this._listItems.get(groupPosition).items.size();
	}

	@Override
	public Object getGroup(int groupPosition) {

		return this._listItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return this._listItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		String headerTitle = ((MainMenuItemGroup) getGroup(groupPosition)).groupName;

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}
}