package com.tian.project.chabaike.task;

import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

public class APKDownLoadTask extends AsyncTask<String, Void, String> {
	
	@Override
	protected void onPreExecute() {
		NotificationCompat compat = new NotificationCompat();
		
	}

	@Override
	protected String doInBackground(String... params) {
		return null;
	}

}
