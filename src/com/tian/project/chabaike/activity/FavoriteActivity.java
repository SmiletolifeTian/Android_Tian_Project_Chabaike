package com.tian.project.chabaike.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.adapter.ItemInfoAdapter;
import com.tian.project.chabaike.common.DBHelper;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.entity.ItemInfoData;
import com.tian.project.chabaike.fragment.HeadLineFragment;
import com.tian.project.chabaike.view.CustomTitle;

public class FavoriteActivity extends Activity implements OnItemClickListener{
	private CustomTitle ctFavorite;
	private ListView lvFavorite;
	private DBHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		
		initUI();
		initData();
	}

	private void initUI() {
		ctFavorite = (CustomTitle) findViewById(R.id.ct_favorite);
		ctFavorite.setTitleBackOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FavoriteActivity.this.finish();
			}
		});
		ctFavorite.setTitleHomeOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FavoriteActivity.this,MainActivity.class));
			}
		});
		
		lvFavorite = (ListView) findViewById(R.id.lv_favorite);
		lvFavorite.setOnItemClickListener(this);

		db = new DBHelper(this);
		
	}

	private void initData() {
		String sql = "SELECT * FROM tb_chabaike";
		Cursor cursor = db.execQuery(sql);
		List<ItemInfoData> infoDatas = new ArrayList<ItemInfoData>();
		ItemInfoData infoData = null;
		while(cursor.moveToNext()){
			infoData = new ItemInfoData();
			infoData.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
			infoData.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			infoData.setId(cursor.getString(cursor.getColumnIndex("detail_id")));
			infoData.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
			infoData.setSource(cursor.getString(cursor.getColumnIndex("source")));
			infoData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			infoData.setWap_thumb(cursor.getString(cursor.getColumnIndex("wap_thumb")));
			infoDatas.add(infoData);
		}
		
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setData(infoDatas);
		ItemInfoAdapter adapter = new ItemInfoAdapter(this, itemInfo);
		lvFavorite.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		ItemInfoData infoData = (ItemInfoData) parent.getItemAtPosition(position);
		Intent intent = new Intent(this,ContentActivity.class);
		intent.putExtra(HeadLineFragment.KEY_ITEM_INFO_DATA, infoData);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
		
	}
}
