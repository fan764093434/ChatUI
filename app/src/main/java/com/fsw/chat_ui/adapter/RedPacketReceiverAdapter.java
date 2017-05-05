package com.fsw.chat_ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fsw.chat_ui.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/3/30.
 */

public class RedPacketReceiverAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public RedPacketReceiverAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_list_item_red_packet_receiver, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
