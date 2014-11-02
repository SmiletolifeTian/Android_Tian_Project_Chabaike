package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.tian.project.chabaike.R;

public class GuideActivity extends Activity implements OnPageChangeListener {
	private ViewPager vpGuide;
	private LinearLayout llDotGroup;
	private ImageView[] imgViews;
	private ImageView[] imgDots;
	private int[] ids = new int[] { R.drawable.slide1, R.drawable.slide2,
			R.drawable.slide3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		initUI();
		initData();
	}

	private void initUI() {
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llDotGroup = (LinearLayout) findViewById(R.id.ll_dot_group);
	}

	private void initData() {
		imgViews = new ImageView[ids.length];
		imgDots = new ImageView[ids.length];

		for (int i = 0; i < ids.length; i++) {
			// 初始化viewpager的数据
			imgViews[i] = new ImageView(this);
			imgViews[i].setScaleType(ScaleType.FIT_XY);
			imgViews[i].setImageResource(ids[i]);

			// 初始化点的数据
			imgDots[i] = new ImageView(this);
			if (i == 0) {
				imgDots[i].setImageResource(R.drawable.page_now);
			} else {
				imgDots[i].setImageResource(R.drawable.page);
			}
			LayoutParams params = new LinearLayout.LayoutParams(10, 10);
			params.setMargins(10, 0, 0, 0);
			imgDots[i].setLayoutParams(params);
			llDotGroup.addView(imgDots[i]);
		}
		
		imgViews[ids.length-1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GuideActivity.this.finish();
				startActivity(new Intent(GuideActivity.this,MainActivity.class));
			}
		});
		vpGuide.setAdapter(new MyPagerAdapter());
		vpGuide.setOnPageChangeListener(this);
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imgViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imgViews[position]);
			return imgViews[position];
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imgViews[position]);
		}

	}

	/* ---------------------- OnPageChangeListener ---------------------- */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		for (int i = 0; i < ids.length; i++) {
			if (i == position) {
				imgDots[i].setImageResource(R.drawable.page_now);
			} else {
				imgDots[i].setImageResource(R.drawable.page);
			}
		}
	}
	/* ---------------------- OnPageChangeListener ---------------------- */
}
