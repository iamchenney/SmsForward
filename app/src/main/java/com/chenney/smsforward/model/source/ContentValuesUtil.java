package com.chenney.smsforward.model.source;

import android.content.ContentValues;

import com.chenney.smsforward.model.bean.SettingsBean;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ContentValuesUtil {

    public static ContentValues fromSetting(SettingsBean settingsBean){
        ContentValues values = new ContentValues();
        values.put(PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID,settingsBean.getId());
        values.put(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_TO_PHONE_NUM,settingsBean.getReceiverPhone());
        values.put(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_PHONE,settingsBean.isSendNoReadCall()? 1 : 0);
        values.put(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_SMS,settingsBean.isSendNoReadSms()? 1 : 0);
        values.put(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_BATTERY,settingsBean.isSendBatteryAlerm()? 1:0);

        return values;
    }
}
