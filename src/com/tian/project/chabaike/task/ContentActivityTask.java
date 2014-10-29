package com.tian.project.chabaike.task;

import com.alibaba.fastjson.JSON;
import com.tian.project.chabaike.activity.ContentActivity.ViewHolder;
import com.tian.project.chabaike.common.CommonJson;
import com.tian.project.chabaike.entity.HeadLineContent;

import android.os.AsyncTask;

public class ContentActivityTask extends
		AsyncTask<String, Void, HeadLineContent> {
	private ViewHolder viewHolder;

	public ContentActivityTask(ViewHolder viewHolder) {
		this.viewHolder = viewHolder;
	}

	@Override
	protected HeadLineContent doInBackground(String... params) {
		String json = CommonJson.getJson(params[0]);
		HeadLineContent content = JSON.parseObject(json, HeadLineContent.class);

		return content;
	}

	@Override
	protected void onPostExecute(HeadLineContent result) {
		if (result != null) {
			viewHolder.txTitle.setText(result.getData().getTitle());
			viewHolder.txCreateTime.setText(result.getData().getCreate_time());
			viewHolder.txAuthor.setText(result.getData().getAuthor());

			viewHolder.wvContent.loadDataWithBaseURL(null, result.getData()
					.getWap_content(), null, "UTF-8", null);
		}

	}
}
