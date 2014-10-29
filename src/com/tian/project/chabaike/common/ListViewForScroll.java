package com.tian.project.chabaike.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewForScroll extends ListView {
	public ListViewForScroll(Context context) {
		super(context);
	}

	public ListViewForScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
