package com.hrw.remotecontroller.fragments;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.example.remotecontroller.R;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MousContFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	ObjectOutputStream oos;
	Thread client;
	Socket socket;
	String IP;
	Vibrator vibrator;
	boolean isConnected = false;
	private float originX;
	private float originY;
	private float moveX;
	private float moveY;
	private float mSolidX;
	private float mSolidY;
	int[] coordinate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_mouscont, container,
				false);
		rootView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				try {
					// when user touches the screen
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// reset deltaX and deltaY
						originX = event.getX();
						originY = event.getY();
					}
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						// moveX = event.getX() - originX;
						// moveY = event.getY() - originY;
						// if (moveX > 10) {
						// mSolidX = 3;
						// } else if (moveX < -10) {
						// mSolidX = -3;
						// }
						// if (moveY > 10) {
						// mSolidY = 3;
						// } else if (moveY < -10) {
						// mSolidY = -3;
						// }
						originX = event.getX();
						originY = event.getY();
						coordinate = new int[] { (int) originX, (int) originY };
						oos.writeObject(coordinate);

					}
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		client = new Thread(clientSocket);
		client.start();
		vibrator = (Vibrator) getActivity().getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
		while (true) {
			if (isConnected) {
				break;
			}

		}
	}

	Runnable clientSocket = new Runnable() {
		@Override
		public void run() {
			Intent intent = getActivity().getIntent();
			IP = intent.getStringExtra("IP");
			try {
				if (IP.length() >= 0) {
					socket = new Socket();
					InetSocketAddress isa = new InetSocketAddress(IP, 1025);
					try {
						socket.connect(isa);
						isConnected = true;
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
				OutputStream ops = socket.getOutputStream();
				ops.write(7);
				oos = new ObjectOutputStream(socket.getOutputStream());
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
