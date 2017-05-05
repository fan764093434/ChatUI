package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.HorizontalImageAdapter;
import com.fsw.chat_ui.entity.CanDragImage;
import com.fsw.chat_ui.interfaces.OnImageDragListener;
import com.fsw.chat_ui.utils.DisplayUtil;
import com.lzy.imagepicker.ImageDataSource;
import com.lzy.imagepicker.bean.ImageFolder;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/4/6.
 */

public class ChatImageMenu extends LinearLayout implements View.OnClickListener, ImageDataSource.OnImagesLoadedListener {

    private ArrayList<ImageItem> images;

    private List<CanDragImage> canDragImages;

    private View notImageView;

    private LowSensitiveRecyclerView imageParent;

    private HorizontalImageAdapter adapter;

    private int imageParentHeight = 0;

    private OnOpenAlbumListener onOpenAlbumListener;

    private OnImageDragListener onImageDragListener;


    public void setOnOpenAlbumListener(OnOpenAlbumListener onOpenAlbumListener) {
        this.onOpenAlbumListener = onOpenAlbumListener;
    }

    public ChatImageMenu(Context context) {
        this(context, null);
    }

    public ChatImageMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatImageMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     */
    private void initView(Context context) {
        imageParentHeight = DisplayUtil.dp2px(140);
        LayoutInflater.from(context).inflate(R.layout.chat_view_image_menu, this);
        notImageView = findViewById(R.id.not_image_view);
        imageParent = (LowSensitiveRecyclerView) findViewById(R.id.image_parent);
        findViewById(R.id.open_album).setOnClickListener(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageParent.setLayoutManager(linearLayoutManager);
        //设置适配器
        canDragImages = new ArrayList<>();
        adapter = new HorizontalImageAdapter(getContext(), canDragImages, imageParentHeight);
        imageParent.setAdapter(adapter);
    }

    /**
     * 加载图片
     */
    public void loadAllImage() {
        if (images == null || images.size() < 1) {
            new ImageDataSource((AppCompatActivity) getContext(), null, this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_album:
                if (onOpenAlbumListener != null) {
                    onOpenAlbumListener.onOpenAlbumListener();
                }
                break;
        }
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        canDragImages.clear();
        this.images = imageFolders.get(0).images;
        for (int i = 0; i < images.size(); i++) {
            CanDragImage canDragImage = new CanDragImage();
            canDragImage.path = images.get(i).path;
            if (onImageDragListener != null) {
                canDragImage.onImageDragListener = onImageDragListener;
            }
            canDragImages.add(canDragImage);
        }
        if (canDragImages.size() < 1) {
            notImageView.setVisibility(View.VISIBLE);
            imageParent.setVisibility(View.GONE);
        } else {
            notImageView.setVisibility(View.GONE);
            imageParent.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 当点击打开相册监听
     */
    public interface OnOpenAlbumListener {
        void onOpenAlbumListener();
    }

    /**
     * register menu item
     */
    public void setOnImageDragListener(OnImageDragListener listener) {
        this.onImageDragListener = listener;
    }


}
