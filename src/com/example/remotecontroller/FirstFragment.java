package com.example.remotecontroller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FirstFragment extends Fragment {
	ImageView image1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		image1 = (ImageView) getView().findViewById(R.id.image1);
		// AlphaAnimation alphaAnimation
		image1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.animate_welcome));
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
