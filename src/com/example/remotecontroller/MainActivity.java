package com.example.remotecontroller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static EditText showtext;
	private static Button connect, clear, play, stop, prevpage, nextpage;
	private ObjectInputStream fromServer;
	private OutputStream outputStream;
	private ObjectOutputStream fromClient;
	private final static int F5 = 0;
	private final static int RIGHT = 1;
	private final static int LEFT = 2;
	private final static int ESC = 3;
	Socket socket;
	Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showtext = (EditText) findViewById(R.id.showtext);
		connect = (Button) findViewById(R.id.connect);
		connect.setOnClickListener(btnOnclickListener);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(btnOnclickListener);
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(btnOnclickListener);
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(btnOnclickListener);
		prevpage = (Button) findViewById(R.id.prevpage);
		prevpage.setOnClickListener(btnOnclickListener);
		nextpage = (Button) findViewById(R.id.nextpage);
		nextpage.setOnClickListener(btnOnclickListener);
		vibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
	}

	OnClickListener btnOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.connect:
				Thread clientthread = new Thread(clientSocket);
				clientthread.start();
				try {
					vibrator.vibrate(100);
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				break;
			case R.id.clear:
				vibrator.vibrate(100);
				showtext.setText("");
				break;
			case R.id.play:
				int play = 0;
				// Choices choice = new Choices(F5);
				try {
					vibrator.vibrate(100);
					outputStream.write(play);
					// fromClient.writeObject(choice);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.stop:
				int stop = 3;
				// Choices choice1 = new Choices(ESC);
				try {
					vibrator.vibrate(100);
					outputStream.write(stop);
					// fromClient.writeObject(choice1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.prevpage:
				int prevpage = 1;
				// Choices choice2 = new Choices(RIGHT);
				try {
					vibrator.vibrate(100);
					outputStream.write(prevpage);
					// fromClient.writeObject(choice2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.nextpage:
				int nextpage = 2;
				// Choices choice3 = new Choices(LEFT);
				try {
					vibrator.vibrate(100);
					outputStream.write(nextpage);
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
			final String IP = showtext.getText().toString();
			MainActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,
							IP + "       Connecting......", Toast.LENGTH_SHORT)
							.show();
				}
			});
			try {
				if (showtext.getText() != null) {
					int serverPort = 1025;
					socket = new Socket();
					InetSocketAddress isa = new InetSocketAddress(IP, serverPort);
					socket.connect(isa);
					MainActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "Connected",
									Toast.LENGTH_SHORT).show();
						}
					});
					outputStream = socket.getOutputStream();
					fromClient = new ObjectOutputStream(
							socket.getOutputStream());
					fromServer = new ObjectInputStream(socket.getInputStream());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MainActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this,
								"Client: Server Not Found", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			int exit = 4;
			try {
				outputStream.write(exit);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("exit app");
			builder.setMessage("You will exit the app...");
			// builder.setIcon(R.drawable.stat_sys_warning);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
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
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}
