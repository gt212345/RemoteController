package com.hrw.remotecontroller.fragments;

import java.net.Socket;

import com.example.remotecontroller.R;
import com.hrw.remotecontroller.activities.MainActivity;
import com.hrw.remotecontroller.activities.WelcomeActivity;
import com.hrw.remotecontroller.service.SocketConnect;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FourthFragment extends Fragment {
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isfirstAnimPlayed){
			tv2.setVisibility(View.VISIBLE);
			tv1.setVisibility(View.VISIBLE);
			tv3.setVisibility(View.VISIBLE);
			bt1.setVisibility(View.VISIBLE);
		}
		if(issecAnimPlayed){
			intent.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isfirstAnimPlayed = false;
		issecAnimPlayed = false;
	}

	boolean isConnected;
	int serverPort = 1025;
	TextView tv1, tv2, tv3;
	EditText ed1;
	Button bt1, intent;
	Vibrator vibrator;
	Handler handler;
	public String IP;
	Socket socket;
	SQLiteDatabase db;
	boolean isDuplicate = false;
	boolean issecAnimPlayed;
	boolean isfirstAnimPlayed;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!isfirstAnimPlayed) {
				tv2.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome));
				tv1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome2));
				tv3.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome3));
				isfirstAnimPlayed = true;
				tv2.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.VISIBLE);
				tv3.setVisibility(View.VISIBLE);
				bt1.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getFragmentManager().findFragmentByTag("tf");
		db = ((WelcomeActivity) getActivity()).getDB();
		intent = (Button) getView().findViewById(R.id.intent);
		tv1 = (TextView) getView().findViewById(R.id.textView1);
		tv2 = (TextView) getView().findViewById(R.id.textView2);
		tv3 = (TextView) getView().findViewById(R.id.textView3);
		bt1 = (Button) getView().findViewById(R.id.button1);
		ed1 = (EditText) getActivity().findViewById(R.id.editText1);
		Thread checkConnect = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BroadcastReceiver br = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						// TODO Auto-generated method stub
						isConnected = intent.getExtras().getBoolean(
								"isConnected");
					}
				};
				IntentFilter iF = new IntentFilter(
						FourthFragment.class.getName());
				getActivity().registerReceiver(br, iF);
				while (true) {
					if (isConnected) {
						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								Cursor c = db.rawQuery("SELECT * FROM IPs",
										null);
								if (c.moveToFirst()) {
									do {
										if (ed1.getText().toString()
												.equals(c.getString(0))) {
											isDuplicate = true;
										}
									} while (c.moveToNext());
								}
								if (!isDuplicate) {
									addIP(ed1.getText().toString());
								}
								Log.w("DB", "IP added");
								Toast.makeText(getActivity(), "Connected",
										Toast.LENGTH_SHORT).show();
								if (!issecAnimPlayed) {
									intent.startAnimation(AnimationUtils
											.loadAnimation(getActivity(),
													R.anim.animate_welcome));
									issecAnimPlayed = true;
									intent.setVisibility(View.VISIBLE);
								}
							}
						});
						break;
					}
				}
			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Connecting......",
						Toast.LENGTH_SHORT).show();
				Intent socketConnect = new Intent(getActivity(),
						SocketConnect.class);
				socketConnect.putExtra("IP", ed1.getText().toString());
				v.getContext().startService(socketConnect);
			}
		});
		intent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getActivity(), MainActivity.class);
				it.putExtra("IP", ed1.getText().toString());
				startActivity(it);
				Intent socketConnect = new Intent(getActivity(),
						SocketConnect.class);
				v.getContext().stopService(socketConnect);
			}
		});
		checkConnect.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_fourth, container, false);
	}

	private void addIP(String IP) {
		ContentValues cv = new ContentValues(1);
		cv.put("IP", IP);
		db.insert("IPs", null, cv);
	}

}
