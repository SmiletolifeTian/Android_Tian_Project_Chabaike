package com.tian.project.chabaike.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.fragment.OtherFragment;
import com.tian.project.chabaike.fragment.HeadLineFragment;
import com.tian.project.chabaike.fragment.HomePageViewpPager;
import com.tian.project.chabaike.task.UpdateTask;

public class MainActivity extends FragmentActivity implements TabListener,
		OnPageChangeListener {
	public static final String KEY_NEED_UPDATE = "is_update";
	public static final String KEY_FRAGMENT_ID = "fragment_id";
	private static final int ACTIONBAR_ITEM_COUNT = 6;
	private ViewPager vpMain;
	private List<Fragment> fragments;
	private boolean isExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initNavi();
		initData();
		initUpdate();
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
				f = new OtherFragment();
			}

			Bundle bundle = new Bundle();
			bundle.putInt(KEY_FRAGMENT_ID, i);
			f.setArguments(bundle);
			fragments.add(f);
		}

		vpMain.setAdapter(new MyMainPagerAdapter(getSupportFragmentManager()));
		vpMain.setOnPageChangeListener(this);

	}

	private void initUpdate() {
		SharedPreferences sp = getSharedPreferences(WelcomeActivity.INIT_PARAMS, MODE_PRIVATE);
		boolean isUpdate = sp.getBoolean(KEY_NEED_UPDATE, true);
		if(isUpdate){
			UpdateTask task = new UpdateTask(this);
			task.execute(CommonInterface.URI_UPDATE);
		}
	}

	/* -------------------- TabListener -------------------- */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getPosition() == ACTIONBAR_ITEM_COUNT - 1) {
			getActionBar().setSelectedNavigationItem(tab.getPosition()-1);
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
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		if (position < ACTIONBAR_ITEM_COUNT - 1) {
			getActionBar().setSelectedNavigationItem(position);
		} else {
			getActionBar().setSelectedNavigationItem(position - 1);
			vpMain.setCurrentItem(position - 1);
			startActivity(new Intent(this, MoreActivity.class));
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if(!isExit){
			isExit = true;
			Toast.makeText(this, "请再按一次退出程序", Toast.LENGTH_SHORT).show();
			new CountDownTimer(2000,2000) {
				@Override
				public void onTick(long millisUntilFinished) {
					
				}
				@Override
				public void onFinish() {
					isExit = false;
				}
			}.start();
		}else{
			//结束当前应用程序
			android.os.Process.killProcess(android.os.Process.myPid());   
			System.exit(0);
		}
	}

}
