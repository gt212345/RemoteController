package com.hrw.remotecontroller.fragments;

import com.hrw.remotecontroller.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FirstFragment extends Fragment {
	TextView welcome;
	private boolean isAnimPlayed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isAnimPlayed = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (!isAnimPlayed) {
			welcome = (TextView) getView().findViewById(R.id.welcome);
			// AlphaAnimation alphaAnimation
			welcome.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome));
			isAnimPlayed = true;
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
