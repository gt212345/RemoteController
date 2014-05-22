package com.example.remotecontroller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.navdrawer.SimpleSideDrawer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PptControlFragment extends Fragment {
	private static Button play, stop, prevpage, nextpage;
	private OutputStream outputStream;
	private final static int F5 = 0;
	private final static int Forward = 2;
	private final static int Backward = 1;
	private final static int ESC = 3;
	SimpleSideDrawer mSlidingMenu;
	Thread client;
	Socket socket;
	String IP;
	Vibrator vibrator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_pptcontrol,
				container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		play = (Button) getView().findViewById(R.id.play);
		play.setOnClickListener(btnOnclickListener);
		stop = (Button) getView().findViewById(R.id.stop);
		stop.setOnClickListener(btnOnclickListener);
		prevpage = (Button) getView().findViewById(R.id.prevpage);
		prevpage.setOnClickListener(btnOnclickListener);
		nextpage = (Button) getView().findViewById(R.id.nextpage);
		nextpage.setOnClickListener(btnOnclickListener);
		client = new Thread(clientSocket);
		client.start();
		vibrator = (Vibrator) getActivity().getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
	}

	OnClickListener btnOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.play:
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(F5);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.stop:
				// Choices choice1 = new Choices(ESC);
				try {
					vibrator.vibrate(100);
					outputStream.write(ESC);
					// fromClient.writeObject(choice1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.prevpage:
				// Choices choice2 = new Choices(RIGHT);
				try {
					vibrator.vibrate(100);
					outputStream.write(Backward);
					// fromClient.writeObject(choice2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.nextpage:
				// Choices choice3 = new Choices(LEFT);
				try {
					vibrator.vibrate(100);
					outputStream.write(Forward);
					// fromClient.writeObject(choice3);
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
					InetSocketAddress isa = new InetSocketAddress(IP, 1025);
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
				play.setClickable(true);
				stop.setClickable(true);
				prevpage.setClickable(true);
				nextpage.setClickable(true);
				outputStream = socket.getOutputStream();
				// fromClient = new
				// ObjectOutputStream(socket.getOutputStream());
				// fromServer = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void myOnKeyDown(int keyCode) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("exit app");
		builder.setMessage("You will exit the app...");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					int exit = 4;
					outputStream.write(exit);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.show();

	}
}
