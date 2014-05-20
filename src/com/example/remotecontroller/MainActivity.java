package com.example.remotecontroller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.navdrawer.SimpleSideDrawer;

import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static Button play, stop, prevpage, nextpage;
	private TextView ppt, mouse;
	private ObjectInputStream fromServer;
	private OutputStream outputStream;
	private ObjectOutputStream fromClient;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ppt = (TextView) findViewById(R.id.PPT);
		mouse = (TextView) findViewById(R.id.Mouse);
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(btnOnclickListener);
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(btnOnclickListener);
		prevpage = (Button) findViewById(R.id.prevpage);
		prevpage.setOnClickListener(btnOnclickListener);
		nextpage = (Button) findViewById(R.id.nextpage);
		nextpage.setOnClickListener(btnOnclickListener);
		mSlidingMenu = new SimpleSideDrawer(this);
		mSlidingMenu.setLeftBehindContentView(R.layout.behind_left);
//		ppt.setOnClickListener(txvOnclickListener);
//		mouse.setOnClickListener(txvOnclickListener);
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		client = new Thread(clientSocket);
		client.start();
		vibrator = (Vibrator) getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		mSlidingMenu.toggleLeftDrawer();
		return super.onOptionsItemSelected(item);
	}

	OnClickListener txvOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.PPT:
				break;
			}

		}
	};
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
			Intent intent = getIntent();
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
						MainActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(MainActivity.this, "Error",
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("exit app");
			builder.setMessage("You will exit the app...");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
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
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}
