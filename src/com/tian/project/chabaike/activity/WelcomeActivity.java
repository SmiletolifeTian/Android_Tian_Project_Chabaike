package com.tian.project.chabaike.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.tian.project.chabaike.R;

public class WelcomeActivity extends Activity {
	private static final String INIT_PARAMS = "init_params";
	private static final String IS_FIRST_START_APP = "isFirstStartApp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		initGuide();
	}

	private void initGuide() {
		SharedPreferences sp = getSharedPreferences(INIT_PARAMS, MODE_PRIVATE);
		boolean isFirstStartApp = sp.getBoolean(IS_FIRST_START_APP, true);
		isFirstStartApp = true;
		if (isFirstStartApp) {
			startActivity(new Intent(this, GuideActivity.class));
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean(IS_FIRST_START_APP, false);
			editor.commit();
		} else {
			new CountDownTimer(3000, 3000) {

				@Override
				public void onTick(long millisUntilFinished) {

				}

				@Override
				public void onFinish() {
					startActivity(new Intent(WelcomeActivity.this,
							MainActivity.class));
				}
			}.start();
		}
	}
}
