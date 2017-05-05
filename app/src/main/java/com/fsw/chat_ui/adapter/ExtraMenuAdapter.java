package com.fsw.chat_ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.ExtraMenu;
import com.fsw.chat_ui.widget.SquareLayout;

import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ExtraMenuAdapter extends BaseAdapter {

    private Context context;

    private List<ExtraMenu> list;


    private LinearLayout.LayoutParams params;

    public ExtraMenuAdapter(Context context, List<ExtraMenu> list, int itemHeight, int squareWidth) {
        this.context = context;
        this.list = list;
        params = new LinearLayout.LayoutParams(squareWidth, itemHeight);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ExtraMenu getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_extra_menu_list_item, null);
            holder.iconContainer = (SquareLayout) convertView.findViewById(R.id.icon_container);
            holder.menuIcon = (ImageView) convertView.findViewById(R.id.menu_icon);
            holder.item_main = (LinearLayout) convertView.findViewById(R.id.item_main);
            holder.menuName = (TextView) convertView.findViewById(R.id.menu_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_main.setLayoutParams(params);
        ExtraMenu menu = list.get(position);
        holder.iconContainer.setBackground(context.getResources().getDrawable(menu.getBackground()));
        holder.menuIcon.setBackgroundDrawable(context.getResources().getDrawable(menu.getIcon()));
        if (!TextUtils.isEmpty(menu.getName())) {
            holder.menuName.setText(menu.getName());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).clickListener != null) {
                    getItem(position).clickListener.onClick(getItem(position).id, v);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        LinearLayout item_main;
        SquareLayout iconContainer;
        ImageView menuIcon;
        TextView menuName;
    }

}
