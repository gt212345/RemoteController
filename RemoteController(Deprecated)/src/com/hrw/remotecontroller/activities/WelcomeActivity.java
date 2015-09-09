package com.hrw.remotecontroller.activities;

import com.hrw.remotecontroller.R;
import com.hrw.remotecontroller.fragments.FirstFragment;
import com.hrw.remotecontroller.fragments.FourthFragment;
import com.hrw.remotecontroller.fragments.SecondFragment;
import com.hrw.remotecontroller.fragments.ServerDoFrag;
import com.hrw.remotecontroller.fragments.ThirdFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class WelcomeActivity extends Activity {
	FragmentTransaction mFragmentTransaction = getFragmentManager()
			.beginTransaction();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Exit Application");
			builder.setMessage("The app is shutting down......");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (getFragmentManager().getBackStackEntryCount() == 0) {
								WelcomeActivity.this.finish();
							} else {
								getFragmentManager().popBackStack();
							}
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	public SQLiteDatabase db;

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
		setContentView(R.layout.activity_welcome_fragment);
		db = openOrCreateDatabase("IPdb", Context.MODE_PRIVATE, null);
		String sql = "CREATE TABLE IF NOT EXISTS IPs " + "(IP VATCHAR(32))";
		db.execSQL(sql);
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.deleteDB:
			db.delete("IPs", "IP", null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
				mFragmentTransaction.addToBackStack(null);
				break;
			case 1:
				fragment = new ServerDoFrag();
				mFragmentTransaction.addToBackStack(null);
				break;
			case 2:
				fragment = new SecondFragment();
				mFragmentTransaction.addToBackStack(null);
				break;
			case 3:
				fragment = new ThirdFragment();
				mFragmentTransaction.addToBackStack(null);
				break;
			case 4:
				fragment = new FourthFragment();
				mFragmentTransaction.addToBackStack(null);
				break;
			}

			return fragment;
		}

		public int getCount() {
			// Show 3 total pages.
			return 5;
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
			case 4:
				return new String("Step 4");
			}
			return null;
		}
	}

	public SQLiteDatabase getDB() {
		return this.db;
	}

}
