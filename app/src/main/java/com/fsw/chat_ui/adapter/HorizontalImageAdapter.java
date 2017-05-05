package com.fsw.chat_ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.CanDragImage;
import com.fsw.chat_ui.utils.FileHelper;
import com.fsw.chat_ui.widget.DragView;

import java.util.List;

/**
 * Created by Admin on 2017/4/6.
 */

public class HorizontalImageAdapter extends RecyclerView.Adapter<HorizontalImageAdapter.ViewHolder> {

    private Context context;

    private List<CanDragImage> images;

    private int imageParentHeight;

    private LinearLayout.LayoutParams lParams;

    private RelativeLayout.LayoutParams rParams;


    public HorizontalImageAdapter(Context context, List<CanDragImage> images, int imageParentHeight) {
        this.context = context;
        this.images = images;
        this.imageParentHeight = imageParentHeight;
    }

    @Override
    public HorizontalImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item_horizontal_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.dragView = (DragView) view.findViewById(R.id.drag_view);
        viewHolder.image_icon = (ImageView) view.findViewById(R.id.image_icon);
        viewHolder.isChecked = (CheckBox) view.findViewById(R.id.is_checked);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HorizontalImageAdapter.ViewHolder holder, int position) {
        final CanDragImage imageItem = images.get(position);
        Glide.with(context)
                .load(imageItem.path)
                .placeholder(R.mipmap.default_image)
                .into(holder.image_icon);
        Point point = FileHelper.getWhByImagePath(imageItem.path);
        final int width = point.x * imageParentHeight / point.y;
        lParams = new LinearLayout.LayoutParams(width, imageParentHeight);
        rParams = new RelativeLayout.LayoutParams(width, imageParentHeight);
        holder.dragView.setLayoutParams(lParams);
        holder.image_icon.setLayoutParams(rParams);
        holder.dragView.setOnDragListener(new DragView.onDragListener() {
            @Override
            public void onDragStart() {
                imageItem.onImageDragListener.onDragStartListener(holder.image_icon, width, imageParentHeight);
            }

            @Override
            public void onDrag(int ver) {
                imageItem.onImageDragListener.onImageDragStartListener(holder.image_icon, ver);
            }

            @Override
            public void onDragStop() {
                imageItem.onImageDragListener.onImageDragStopListener();
            }
        });
        holder.isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageItem.onImageDragListener.onCheckedListener(imageItem.path, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        DragView dragView;
        ImageView image_icon;
        CheckBox isChecked;

    }

}
