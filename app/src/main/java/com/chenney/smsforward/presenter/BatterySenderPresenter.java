package com.chenney.smsforward.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

import com.chenney.smsforward.forward.ForwardPresenter;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.util.StringUtil;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/17.
 */
public class BatterySenderPresenter implements BaseSenderPresenter{

    private  int level = 0;

    private boolean hasReceived = false;

    private SettingsBean settingsBean;

    @Inject
    Context mContext;

    ForwardPresenter forwardPresenter;

    public void setForwardPresenter(ForwardPresenter forwardPresenter) {
        this.forwardPresenter = forwardPresenter;
    }

    @Inject
    public BatterySenderPresenter(){}

    public SettingsBean getSettingsBean() {
        return settingsBean;
    }

    public void setSettingsBean(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            level = intent.getIntExtra("level",0);

            if(level > 20){
                hasReceived = false;
            }

            if(level < 10 && !hasReceived){
                hasReceived = true;
                if(StringUtil.isPhone(settingsBean.getReceiverPhone())){

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(settingsBean.getReceiverPhone(),null,"您的手机电量低",null,null);
                }
            }
        }
    };

    @Override
    public void start() {
        mContext.registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void stop() {
        mContext.unregisterReceiver(batteryReceiver);
    }


}
