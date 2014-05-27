package com.example.remotecontroller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	ImageView image2;
	Button bt1;
	TextView second;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_second, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		second.setVisibility(View.INVISIBLE);
		super.onPause();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			second.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			second.setVisibility(View.VISIBLE);
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		image2 = (ImageView) getView().findViewById(R.id.image2);
		bt1 = (Button) getView().findViewById(R.id.bt1);
		second = (TextView) getView().findViewById(R.id.Second);
	}
	
	public void wifiopen(){
		Intent it = 
				new Intent(Settings.ACTION_WIFI_SETTINGS);
		startActivity(it);
	}
}
