package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tian.project.chabaike.R;
import com.tian.project.chabaike.common.CommonInterface;
import com.tian.project.chabaike.task.UpdateTask;
import com.tian.project.chabaike.view.CustomTitle;

public class AboutUsActivity extends Activity implements OnClickListener{
	private CustomTitle ctAboutUs;
	private TextView txAboutUsVersion;
	private Button btnVersionCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		
		initUI();
	}

	private void initUI() {
		try {
			txAboutUsVersion = (TextView) findViewById(R.id.tx_about_us_version);
			String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			String versionformat = getResources().getString(R.string.tx_about_us_version);
			txAboutUsVersion.setText(String.format(versionformat, versionName));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		btnVersionCheck = (Button) findViewById(R.id.btn_check_version);
		btnVersionCheck.setOnClickListener(this);
		ctAboutUs = (CustomTitle) findViewById(R.id.ct_about_us);
		ctAboutUs.setTitleBackOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AboutUsActivity.this.finish();
			}
		});
		ctAboutUs.setTitleHomeOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AboutUsActivity.this,MainActivity.class));
			}
		});
	}

	@Override
	public void onClick(View v) {
		UpdateTask task = new UpdateTask(this);
		task.execute(CommonInterface.URI_UPDATE);
	}
}
