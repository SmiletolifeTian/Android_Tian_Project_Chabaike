package com.tian.project.chabaike.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.fragment.ContentFragment;
import com.tian.project.chabaike.fragment.HeadLineFragment;
import com.tian.project.chabaike.fragment.HomePageViewpPager;

public class MainActivity extends FragmentActivity implements TabListener,
		OnPageChangeListener {
	public static final String KEY_FRAGMENT_ID = "fragment_id";
	private ViewPager vpMain;
	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initNavi();
		initData();
	}

	private void initUI() {
		vpMain = (ViewPager) findViewById(R.id.vp_main);
	}

	private void initNavi() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.addTab(actionBar.newTab().setText("头条").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("百科").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("咨询").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("经营").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("数据").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.more)
				.setTabListener(this));
	}

	private void initData() {
		fragments = new ArrayList<Fragment>();
		Fragment f = null;
		for (int i = 0; i < getActionBar().getTabCount() - 1; i++) {
			if (i == 0) {
				f = new HeadLineFragment();
			} else {
				f = new ContentFragment();
			}

			Bundle bundle = new Bundle();
			bundle.putInt(KEY_FRAGMENT_ID, i);
			f.setArguments(bundle);
			fragments.add(f);
		}

		vpMain.setAdapter(new MyMainPagerAdapter(getSupportFragmentManager()));
		vpMain.setOnPageChangeListener(this);

	}

	/* -------------------- TabListener -------------------- */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == getActionBar().getTabCount() - 1) {
			startActivity(new Intent(this, MoreActivity.class));
		} else {
			vpMain.setCurrentItem(tab.getPosition());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	/* -------------------- TabListener -------------------- */

	/* -------------------- OnPageChangeListener -------------------- */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		if (position == getActionBar().getTabCount() - 1) {
			startActivity(new Intent(this, MoreActivity.class));
		} else {
			getActionBar().setSelectedNavigationItem(position);
		}
	}

	/* -------------------- OnPageChangeListener -------------------- */

	class MyMainPagerAdapter extends FragmentPagerAdapter {

		public MyMainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

}
