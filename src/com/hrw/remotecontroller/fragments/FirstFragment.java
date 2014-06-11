package com.hrw.remotecontroller.fragments;

import com.example.remotecontroller.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FirstFragment extends Fragment {
	TextView welcome;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		welcome = (TextView) getView().findViewById(R.id.welcome);
		// AlphaAnimation alphaAnimation
		welcome.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.animate_welcome));
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
