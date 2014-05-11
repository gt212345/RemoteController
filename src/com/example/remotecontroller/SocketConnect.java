package com.example.remotecontroller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SocketConnect extends Service {
	String IP;
	Socket socket;
	Thread socketConnect;
	private Runnable connect = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int serverPort = 1025;
			if (IP.length() >= 0) {
				Intent i = new Intent(FourthFragment.class.getName());
				socket = new Socket();
				InetSocketAddress isa = new InetSocketAddress(IP, serverPort);
				try {
					socket.connect(isa);
					i.putExtra("isConnected", true);
					sendBroadcast(i);
					socket.close();
					Log.w("Service","Socket closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					i.putExtra("isConnected", false);
					sendBroadcast(i);
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		IP = intent.getStringExtra("IP");
		socketConnect = new Thread(connect);
		socketConnect.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
