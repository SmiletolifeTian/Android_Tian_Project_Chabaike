package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.view.CustomTitle;

public class MoreActivity extends Activity implements OnClickListener {
	public static final String INTENT_KEY_WORD = "key_word";
	private CustomTitle ctMore;
	private TextView txFavorite;
	private ImageView imgSearch;
	private EditText edSearch;
	private TextView txCopyrightInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);

		initUI();
	}

	private void initUI() {
		ctMore = (CustomTitle) findViewById(R.id.ct_more);
		ctMore.setTitleBackOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MoreActivity.this.finish();
			}
		});
		ctMore.setTitleHomeOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		txFavorite = (TextView) findViewById(R.id.tx_favorite);
		txFavorite.setOnClickListener(this);
		imgSearch = (ImageView) findViewById(R.id.img_more_search);
		imgSearch.setOnClickListener(this);
		edSearch = (EditText) findViewById(R.id.et_search);
		txCopyrightInfo = (TextView) findViewById(R.id.tx_copyright_info);
		txCopyrightInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tx_favorite:
			startActivity(new Intent(this, FavoriteActivity.class));
			break;
		case R.id.img_more_search:
			String keyword = edSearch.getText().toString();
			if (keyword.length() > 0) {
				intent = new Intent(this, SearchActivity.class);
				intent.putExtra(INTENT_KEY_WORD, keyword);
				startActivity(intent);
			}else{
				Toast.makeText(this, "«Î ‰»Îπÿº¸◊÷", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tx_copyright_info:
			startActivity(new Intent(this,AboutUsActivity.class));
			break;

		default:
			break;
		}
	}

}
