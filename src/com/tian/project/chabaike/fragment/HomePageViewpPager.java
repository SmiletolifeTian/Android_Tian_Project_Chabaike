package com.tian.project.chabaike.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.image.SmartImageView;
import com.tian.project.chabaike.R;
import com.tian.project.chabaike.adapter.CycleAdapter;
import com.tian.project.chabaike.entity.HomePage;

public class HomePageViewpPager {
	// 控件相关
	private Context context;
	private ViewPager vpHomePage;
	private LinearLayout llDotGroup;
	private SmartImageView[] imgViews;
	private ImageView[] imgDots;

	// 数据相关
	private HomePage homePage;

	private Handler handler = new Handler();

	public HomePageViewpPager(Context context, LinearLayout llDotGroup,
			HomePage homePage) {
		this.context = context;
		this.llDotGroup = llDotGroup;
		this.homePage = homePage;

		int num = homePage.getData().size();
		if (num > 1) {
			imgViews = new SmartImageView[num + 2];
			for (int i = 0; i < imgViews.length; i++) {
				if (i == 0) {
					imgViews[i] = new SmartImageView(context);
					imgViews[i].setImageUrl(homePage.getData().get(num - 1)
							.getImage());
				}else if(i==num+1){
					imgViews[i] = new SmartImageView(context);
					imgViews[i].setImageUrl(homePage.getData().get(0)
							.getImage());
				}else{
					imgViews[i] = new SmartImageView(context);
					imgViews[i].setImageUrl(homePage.getData().get(i-1)
							.getImage());
				}

			}
		} else {
			imgViews = new SmartImageView[1];
			imgViews[0].setImageUrl(homePage.getData().get(0).getImage());
		}

		imgDots = new ImageView[num];
		for (int i = 0; i < imgDots.length; i++) {
			imgDots[i] = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);
			params.setMargins(10, 0, 0, 0);
			imgDots[i].setLayoutParams(params);
			if (i == 0) {
				imgDots[i].setImageResource(R.drawable.page_now);
			} else {
				imgDots[i].setImageResource(R.drawable.page);
			}
			llDotGroup.addView(imgDots[i]);
		}

	}

	public void start(ViewPager vpHomePage) {
		this.vpHomePage = vpHomePage;

		CycleAdapter cycleAdapter = new CycleAdapter(vpHomePage,imgViews, imgDots);
		vpHomePage.setAdapter(cycleAdapter);
		vpHomePage.setOnPageChangeListener(cycleAdapter);
		if (imgViews.length > 1) {
			vpHomePage.setCurrentItem(1);
			handler.postDelayed(changeImgTask, 3000);
		}
	}

	Runnable changeImgTask = new Runnable() {

		@Override
		public void run() {
			int index = vpHomePage.getCurrentItem();
			index = (index + 1) % imgViews.length;
			vpHomePage.setCurrentItem(index);

			handler.postDelayed(changeImgTask, 3000);
		}
	};

}
