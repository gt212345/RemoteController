package com.example.remotecontroller;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdFragment extends Fragment {
	TextView tv1, tv2, tv3, tv4;
	static EditText ed1;
	Button bt1;
	Bundle bundle;
	String IP;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			tv1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome));
			tv2.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome2));
			tv4.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.animate_welcome3));
			tv1.setVisibility(View.VISIBLE);
			tv2.setVisibility(View.VISIBLE);
			tv4.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.fragment_third, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tv1 = (TextView) getView().findViewById(R.id.textView1);
		tv2 = (TextView) getView().findViewById(R.id.textView2);
		tv3 = (TextView) getView().findViewById(R.id.textView3);
		tv4 = (TextView) getView().findViewById(R.id.textView4);
		ed1 = (EditText) getView().findViewById(R.id.editText1);
		ed1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				tv3.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome2));
				bt1.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						R.anim.animate_welcome2));
				tv3.setVisibility(View.VISIBLE);
				bt1.setVisibility(View.VISIBLE);
			}
		});
		bt1 = (Button) getView().findViewById(R.id.button1);
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "IP位置已存取", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
