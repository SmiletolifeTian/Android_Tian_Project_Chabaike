package com.tian.project.chabaike.task;

import com.alibaba.fastjson.JSON;
import com.tian.project.chabaike.common.CommonJson;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.entity.HomePage;

import android.os.AsyncTask;

public class HeadlineTask extends AsyncTask<String, Void, Void> {
	private HomePage homePage;
	private ItemInfo headLine;
	private HeadlineTaskComplete headlineTaskComplete;

	public HeadlineTask(HeadlineTaskComplete headlineTaskComplete) {
		this.headlineTaskComplete = headlineTaskComplete;
	}

	@Override
	protected Void doInBackground(String... params) {
		String json = null;
		if (params.length > 1) {
			json = CommonJson.getJson(params[0]);
			homePage = JSON.parseObject(json, HomePage.class);

			json = CommonJson.getJson(params[1]);
			headLine = JSON.parseObject(json, ItemInfo.class);
		} else {
			json = CommonJson.getJson(params[0]);
			headLine = JSON.parseObject(json, ItemInfo.class);
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		headlineTaskComplete.getData(homePage, headLine);
	}

	public interface HeadlineTaskComplete {
		void getData(HomePage homePage, ItemInfo itemInfo);

	}

}
