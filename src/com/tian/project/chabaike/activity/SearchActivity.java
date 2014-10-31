package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.adapter.ItemInfoAdapter;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.entity.HomePage;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.task.HeadlineTask;
import com.tian.project.chabaike.task.HeadlineTask.HeadlineTaskComplete;
import com.tian.project.chabaike.view.CustomTitle;

public class SearchActivity extends Activity implements OnScrollListener, OnClickListener{
	//控件相关
	private CustomTitle ctSearch;
	private ListView lvSearch;
	private View moreView;
	private Button btnLoad;
	private ProgressBar pgLoading;
	
	//数据相关
	private ItemInfo itemInfo;
	private ItemInfoAdapter adapter;
	private String keywordSearch;
	private int page;
	private int rows = 15;
	private int lastListItemIndex;
	private boolean isLoadCompleted = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initUI();
		initData();
	}

	private void initUI() {
		keywordSearch = getIntent().getStringExtra(MoreActivity.INTENT_KEY_WORD);
		ctSearch = (CustomTitle) findViewById(R.id.ct_search);
		ctSearch.setTitleNameText(keywordSearch);
		ctSearch.setTitleBackOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchActivity.this.finish();
			}
		});
		ctSearch.setTitleHomeOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SearchActivity.this,MainActivity.class));
			}
		});
		
		lvSearch = (ListView) findViewById(R.id.lv_search);
		moreView = getLayoutInflater().inflate(R.layout.list_view_footer_load_more,null);
		btnLoad = (Button) moreView.findViewById(R.id.btn_load);
		btnLoad.setOnClickListener(this);
		pgLoading = (ProgressBar) moreView.findViewById(R.id.pg_loading);
	}

	private void initData() {
		FirstLoadData();
	}

	/* -------------------- OnScrollListener -------------------- */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(lastListItemIndex == adapter.getCount()&&scrollState == SCROLL_STATE_IDLE){
			LoadMoreData();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastListItemIndex = firstVisibleItem+visibleItemCount-1;
	}
	/* -------------------- OnScrollListener -------------------- */

	private void FirstLoadData() {
		HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
			
			@Override
			public void getData(HomePage homePage, ItemInfo itemInfo) {
				SearchActivity.this.itemInfo = itemInfo;
				adapter = new ItemInfoAdapter(SearchActivity.this, itemInfo);
				lvSearch.addFooterView(moreView, null, false);
				lvSearch.setAdapter(adapter);
				lvSearch.setOnScrollListener(SearchActivity.this);
			}
		});
		task.execute(String.format(CommonInterface.URI_KEYWORD_SEARCH, page,rows,keywordSearch));
		page++;
	}

	private void LoadMoreData() {
		if(isLoadCompleted){
			isLoadCompleted = false;
			btnLoad.setVisibility(View.GONE);
			pgLoading.setVisibility(View.VISIBLE);
			
			HeadlineTask task = new HeadlineTask(new HeadlineTaskComplete() {
				
				@Override
				public void getData(HomePage homePage, ItemInfo itemInfo) {
					SearchActivity.this.itemInfo.getData().addAll(itemInfo.getData());
					adapter.notifyDataSetChanged();
					
					isLoadCompleted = true;
					btnLoad.setVisibility(View.VISIBLE);
					pgLoading.setVisibility(View.GONE);
				}
			});
			task.execute(String.format(CommonInterface.URI_KEYWORD_SEARCH, page,rows,keywordSearch));
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
