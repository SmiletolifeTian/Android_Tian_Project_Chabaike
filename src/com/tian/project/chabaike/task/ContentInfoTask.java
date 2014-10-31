package com.tian.project.chabaike.task;

import com.alibaba.fastjson.JSON;
import com.tian.project.chabaike.activity.ContentActivity.ViewHolder;
import com.tian.project.chabaike.common.CommonJson;
import com.tian.project.chabaike.entity.ContentInfo;

import android.os.AsyncTask;

public class ContentInfoTask extends
		AsyncTask<String, Void, ContentInfo> {
	private ViewHolder viewHolder;

	public ContentInfoTask(ViewHolder viewHolder) {
		this.viewHolder = viewHolder;
	}

	@Override
	protected ContentInfo doInBackground(String... params) {
		String json = CommonJson.getJson(params[0]);
		ContentInfo content = JSON.parseObject(json, ContentInfo.class);

		return content;
	}

	@Override
	protected void onPostExecute(ContentInfo result) {
		if (result != null) {
			viewHolder.txTitle.setText(result.getData().getTitle());
			viewHolder.txCreateTime.setText(result.getData().getCreate_time());
			viewHolder.txAuthor.setText(result.getData().getAuthor());

			viewHolder.wvContent.loadDataWithBaseURL(null, result.getData()
					.getWap_content(), null, "UTF-8", null);
		}

	}
}
