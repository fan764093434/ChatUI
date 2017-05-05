package com.fsw.chat_ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.PayWayAdapter;
import com.fsw.chat_ui.utils.DisplayUtil;
import com.fsw.chat_ui.widget.ProjectToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by fsw on 2017/3/30.
 * use 转账
 */

public class ChatTransferAccountsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    ProjectToolbar toolbar;

    private PopupWindow window;
    /**
     * 付款方式列表
     */
    private ListView payWayListView;
    private List<String> payWayList;
    private PayWayAdapter payWayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_transfer_accounts);
        ButterKnife.inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    private void initView() {
        toolbar.getNavigationIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setCenterTitle("转账汇款");
        toolbar.setMenuTwoIcon(R.drawable.chat_icon_menu_record);
        payWayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            payWayList.add("");
        }
        payWayAdapter = new PayWayAdapter(this, payWayList);
    }


    @OnClick({R.id.choose_pay_way})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_pay_way:
                buildPickerWindow();
                break;
        }
    }


    public void buildPickerWindow() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.chat_pop_choose_pay_way, null);
        outerView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null && window.isShowing()) {
                    window.dismiss();
                }
            }
        });
        payWayListView = (ListView) outerView.findViewById(R.id.pay_way_list_view);
        payWayListView.setAdapter(payWayAdapter);
        window = new PopupWindow(outerView, RelativeLayout.LayoutParams.MATCH_PARENT, DisplayUtil.getDisplayMetrics(this).heightPixels * 3 / 7, true);
        window.setFocusable(true);
        window.setAnimationStyle(R.style.AnimationPopWindow);
        window.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.pop_back));//必须给popupWindow设置一个背景
        window.setOutsideTouchable(true);//这样点击空白区域才可以关闭popupWindow
        //显示窗口
        window.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        //设置整体背景暗透明
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.7f;
        this.getWindow().setAttributes(params);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = ChatTransferAccountsActivity.this.getWindow().getAttributes();
                params.alpha = 1f;
                ChatTransferAccountsActivity.this.getWindow().setAttributes(params);
            }
        });

    }
}
