package com.tian.project.chabaike.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.tian.project.chabaike.R;

public class APKDownLoadTask extends AsyncTask<String, Integer, String> {
	private static final int HTTP_CONNECT_TIMEOUT = 5000;
	private Context context;

	// 通知相关
	NotificationManager manager;
	NotificationCompat.Builder builder;
	
	public APKDownLoadTask(Context context){
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context);
		builder.setContentText("正在下载...");
		builder.setProgress(100, 0, false);
		builder.setSmallIcon(R.drawable.ic_launcher);
		manager.notify(0, builder.build());

	}

	@Override
	protected String doInBackground(String... params) {
		String downloadFilePath = null;

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					HTTP_CONNECT_TIMEOUT);
			HttpGet httpGet = new HttpGet(params[0]);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null
					&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					long length = httpEntity.getContentLength();
					is = httpEntity.getContent();
					String filePath = Environment.getExternalStorageDirectory()
							+ "/tian/";
					File file = new File(filePath);
					if (!file.exists()) {
						file.mkdir();
					}
					downloadFilePath = filePath + "chabaike.apk";
					fos = new FileOutputStream(downloadFilePath);

					byte[] buf = new byte[1024];
					int len = -1, count = 0;
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
						fos.flush();
						count += len;
						publishProgress((int) (count * 100.0 / length));
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}

				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return downloadFilePath;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		builder.setProgress(100, values[0], false);
		builder.setContentText(String.format("已下载%s%%", values[0]));
		manager.notify(0, builder.build());
	}

	@Override
	protected void onPostExecute(String result) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(result)),"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

}
