package com.fsw.chat_ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.ViewPagerAdapter;
import com.fsw.chat_ui.widget.ProjectToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsw on 2017/3/29.
 * use 包红包页面
 */

public class ChatPrepareRedPacketActivity extends BaseActivity {

    private ProjectToolbar toolbar;

    private EditText amountInput;

    private TextView redPacketTypeText;

    private ViewPager viewPager;
    private List<View> viewList;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_prepare_red_packet);
        initView();
    }

    private void initView() {
        toolbar = (ProjectToolbar) findViewById(R.id.toolbar);
        toolbar.setCenterTitle("发送红包");
        toolbar.setMenuTwoIcon(R.drawable.chat_icon_config_white_36dp);
        toolbar.getNavigationIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        redPacketTypeText = (TextView) findViewById(R.id.red_packet_type_text);
        findViewById(R.id.red_packet_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    redPacketTypeText.setText("普通红包");
                } else if (position == 1) {
                    redPacketTypeText.setText("安全红包");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(ChatPrepareRedPacketActivity.this);
        View normalRedPacket = inflater.inflate(R.layout.chat_view_prepare_normal_red_packet, null);
        viewList.add(normalRedPacket);
        View safeRedPacket = inflater.inflate(R.layout.chat_view_prepare_safe_red_packet, null);
        viewList.add(safeRedPacket);
        adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);

        amountInput = (EditText) normalRedPacket.findViewById(R.id.amount_input);
        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        amountInput.setText(s);
                        amountInput.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    amountInput.setText(s);
                    amountInput.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        amountInput.setText(s.subSequence(0, 1));
                        amountInput.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    return;
                }
                if (Float.parseFloat(s.toString()) < 0 || Float.parseFloat(s.toString()) > 222) {
                    Toast.makeText(ChatPrepareRedPacketActivity.this, "红包金额不能小于0或者大于222", Toast.LENGTH_SHORT).show();
                    amountInput.setText(null);
                }
            }
        });
    }


}
