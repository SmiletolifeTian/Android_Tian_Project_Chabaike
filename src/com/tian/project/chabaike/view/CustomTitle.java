package com.tian.project.chabaike.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tian.project.chabaike.R;

public class CustomTitle extends FrameLayout {
	private TextView txTitleBack;
	private TextView txTitleName;
	private TextView txTitleHome;

	public CustomTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.abc_custom_title, this, true);
		initUI();
		initData(attrs);
	}

	private void initUI() {
		txTitleBack = (TextView) findViewById(R.id.tx_title_back);
		txTitleName = (TextView) findViewById(R.id.tx_title_name);
		txTitleHome = (TextView) findViewById(R.id.tx_title_home);
	}

	private void initData(AttributeSet attrs) {
		TypedArray t = getContext().obtainStyledAttributes(attrs,R.styleable.CustomTitle);
		String textValue = t.getString(R.styleable.CustomTitle_titleNameText); 
		txTitleName.setText(textValue);
	}
	
	public void setTitleBackOnClickListener(OnClickListener listener){
		txTitleBack.setOnClickListener(listener);
	}
	
	public void setTitleHomeOnClickListener(OnClickListener listener){
		txTitleHome.setOnClickListener(listener);
	}
	
	public void setTitleNameText(String titleName){
		txTitleName.setText(titleName);
	}

}
