package com.example.remotecontroller;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.os.Build;

public class SecondFragment extends Fragment {
	TextView first, second;

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
		first.setVisibility(View.INVISIBLE);
		second.setVisibility(View.INVISIBLE);
		super.onPause();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			first.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome));
			second.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			first.setVisibility(View.VISIBLE);
			second.setVisibility(View.VISIBLE);
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		first = (TextView) getView().findViewById(R.id.First);
		second = (TextView) getView().findViewById(R.id.Second);
	}
}
