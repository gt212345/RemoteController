package com.example.remotecontroller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Fragment;
import android.content.Intent;
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
	int serverPort = 1025;
	TextView tv1, tv2, tv3;
	EditText ed1;
	Button bt1;
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
			bt1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
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
		tv1 = (TextView) getView().findViewById(R.id.textView1);
		tv2 = (TextView) getView().findViewById(R.id.textView2);
		tv3 = (TextView) getView().findViewById(R.id.textView3);
		bt1 = (Button) getView().findViewById(R.id.button1);
		ed1 = (EditText) getActivity().findViewById(R.id.editText1);
		handler = new Handler();
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						handler.post(clientSocket);
					}

				}.start();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_fourth, container, false);
	}

	Runnable clientSocket = new Runnable() {
		@Override
		public void run() {
			boolean isConnected = false;
			IP = ed1.getText().toString();
			Toast.makeText(getActivity(), IP + "       Connecting......",
					Toast.LENGTH_SHORT).show();
			try {
				// if (IP != null) {
				socket = new Socket();
				InetSocketAddress isa = new InetSocketAddress(IP, serverPort);
				socket.connect(isa);
				isConnected = true;
				Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT)
						.show();
				outputStream = socket.getOutputStream();
				fromClient = new ObjectOutputStream(socket.getOutputStream());
				// fromServer = new
				// ObjectInputStream(socket.getInputStream());
				// }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isConnected = false;
				Toast.makeText(getActivity(), "Client: Server Not Found",
						Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), "連線失敗，請重新輸入", Toast.LENGTH_SHORT)
						.show();
			}
			if (isConnected) {
				Intent i = new Intent();
				i.setClass(getActivity(), MainActivity.class);
				startActivity(i);
			}
		}
	};

}
