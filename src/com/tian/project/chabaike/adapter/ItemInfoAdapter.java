package com.tian.project.chabaike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.tian.project.chabaike.R;
import com.tian.project.chabaike.entity.ItemInfo;
import com.tian.project.chabaike.entity.ItemInfoData;

public class ItemInfoAdapter extends BaseAdapter {
	private Context context;
	private ItemInfo itemInfo;

	public ItemInfoAdapter(Context context, ItemInfo itemInfo) {
		this.context = context;
		this.itemInfo = itemInfo;
	}

	@Override
	public int getCount() {
		if (itemInfo != null && itemInfo.getData() != null
				&& itemInfo.getData().size() > 0) {
			return itemInfo.getData().size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (itemInfo != null && itemInfo.getData() != null
				&& itemInfo.getData().size() > 0) {
			return itemInfo.getData().get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_view_item_head_line, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imgIcon = (SmartImageView) convertView
					.findViewById(R.id.img_icon);
			viewHolder.txCreateTime = (TextView) convertView
					.findViewById(R.id.tx_create_time);
			viewHolder.txNickname = (TextView) convertView
					.findViewById(R.id.tx_nickname);
			viewHolder.txSource = (TextView) convertView
					.findViewById(R.id.tx_source);
			viewHolder.txTitle = (TextView) convertView
					.findViewById(R.id.tx_title);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ItemInfoData data = (ItemInfoData) getItem(position);
		if (data != null) {
			if (data.getWap_thumb().length() > 0) {
				viewHolder.imgIcon.setImageUrl(data.getWap_thumb());
			}
			viewHolder.txCreateTime.setText(data.getCreate_time());
			viewHolder.txNickname.setText(data.getNickname());
			viewHolder.txSource.setText(data.getSource());
			viewHolder.txTitle.setText(data.getTitle());
		}

		return convertView;
	}

	class ViewHolder {
		public SmartImageView imgIcon;
		public TextView txTitle;
		public TextView txSource;
		public TextView txNickname;
		public TextView txCreateTime;
	}

}
