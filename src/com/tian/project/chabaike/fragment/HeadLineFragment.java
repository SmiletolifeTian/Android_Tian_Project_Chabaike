package com.tian.project.chabaike.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.activity.ContentActivity;
import com.tian.project.chabaike.adapter.HeadlineAdapter;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.common.ScrollBottomScrollView;
import com.tian.project.chabaike.common.ScrollBottomScrollView.OnScrollBottomListener;
import com.tian.project.chabaike.entity.HeadLine;
import com.tian.project.chabaike.entity.HeadLineData;
import com.tian.project.chabaike.entity.HomePage;
import com.tian.project.chabaike.task.HeadlineTask;
import com.tian.project.chabaike.task.HeadlineTask.HeadlineTaskComplete;

public class HeadLineFragment extends Fragment implements OnItemClickListener{
	public static final String KEY_HEADLINE_ID = "headline_id";
	// 控件相关
	private ViewPager vpHomePage;
	private ListView lvHeadLine;
	private LinearLayout llDotGroup;
	private ScrollBottomScrollView svHeadLine;

	// 数据相关
	private HomePage homePage;
	private HeadLine headLine;
	private HeadlineAdapter adapter;
	private HomePageViewpPager homePageViewpPager;
	private int page;
	private int rows = 15;
	private boolean isFirstLoad = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		View layout = inflater.inflate(R.layout.fragment_head_line, container,
				false);
		initUI(layout);
		initData();

		return layout;
	}

	private void initUI(View layout) {
		vpHomePage = (ViewPager) layout.findViewById(R.id.vp_head_line);
		llDotGroup = (LinearLayout) layout.findViewById(R.id.ll_dot_group);
		lvHeadLine = (ListView) layout.findViewById(R.id.lv_head_line);
		svHeadLine = (ScrollBottomScrollView) layout.findViewById(R.id.sv_head_line);
	}

	private void initData() {
		if (isFirstLoad) {
			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				@Override
				public void getData(HomePage homePage, HeadLine headLine) {
					FirstLoadData(homePage, headLine);
				}
			});
			task.execute(CommonInterface.URI_HOME_PAGE,
					String.format(CommonInterface.URI_HEAD_LINE, page, rows));
			page++;
			isFirstLoad = false;
		}else{
			lvHeadLine.setAdapter(adapter);
			homePageViewpPager.start(vpHomePage);
		}
	}

	private void FirstLoadData(HomePage homePage, HeadLine headLine) {
		this.homePage = homePage;
		this.headLine = headLine;

		homePageViewpPager = new HomePageViewpPager(getActivity(),llDotGroup, homePage);
		homePageViewpPager.start(vpHomePage);
		adapter = new HeadlineAdapter(getActivity(), headLine);
		lvHeadLine.setAdapter(adapter);
		lvHeadLine.setOnItemClickListener(this);
		svHeadLine.setOnScrollBottomListener(new OnScrollBottomListener() {
			
			@Override
			public void scrollBottom() {
				HeadlineTask task = new HeadlineTask(
						new HeadlineTaskComplete() {
							@Override
							public void getData(HomePage homePage,HeadLine headLine) {
								HeadLineFragment.this.headLine.getData().addAll(headLine.getData());
								HeadLineFragment.this.adapter.notifyDataSetChanged();
							}
						});
				task.execute(CommonInterface.URI_HOME_PAGE, String.format(
						CommonInterface.URI_HEAD_LINE, page, rows));
				page++;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		HeadLineData data = (HeadLineData) parent.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(),ContentActivity.class);
		intent.putExtra(KEY_HEADLINE_ID, data.getId());
		startActivity(intent);
	}
	

}
