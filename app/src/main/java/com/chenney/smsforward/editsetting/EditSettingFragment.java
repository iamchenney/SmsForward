package com.chenney.smsforward.editsetting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chenney.smsforward.R;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/12.
 */
public class EditSettingFragment extends Fragment implements EditSettingContract.View {

    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.unread_phone_ck)
    CheckBox unreadPhoneCk;
    @BindView(R.id.unread_sms_ck)
    CheckBox unreadSmsCk;
    @BindView(R.id.battery_ck)
    CheckBox batteryCk;

    private EditSettingContract.Persenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onCreate();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsBean bean = new SettingsBean();
                bean.setReceiverPhone(phoneNum.getText().toString());
                bean.setSendNoReadCall(unreadPhoneCk.isChecked());
                bean.setSendNoReadSms(unreadSmsCk.isChecked());
                bean.setSendBatteryAlerm(batteryCk.isChecked());
                mPresenter.saveSettings(bean);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.editsetting_frag, container, false);


        setHasOptionsMenu(true);
        setRetainInstance(true);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void showSettings(SettingsBean settingsBean) {
        phoneNum.setText(settingsBean.getReceiverPhone());
        unreadPhoneCk.setChecked(settingsBean.isSendNoReadCall());
        unreadSmsCk.setChecked(settingsBean.isSendNoReadSms());
        batteryCk.setChecked(settingsBean.isSendBatteryAlerm());
    }

    @Override
    public void showForward() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showError() {
        Snackbar.make(phoneNum, getString(R.string.error_phone_pattern), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void setPresenter(EditSettingContract.Persenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
