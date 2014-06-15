package com.hrw.remotecontroller.activities;

import com.hrw.remotecontroller.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {
	// private LayoutInflater myInflater;
	Context context;
	static final String[] controlFragments = new String[] { "PowerPoint控制頁面",
			"Mouse控制頁面","Youtube控制頁面" };

	public NavDrawerListAdapter(Context ctxt) {
		this.context = ctxt;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return controlFragments.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return controlFragments[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		txtTitle.setText(controlFragments[position]);
		return convertView;
	}

}
