package com.hrw.remotecontroller.fragments;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.hrw.remotecontroller.R;
import com.navdrawer.SimpleSideDrawer;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class YoutubeControl extends Fragment {

	private Button down, up, back, forward, play, b1, b2, b3, b4, b5, b6, b7,
			b8, b9, b0;
	private OutputStream outputStream;
	SimpleSideDrawer mSlidingMenu;
	Thread client;
	Socket socket;
	String IP;
	Vibrator vibrator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_youtubecontrol,
				container, false);

		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (!socket.isClosed()) {
			try {
				outputStream.write(15);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		play = (Button) getView().findViewById(R.id.playpause);
		up = (Button) getView().findViewById(R.id.up);
		down = (Button) getView().findViewById(R.id.down);
		back = (Button) getView().findViewById(R.id.back);
		forward = (Button) getView().findViewById(R.id.forward);
		b0 = (Button) getView().findViewById(R.id.b0);
		b1 = (Button) getView().findViewById(R.id.b1);
		b2 = (Button) getView().findViewById(R.id.b2);
		b3 = (Button) getView().findViewById(R.id.b3);
		b4 = (Button) getView().findViewById(R.id.b4);
		b5 = (Button) getView().findViewById(R.id.b5);
		b6 = (Button) getView().findViewById(R.id.b6);
		b7 = (Button) getView().findViewById(R.id.b7);
		b8 = (Button) getView().findViewById(R.id.b8);
		b9 = (Button) getView().findViewById(R.id.b9);
		b0.setOnClickListener(btnOnclickListener);
		b1.setOnClickListener(btnOnclickListener);
		b2.setOnClickListener(btnOnclickListener);
		b3.setOnClickListener(btnOnclickListener);
		b4.setOnClickListener(btnOnclickListener);
		b5.setOnClickListener(btnOnclickListener);
		b6.setOnClickListener(btnOnclickListener);
		b7.setOnClickListener(btnOnclickListener);
		b8.setOnClickListener(btnOnclickListener);
		b9.setOnClickListener(btnOnclickListener);
		play.setOnClickListener(btnOnclickListener);
		back.setOnClickListener(btnOnclickListener);
		forward.setOnClickListener(btnOnclickListener);
		up.setOnClickListener(btnOnclickListener);
		down.setOnClickListener(btnOnclickListener);
		client = new Thread(clientSocket);
		client.start();
		vibrator = (Vibrator) getActivity().getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
	}

	OnClickListener btnOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.playpause:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(10);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.back:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(11);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.forward:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(12);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.up:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(13);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.down:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(14);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b0:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(0);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b1:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(1);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b2:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(2);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b3:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(3);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b4:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(4);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b5:
				// Choices choice1 = new Choices(ESC);
				try {
					vibrator.vibrate(100);
					outputStream.write(5);
					// fromClient.writeObject(choice1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b6:
				// Choices choice2 = new Choices(RIGHT);
				try {
					vibrator.vibrate(100);
					outputStream.write(6);
					// fromClient.writeObject(choice2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b7:
				// Choices choice3 = new Choices(LEFT);
				try {
					vibrator.vibrate(100);
					outputStream.write(7);
					// fromClient.writeObject(choice3);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b8:
				try {
					vibrator.vibrate(100);
					outputStream.write(8);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.b9:
				try {
					vibrator.vibrate(100);
					outputStream.write(9);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};
	Runnable clientSocket = new Runnable() {
		@Override
		public void run() {
			Intent intent = getActivity().getIntent();
			IP = intent.getStringExtra("IP");
			try {
				if (IP.length() >= 0) {
					socket = new Socket();
					InetSocketAddress isa = new InetSocketAddress(IP, 2025);
					try {
						socket.connect(isa);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(getActivity(), "Error",
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
				outputStream = socket.getOutputStream();
				outputStream.write(9);
				// fromClient = new
				// ObjectOutputStream(socket.getOutputStream());
				// fromServer = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
