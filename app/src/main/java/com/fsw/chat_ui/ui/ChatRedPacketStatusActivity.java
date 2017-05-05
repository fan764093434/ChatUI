package com.fsw.chat_ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.RedPacketReceiverAdapter;
import com.fsw.chat_ui.widget.ProjectToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by fsw on 2017/3/29.
 * 当前红包状态页面
 */

public class ChatRedPacketStatusActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    ProjectToolbar toolbar;
    /**
     * 发红包的人
     * xxx的红包
     */
    @InjectView(R.id.red_packet_owner)
    TextView redPacketOwner;
    /**
     * 领取人列表
     * 适配器
     */
    @InjectView(R.id.receiver_list_view)
    ListView receiverListView;
    private List<String> receiverList;
    private RedPacketReceiverAdapter receiverAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_red_packet_status);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        toolbar.getNavigationIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setCenterTitle("红包记录");
        toolbar.setMenuTwoIcon(R.drawable.chat_icon_menu_record);
        redPacketOwner.setFocusable(true);
        redPacketOwner.setFocusableInTouchMode(true);
        redPacketOwner.requestFocus();
        receiverList = new ArrayList<>();
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverList.add("");
        receiverAdapter = new RedPacketReceiverAdapter(this, receiverList);
        receiverListView.setAdapter(receiverAdapter);
    }

}
