package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.fragment.HeadLineFragment;
import com.tian.project.chabaike.task.ContentActivityTask;

public class ContentActivity extends Activity implements OnClickListener{
	ViewHolder viewHolder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);

		initUI();
		initData();
	}

	private void initUI() {
		viewHolder = new ViewHolder();
		viewHolder.txTitle = (TextView) findViewById(R.id.tx_title);
		viewHolder.txAuthor = (TextView) findViewById(R.id.tx_author);
		viewHolder.txCreateTime = (TextView) findViewById(R.id.tx_create_time);
		viewHolder.wvContent = (WebView) findViewById(R.id.wv_wap_content);
		WebSettings settings = viewHolder.wvContent.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
        
		viewHolder.btnBack = (Button) findViewById(R.id.btn_back);
		viewHolder.btnBack.setOnClickListener(this);
		viewHolder.btnShare = (Button) findViewById(R.id.btn_share);
		viewHolder.btnShare.setOnClickListener(this);
		viewHolder.btnFavorite = (Button) findViewById(R.id.btn_favorite);
		viewHolder.btnFavorite.setOnClickListener(this);
	}

	private void initData() {
		String id = getIntent().getStringExtra(HeadLineFragment.KEY_HEADLINE_ID);
		ContentActivityTask task = new ContentActivityTask(viewHolder);
		task.execute(String.format(CommonInterface.URI_HEAD_LINE_CONTENT, id));

	}

	public class ViewHolder {

		public TextView txTitle;
		public TextView txAuthor;
		public TextView txCreateTime;
		public WebView wvContent;
		public Button btnBack;
		public Button btnShare;
		public Button btnFavorite;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_share:
			
			break;
		case R.id.btn_favorite:
			
			break;

		default:
			break;
		}
	}
}
