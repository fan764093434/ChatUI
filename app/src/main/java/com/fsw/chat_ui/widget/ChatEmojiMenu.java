package com.fsw.chat_ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.ViewPagerAdapter;
import com.fsw.chat_ui.entity.Emotion;
import com.fsw.chat_ui.utils.EmojiUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatEmojiMenu extends LinearLayout {
    /**
     * 表情指示
     */
    private MagicIndicator magicIndicator;
    /**
     * 表情页
     */
    private ViewPager emojiViewPager;
    /**
     * 所有表情
     */
    private Map<Integer, List<Emotion>> emotionMap;
    /**
     * 表情的点击事件
     */
    private OnEmojiEmojiItemClickListener onEmojiEmojiItemClickListener;

    public void setOnEmojiEmojiItemClickListener(OnEmojiEmojiItemClickListener onEmojiEmojiItemClickListener) {
        this.onEmojiEmojiItemClickListener = onEmojiEmojiItemClickListener;
    }

    public ChatEmojiMenu(Context context) {
        this(context, null);
    }

    public ChatEmojiMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatEmojiMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatEmojiMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_view_emoji_menu, this);
        emojiViewPager = (ViewPager) findViewById(R.id.emoji_view_pager);
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        emotionMap = new HashMap<>();
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            InputStream inputStream = null;
            try {
                inputStream = context.getResources().getAssets().open("emoji.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Emotion> emotions = EmojiUtil.getEmotions(inputStream);
            emotionMap.put(i, emotions);
            ChatEmojiItem chatEmojiItem = new ChatEmojiItem(context, emotions);
            viewList.add(chatEmojiItem);
            //设置表情点击事件
            chatEmojiItem.setOnEmojiGridItemClickListener(new ChatEmojiItem.OnEmojiGridItemClickListener() {
                @Override
                public void onEmojiGridItemClickListener(Emotion emotion) {
                    if (onEmojiEmojiItemClickListener != null) {
                        onEmojiEmojiItemClickListener.onEmojiEmojiItemClickListener(emotion);
                    }
                }
            });
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewList);
        emojiViewPager.setAdapter(viewPagerAdapter);
        viewPagerBindMagicIndicator(context);
    }


    /**
     * 设置MagicIndicator的基本属性
     * 并将ViewPager和MagicIndicator进行绑定
     */
    private void viewPagerBindMagicIndicator(Context context) {
        magicIndicator.setBackgroundColor(0xf8f8f8);
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return emotionMap.size();
            }

            @Override
            public IPagerTitleView getTitleView(final Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.chat_simple_pager_title_layout, null);
                final ImageView titleImg = (ImageView) customLayout.findViewById(R.id.title_img);
                final View titleBack = customLayout.findViewById(R.id.title_bak);
                //设置每一类表情列表的第一个为指示图标
                Field f = null;
                try {
                    f = R.drawable.class.getDeclaredField(emotionMap.get(index).get(0).getName());
                    titleImg.setImageDrawable(context.getResources().getDrawable(f.getInt(R.drawable.class)));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleBack.setBackgroundColor(context.getResources().getColor(R.color.chatEmojiMagicBackSelect));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleBack.setBackgroundColor(context.getResources().getColor(R.color.chatEmojiMagicBack));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        titleBack.setBackgroundColor(context.getResources().getColor(R.color.chatEmojiMagicBack));
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleBack.setBackgroundColor(context.getResources().getColor(R.color.chatEmojiMagicBackSelect));
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emojiViewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }


        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, emojiViewPager);
    }

    protected interface OnEmojiEmojiItemClickListener {
        /**
         * 当表情选择事件
         */
        void onEmojiEmojiItemClickListener(Emotion emotion);
    }

}
