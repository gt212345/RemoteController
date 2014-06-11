package com.hrw.remotecontroller.fragments;

import java.util.ArrayList;

import com.example.remotecontroller.R;
import com.hrw.remotecontroller.activities.WelcomeActivity;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdFragment extends Fragment {

	TextView tv1, tv2, tv3, tv4;
	static EditText ed1;
	Button bt1, usedb;
	Bundle bundle;
	String IP;
	SQLiteDatabase db;
	PopupWindow mpopupwindow;
	ListView iplist;
	private ArrayList<String> IPs;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			usedb.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			tv1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome));
			tv2.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			tv4.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome3));
			tv1.setVisibility(View.VISIBLE);
			tv2.setVisibility(View.VISIBLE);
			tv4.setVisibility(View.VISIBLE);
			usedb.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.fragment_third, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		db = ((WelcomeActivity) getActivity()).getDB();
		tv1 = (TextView) getView().findViewById(R.id.textView1);
		tv2 = (TextView) getView().findViewById(R.id.textView2);
		tv3 = (TextView) getView().findViewById(R.id.textView3);
		tv4 = (TextView) getView().findViewById(R.id.textView4);
		usedb = (Button) getView().findViewById(R.id.useDB);
		usedb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initPopwindow();
			}
		});
		ed1 = (EditText) getView().findViewById(R.id.editText1);
		ed1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				tv3.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome2));
				bt1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome2));
				tv3.setVisibility(View.VISIBLE);
				bt1.setVisibility(View.VISIBLE);
			}
		});
		bt1 = (Button) getView().findViewById(R.id.button1);
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "IP位置已存取", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	private void initPopwindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View popupWindowlayout = layoutInflater
				.inflate(R.layout.popup_ip, null);
		IPs = new ArrayList<String>();
		Cursor c = db.rawQuery("SELECT * FROM IPs", null);
		if (c.moveToFirst()) {
			do {
				IPs.add(c.getString(0));
			} while (c.moveToNext());
		}
		if (!IPs.isEmpty()) {
			mpopupwindow = new PopupWindow(popupWindowlayout, 200, 300, true);
			// mpopupwindow.setAnimationStyle(R.style.);
			mpopupwindow.setBackgroundDrawable(new BitmapDrawable(
					getResources(), ""));
			mpopupwindow.update();
			Log.w("IPdb", IPs.get(0));
			iplist = (ListView) popupWindowlayout.findViewById(R.id.IPs);
			ArrayAdapter<String> aad = new ArrayAdapter<String>(getActivity()
					.getApplicationContext(), R.layout.list_ip, IPs);
			iplist.setAdapter(aad);
			mpopupwindow.showAtLocation(getView(), Gravity.RIGHT, 0, 0);
			iplist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ed1.setText(iplist.getItemAtPosition(position).toString());
					Log.w("onItemclick", "called");
					mpopupwindow.dismiss();
				}
			});
		}
	}

}
