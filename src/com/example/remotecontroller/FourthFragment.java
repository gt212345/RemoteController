package com.example.remotecontroller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FourthFragment extends Fragment {
	boolean isConnected;
	int serverPort = 1025;
	TextView tv1, tv2, tv3;
	EditText ed1;
	Button bt1, intent;
	Vibrator vibrator;
	private OutputStream outputStream;
	private ObjectOutputStream fromClient;
	Handler handler;
	public String IP;
	Socket socket;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			tv2.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome));
			tv1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			tv3.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome3));
			tv2.setVisibility(View.VISIBLE);
			tv1.setVisibility(View.VISIBLE);
			tv3.setVisibility(View.VISIBLE);
			bt1.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getFragmentManager().findFragmentByTag("tf");
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
								Toast.makeText(getActivity(), "Connected",
										Toast.LENGTH_SHORT).show();
								intent.setVisibility(View.VISIBLE);
								intent.startAnimation(AnimationUtils
										.loadAnimation(getActivity(),
												R.anim.animate_welcome));
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
				it.putExtra("IP", IP);
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

}
