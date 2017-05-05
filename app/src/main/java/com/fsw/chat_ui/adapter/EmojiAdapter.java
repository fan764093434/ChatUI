package com.fsw.chat_ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Emotion;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Admin on 2017/3/27.
 */

public class EmojiAdapter extends BaseAdapter {

    private Context context;

    private List<Emotion> list;

    public EmojiAdapter(Context context, List<Emotion> list) {
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_list_item_emoji, null);
            holder.titleImg = (ImageView) convertView.findViewById(R.id.title_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            Field f = R.drawable.class.getDeclaredField(list.get(position).getName());
            holder.titleImg.setImageDrawable(context.getResources().getDrawable(f.getInt(R.drawable.class)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView titleImg;
    }
}