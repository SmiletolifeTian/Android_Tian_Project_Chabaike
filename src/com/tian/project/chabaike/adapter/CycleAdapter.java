package com.tian.project.chabaike.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;
import com.tian.project.chabaike.R;

public class CycleAdapter extends PagerAdapter implements
		ViewPager.OnPageChangeListener {

	private LinkedList<View> mViews; // 用于显示的View
	private ViewPager mViewPager; // 页面
	private ImageView[] tips; // 图片上标记点

	public CycleAdapter(ViewPager viewPager,
			SmartImageView[] list, ImageView[] tips) {
		mViewPager = viewPager;
		this.tips = tips;

		mViews = new LinkedList<View>();
		if (list != null) {
			for (SmartImageView img : list) {
				mViews.add(img);
			}
		}
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = mViews.get(position);
		container.addView(view);
		return view;
	}

	// 实现ViewPager.OnPageChangeListener接口
	@Override
	public void onPageSelected(int position) {
		if (mViews.size() > 1) { // 多于1，才会循环跳转
			if (position < 1) { // 首位之前，跳转到末尾（N）
				position = tips.length; // 注意这里是mList，而不是mViews
				mViewPager.setCurrentItem(position, false);
			} else if (position > tips.length) { // 末位之后，跳转到首位（1）
				mViewPager.setCurrentItem(1, false); // false:不显示跳转过程的动画
				position = 1;
			}

			setImageBackground(position);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		if (selectItems >= 1 && selectItems <= tips.length) {
			selectItems = selectItems - 1;
			for (int i = 0; i < tips.length; i++) {
				if (i == selectItems) {
					tips[i].setImageResource(R.drawable.page_now);
				} else {
					tips[i].setImageResource(R.drawable.page);
				}
			}
		}
	}
}