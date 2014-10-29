package com.tian.project.chabaike.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollBottomScrollView extends ScrollView {

	private OnScrollBottomListener scrollBottomListener;

	public ScrollBottomScrollView(Context context) {
		super(context);
	}

	public ScrollBottomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollBottomScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (t + getHeight() >= computeVerticalScrollRange()) {
			// ScrollView滑动到底部了
			scrollBottomListener.scrollBottom();
		}
	}

	public void setOnScrollBottomListener(
			OnScrollBottomListener scrollBottomListener) {
		this.scrollBottomListener = scrollBottomListener;
	}

	public interface OnScrollBottomListener {
		public void scrollBottom();
	}

}
