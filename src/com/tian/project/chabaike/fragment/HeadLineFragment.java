package com.tian.project.chabaike.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.activity.ContentActivity;
import com.tian.project.chabaike.adapter.ItemInfoAdapter;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.common.ScrollBottomScrollView;
import com.tian.project.chabaike.common.ScrollBottomScrollView.OnScrollBottomListener;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.entity.ItemInfoData;
import com.tian.project.chabaike.entity.HomePage;
import com.tian.project.chabaike.task.HeadlineTask;
import com.tian.project.chabaike.task.HeadlineTask.HeadlineTaskComplete;

public class HeadLineFragment extends Fragment implements OnItemClickListener,
		OnClickListener {
	public static final String KEY_ITEM_INFO_DATA = "item_info_data";
	// 控件相关
	private ViewPager vpHomePage;
	private ListView lvHeadLine;
	private LinearLayout llDotGroup;
	private ScrollBottomScrollView svHeadLine;
	private View moreView;
	private Button btnLoad;
	private ProgressBar pgLoading;

	// 数据相关
	private HomePage homePage;
	private ItemInfo headLine;
	private ItemInfoAdapter adapter;
	private HomePageViewpPager homePageViewpPager;
	private int page = 1;
	private int rows = 15;
	private boolean isFirstLoad = true;
	private boolean isLoadCompleted = true;

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
		svHeadLine = (ScrollBottomScrollView) layout
				.findViewById(R.id.sv_head_line);
		moreView = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_view_footer_load_more, null);
		btnLoad = (Button) moreView.findViewById(R.id.btn_load);
		btnLoad.setOnClickListener(this);
		pgLoading = (ProgressBar) moreView.findViewById(R.id.pg_loading);
	}

	private void initData() {
		if (isFirstLoad) {
			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				@Override
				public void getData(HomePage homePage, ItemInfo headLine) {
					FirstLoadData(homePage, headLine);
				}
			});
			task.execute(CommonInterface.URI_HOME_PAGE,
					String.format(CommonInterface.URI_HEAD_LINE, page, rows));
			page++;
			isFirstLoad = false;
		} else {
			lvHeadLine.addFooterView(moreView, null, false);
			lvHeadLine.setAdapter(adapter);
			homePageViewpPager.start(vpHomePage);
		}
	}

	private void FirstLoadData(HomePage homePage, ItemInfo headLine) {
		this.homePage = homePage;
		this.headLine = headLine;

		homePageViewpPager = new HomePageViewpPager(getActivity(), llDotGroup,
				homePage);
		homePageViewpPager.start(vpHomePage);
		lvHeadLine.addFooterView(moreView, null, false);
		adapter = new ItemInfoAdapter(getActivity(), headLine);
		lvHeadLine.setAdapter(adapter);
		lvHeadLine.setOnItemClickListener(this);
		svHeadLine.setOnScrollBottomListener(new OnScrollBottomListener() {

			@Override
			public void scrollBottom() {
				LoadMoreData();
			}

		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ItemInfoData data = (ItemInfoData) parent.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), ContentActivity.class);
		intent.putExtra(KEY_ITEM_INFO_DATA, data);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_load:
			LoadMoreData();
			break;

		default:
			break;
		}
	}

	private void LoadMoreData() {
		if (isLoadCompleted) {
			isLoadCompleted = false;
			btnLoad.setVisibility(View.GONE);
			pgLoading.setVisibility(View.VISIBLE);

			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				@Override
				public void getData(HomePage homePage, ItemInfo itemInfo) {
					if (itemInfo != null
							&& itemInfo.getErrorMessage().equals("success")
							&& itemInfo.getData() != null) {
						if (itemInfo.getData().size() > 0) {
							HeadLineFragment.this.headLine.getData().addAll(
									itemInfo.getData());
							HeadLineFragment.this.adapter
									.notifyDataSetChanged();

							btnLoad.setText("加载更多数据");

						} else {
							btnLoad.setText("已加载全部数据");
						}
						isLoadCompleted = true;
						btnLoad.setVisibility(View.VISIBLE);
						pgLoading.setVisibility(View.GONE);

					}
				}
			});
			task.execute(CommonInterface.URI_HOME_PAGE,
					String.format(CommonInterface.URI_HEAD_LINE, page, rows));
			page++;

		}
	}
}
