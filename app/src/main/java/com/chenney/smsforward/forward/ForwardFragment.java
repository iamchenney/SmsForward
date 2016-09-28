package com.chenney.smsforward.forward;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenney.smsforward.R;
import com.chenney.smsforward.adapt.StringAdapter;
import com.chenney.smsforward.editsetting.EditSettingActivity;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.ui.ScrollChildSwipeRefreshLayout;
import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForwardFragment extends Fragment implements ForwardContract.View {

    ForwardContract.Presenter mPersenter;

    @BindView(R.id.noTasks)
    LinearLayout noTasks;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    @BindView(R.id.filteringLabel)
    TextView filteringLabel;
    @BindView(R.id.tasksLL)
    LinearLayout tasksLL;
    @BindView(R.id.noTasksIcon)
    ImageView noTasksIcon;
    @BindView(R.id.noTasksMain)
    TextView noTasksMain;
    @BindView(R.id.noTasksAdd)
    TextView noTasksAdd;
    @BindView(R.id.tasksContainer)
    RelativeLayout tasksContainer;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.unread_phone_ck)
    CheckBox unreadPhoneCk;
    @BindView(R.id.unread_sms_ck)
    CheckBox unreadSmsCk;
    @BindView(R.id.battery_ck)
    CheckBox batteryCk;

    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private StringAdapter adapter;

    private LinearLayoutManager layoutManager;

    public ForwardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);


        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(layoutManager);

        adapter = new StringAdapter(getContext());

        recycleView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPersenter.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit);
        fab.setImageResource(R.drawable.ic_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditSetting();
            }
        });
    }

    @Override
    public void showLoading(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) refreshLayout;

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showEmptyView() {
        tasksLL.setVisibility(View.GONE);
        noTasks.setVisibility(View.VISIBLE);

        noTasksMain.setText(getString(R.string.no_tasks_all));
        noTasksIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_assignment_turned_in_24dp));
    }

    @Override
    public void showLog(List<String> logs) {
        adapter.setDatas(logs);
    }


    @Override
    public void showEditSetting() {
        Intent intent = new Intent(getContext(), EditSettingActivity.class);
        startActivityForResult(intent, EditSettingActivity.REQUEST_EDIT_CODE);
    }

    @Override
    public void showSetting(SettingsBean settingsBean) {
        tasksLL.setVisibility(View.VISIBLE);
        noTasks.setVisibility(View.GONE);

        phoneNum.setText(settingsBean.getReceiverPhone());
        unreadPhoneCk.setChecked(settingsBean.isSendNoReadCall());
        unreadSmsCk.setChecked(settingsBean.isSendNoReadSms());
        batteryCk.setChecked(settingsBean.isSendBatteryAlerm());

    }


    @Override
    public void setPresenter(ForwardContract.Presenter persenter) {
        mPersenter = Preconditions.checkNotNull(persenter);
    }

    @OnClick(R.id.send_button)
    public void onClick() {
        if (sendButton.getText().toString().equals(getString(R.string.start_send_label))) {
            mPersenter.startSender();
            sendButton.setText(getString(R.string.stop_send_label));
        } else {
            mPersenter.stopSender();
            sendButton.setText(getString(R.string.start_send_label));
        }
    }
}
