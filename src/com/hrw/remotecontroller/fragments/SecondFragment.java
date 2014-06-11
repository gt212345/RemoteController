package com.hrw.remotecontroller.fragments;

import com.example.remotecontroller.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isAnimPlayed){
			first.setVisibility(View.VISIBLE);
			second.setVisibility(View.VISIBLE);
		}
	}

	TextView first, second;
	boolean isAnimPlayed;

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
		isAnimPlayed = false;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!isAnimPlayed) {
				first.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), R.anim.animate_welcome));
				second.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), R.anim.animate_welcome2));
				isAnimPlayed = true;
				first.setVisibility(View.VISIBLE);
				second.setVisibility(View.VISIBLE);
			}
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		first = (TextView) getView().findViewById(R.id.First);
		second = (TextView) getView().findViewById(R.id.Second);
	}
}
