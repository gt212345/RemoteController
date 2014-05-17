package com.example.remotecontroller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class WelcomeActivity extends Activity {
	String IP;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
<<<<<<< HEAD
		setContentView(R.layout.activity_welcome);
		showtext = (EditText) findViewById(R.id.showtext);
		connect = (Button) findViewById(R.id.connect);
		connect.setOnClickListener(btnOnclickListener);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(btnOnclickListener);
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(btnOnclickListener);
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(btnOnclickListener);
		prevpage = (Button) findViewById(R.id.prevpage);
		prevpage.setOnClickListener(btnOnclickListener);
		nextpage = (Button) findViewById(R.id.nextpage);
		nextpage.setOnClickListener(btnOnclickListener);
		vibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		play.setClickable(false);
		stop.setClickable(false);
		prevpage.setClickable(false);
		nextpage.setClickable(false);
=======
		setContentView(R.layout.activity_welcome_fragment);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
>>>>>>> welcomepage
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new FirstFragment();
				break;
			case 1:
				fragment = new SecondFragment();
				break;
			case 2:
				fragment = new ThirdFragment();
				break;
			case 3:
				fragment = new FourthFragment();
			}

			return fragment;
		}

		@Override
<<<<<<< HEAD
		public void run() {
			final String IP = showtext.getText().toString();
			WelcomeActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(WelcomeActivity.this,
							IP + "       Connecting......", Toast.LENGTH_SHORT)
							.show();
				}
			});
			try {
				if (showtext.getText() != null) {
					int serverPort = 1025;
					socket = new Socket(IP, serverPort);
					WelcomeActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(WelcomeActivity.this, "Connected",
									Toast.LENGTH_SHORT).show();
						}
					});
					play.setClickable(true);
					stop.setClickable(true);
					prevpage.setClickable(true);
					nextpage.setClickable(true);
					outputStream = socket.getOutputStream();
					fromClient = new ObjectOutputStream(
							socket.getOutputStream());
					fromServer = new ObjectInputStream(socket.getInputStream());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				WelcomeActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(WelcomeActivity.this,
								"Client: Server Not Found", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
=======
		public int getCount() {
			// Show 3 total pages.
			return 4;
>>>>>>> welcomepage
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return null;
			case 1:
				return new String("Step 1");
			case 2:
				return new String("Step 2");
			case 3:
				return new String("Step 3");
			}
			return null;
		}
	}

}
