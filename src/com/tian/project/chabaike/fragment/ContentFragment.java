package com.tian.project.chabaike.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tian.project.chabaike.R;

public class ContentFragment extends Fragment{
	private Bundle bundle;
	private ListView lvContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container == null){
			return null;
		}
		View layout = inflater.inflate(R.layout.fragment_content, container, false);
		initUI(layout);
		initData();
		
		return layout;
	}

	private void initUI(View layout) {
		lvContent = (ListView) layout.findViewById(R.id.lv_content);
	}

	private void initData() {
		bundle = getArguments();
	}

}
