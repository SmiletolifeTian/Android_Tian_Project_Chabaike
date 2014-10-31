package com.tian.project.chabaike.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.activity.ContentActivity;
import com.tian.project.chabaike.activity.MainActivity;
import com.tian.project.chabaike.adapter.ItemInfoAdapter;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.entity.ItemInfoData;
import com.tian.project.chabaike.entity.HomePage;
import com.tian.project.chabaike.task.HeadlineTask;
import com.tian.project.chabaike.task.HeadlineTask.HeadlineTaskComplete;

public class OtherFragment extends Fragment implements OnItemClickListener,
		OnClickListener {
	private static final String TAG = "OtherFragment";
	// 控件相关
	private ListView lvContent;
	private View moreView;
	private Button btnLoad;
	private ProgressBar pgLoading;

	// 数据相关
	private Bundle bundle;
	private boolean isFirstLoad = true;
	private ItemInfo otherItemInfo;
	private ItemInfoAdapter adapter;
	private int page = 1;
	private int rows = 15;
	private int type;
	private int lastListItemIndex;
	private boolean isLoadCompleted = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		View layout = inflater.inflate(R.layout.fragment_content, container,
				false);
		initUI(layout);
		initData();

		return layout;
	}

	private void initUI(View layout) {
		lvContent = (ListView) layout.findViewById(R.id.lv_content);
		lvContent.setOnItemClickListener(this);

		moreView = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_view_footer_load_more, null);
		btnLoad = (Button) moreView.findViewById(R.id.btn_load);
		btnLoad.setOnClickListener(this);
		pgLoading = (ProgressBar) moreView.findViewById(R.id.pg_loading);

	}

	private void initData() {
		if (isFirstLoad) {
			FirstLoadData();
		} else {
			lvContent.addFooterView(moreView, null, false);
			lvContent.setAdapter(adapter);
		}

	}

	/**
	 * 创建fragment时加载数据
	 */
	private void FirstLoadData() {
		bundle = getArguments();
		final int index = bundle.getInt(MainActivity.KEY_FRAGMENT_ID);
		// 百科 16 资讯 52 经营 53 数据 54
		type = 0;
		switch (index) {
		case 1:
			type = 16;
			break;
		case 2:
			type = 52;
			break;
		case 3:
			type = 53;
			break;
		case 4:
			type = 54;
			break;

		default:
			break;
		}
		if (type != 0) {
			// 设置异步任务回调接口带回从网络得到的数据
			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				@Override
				public void getData(HomePage homePage, ItemInfo itemInfo) {
					otherItemInfo = itemInfo;
					lvContent.addFooterView(moreView, null, false);
					adapter = new ItemInfoAdapter(getActivity(), otherItemInfo);
					lvContent.setAdapter(adapter);
				}
			});
			task.execute(String.format(CommonInterface.URI_OTHER_CONTENT, page,
					rows, type));
			page++;

			// 设置lvContent的滑动事件
			lvContent.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					if(lastListItemIndex == adapter.getCount()&&scrollState==SCROLL_STATE_IDLE){
						Log.i(TAG, String.format("index:%s;totalItem:%s", index, lastListItemIndex));
					LoadMoreData();
					}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					lastListItemIndex = firstVisibleItem + visibleItemCount - 1;
				}
			});

			isFirstLoad = false;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ItemInfoData data = (ItemInfoData) parent.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), ContentActivity.class);
		intent.putExtra(HeadLineFragment.KEY_ITEM_INFO_DATA, data);
		startActivity(intent);

	}

	/**
	 * 加载更多数据
	 */
	private void LoadMoreData() {
		if (isLoadCompleted) {
			isLoadCompleted = false;
			btnLoad.setVisibility(View.GONE);
			pgLoading.setVisibility(View.VISIBLE);

			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				@Override
				public void getData(HomePage homePage, ItemInfo itemInfo) {
					if (otherItemInfo!=null && otherItemInfo.getData()!= null && itemInfo != null && itemInfo.getErrorMessage().equals("success") && itemInfo.getData() != null) {
						if (itemInfo.getData().size() > 0) {
							otherItemInfo.getData().addAll(itemInfo.getData());
							adapter.notifyDataSetChanged();

							btnLoad.setText("加载更多数据");
						}else{
							btnLoad.setText("已加载全部数据");
						}

						isLoadCompleted = true;
						btnLoad.setVisibility(View.VISIBLE);
						pgLoading.setVisibility(View.GONE);
						
					}
				}
			});
			task.execute(String.format(CommonInterface.URI_OTHER_CONTENT, page,
					rows, type));

			page++;
		}
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
}
