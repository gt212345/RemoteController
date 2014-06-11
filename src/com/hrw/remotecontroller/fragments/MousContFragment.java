package com.hrw.remotecontroller.fragments;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.remotecontroller.R;

public class MousContFragment extends Fragment implements OnGestureListener,
		OnDoubleTapListener {
	PopupWindow mpopupwindow;
	private GestureDetector mGestureDetector;

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.w("onStart", "Called");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.w("onResume", "Called");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.w("onPause", "Called");
		if (!socket.isClosed()) {
			try {
				oos.writeObject(new int[] { (int) 2000, (int) 0 });
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.w("onStop", "Called");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.w("onDestroy", "Called");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.w("onDetach", "Called");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mGestureDetector = new GestureDetector(getActivity(), this);
	}

	ObjectOutputStream oos;
	Thread client;
	Socket socket;
	String IP;
	Vibrator vibrator;
	boolean isConnected = false;

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
				boolean result = mGestureDetector.onTouchEvent(event);
				return result;
			}
		});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getView().setClickable(true);
		client = new Thread(clientSocket);
		client.start();
		vibrator = (Vibrator) getActivity().getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
		while (true) {
			if (isConnected) {
				break;
			}
		}
		initPopupwindow();
	}

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

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		try {
			oos.writeObject(new int[] { (int) 2001, 0 });
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		try {
			oos.writeObject(new int[] { (int) 2002, 0 });
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		Log.w("distanceX", String.valueOf(distanceX));
		Log.w("distanceY", String.valueOf(distanceY));
		try {
			oos.writeObject(new int[] { (int) (distanceX + 0.5),
					(int) (distanceY + 0.5) });
			Log.w("write", "Sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return true;
	}

	private void initPopupwindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View popupWindowlayout = layoutInflater
				.inflate(R.layout.popup_mouseinfo, null);
		mpopupwindow = new PopupWindow(popupWindowlayout, 400, 120, true);
		mpopupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
				""));
		popupWindowlayout.setAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_popupwindow));
		mpopupwindow.update();
		mpopupwindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
	}

}
