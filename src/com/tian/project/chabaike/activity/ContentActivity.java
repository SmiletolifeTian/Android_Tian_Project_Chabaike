package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.common.DBHelper;
import com.tian.project.chabaike.entity.ItemInfoData;
import com.tian.project.chabaike.fragment.HeadLineFragment;
import com.tian.project.chabaike.task.ContentInfoTask;

public class ContentActivity extends Activity implements OnClickListener {
	private ViewHolder viewHolder;
	private ItemInfoData itemInfoData;
	private DBHelper db;
	private boolean hasFavorited;

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
		db = new DBHelper(this);
		itemInfoData = (ItemInfoData) getIntent().getSerializableExtra(
				HeadLineFragment.KEY_ITEM_INFO_DATA);
		ContentInfoTask task = new ContentInfoTask(viewHolder);
		task.execute(String.format(CommonInterface.URI_HEAD_LINE_CONTENT,
				itemInfoData.getId()));

		String sql = "SELECT * FROM tb_chabaike WHERE detail_id = ?";
		Cursor cursor = db
				.execQuery(sql, new String[] { itemInfoData.getId() });
		if (cursor.getCount() > 0) {
			hasFavorited = true;
		} else {
			hasFavorited = false;
		}
		changeFavoriteBG();

	}

	private void changeFavoriteBG() {
		if (!hasFavorited) {
			viewHolder.btnFavorite.setBackgroundResource(R.drawable.collectcontent);
		} else {
			viewHolder.btnFavorite.setBackgroundResource(R.drawable.collectcontent_yishoucang);
		}
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
			if (hasFavorited) {
				removeDataForFavorite();
			} else {
				addDataForFavorite();
			}
			break;

		default:
			break;
		}
	}

	private void addDataForFavorite() {
		String sql = "INSERT INTO tb_chabaike(detail_id,title,source,description,wap_thumb,create_time,nickname) VALUES(?,?,?,?,?,?,?)";
		Object[] bindArgs = new String[] { itemInfoData.getId(),
				itemInfoData.getTitle(), itemInfoData.getSource(),
				itemInfoData.getDescription(), itemInfoData.getWap_thumb(),
				itemInfoData.getCreate_time(), itemInfoData.getNickname() };
		db.execSQL(sql, bindArgs);
		Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
		hasFavorited = true;
		changeFavoriteBG();
	}

	private void removeDataForFavorite() {
		String sql = "DELETE FROM tb_chabaike WHERE detail_id = ?";
		db.execSQL(sql, new Object[] { itemInfoData.getId() });
		Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
		hasFavorited = false;
		changeFavoriteBG();
	}
}
