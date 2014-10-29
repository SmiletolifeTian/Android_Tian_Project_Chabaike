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

	private LinkedList<View> mViews; // ������ʾ��View
	private ViewPager mViewPager; // ҳ��
	private ImageView[] tips; // ͼƬ�ϱ�ǵ�

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

	// ʵ��ViewPager.OnPageChangeListener�ӿ�
	@Override
	public void onPageSelected(int position) {
		if (mViews.size() > 1) { // ����1���Ż�ѭ����ת
			if (position < 1) { // ��λ֮ǰ����ת��ĩβ��N��
				position = tips.length; // ע��������mList��������mViews
				mViewPager.setCurrentItem(position, false);
			} else if (position > tips.length) { // ĩλ֮����ת����λ��1��
				mViewPager.setCurrentItem(1, false); // false:����ʾ��ת���̵Ķ���
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
	 * ����ѡ�е�tip�ı���
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