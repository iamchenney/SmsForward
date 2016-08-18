package com.chenney.smsforward.model.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.chenney.smsforward.model.source.PersistenceContract;

/**
 * Created by Administrator on 2016/8/12.
 */
public class SettingsBean {
    private String id;
    private String receiverPhone;
    private boolean sendNoReadSms;
    private boolean sendNoReadCall;
    private String batteryAlarmNum;
    private boolean sendBatteryAlerm;

    public SettingsBean(){
        this.id = "1";
    }

    public SettingsBean(String id, String receiverPhone, boolean sendNoReadSms, boolean sendNoReadCall, boolean sendBatteryAlerm, String batteryAlarmNum) {
        this.id = id;
        this.receiverPhone = receiverPhone;
        this.sendNoReadSms = sendNoReadSms;
        this.sendNoReadCall = sendNoReadCall;
        this.sendBatteryAlerm = sendBatteryAlerm;
        this.batteryAlarmNum = batteryAlarmNum;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public boolean isSendNoReadSms() {
        return sendNoReadSms;
    }

    public void setSendNoReadSms(boolean sendNoReadSms) {
        this.sendNoReadSms = sendNoReadSms;
    }

    public boolean isSendNoReadCall() {
        return sendNoReadCall;
    }

    public void setSendNoReadCall(boolean sendNoReadCall) {
        this.sendNoReadCall = sendNoReadCall;
    }

    public String getBatteryAlarmNum() {
        return batteryAlarmNum;
    }

    public void setBatteryAlarmNum(String batteryAlarmNum) {
        this.batteryAlarmNum = batteryAlarmNum;
    }

    public boolean isSendBatteryAlerm() {
        return sendBatteryAlerm;
    }

    public void setSendBatteryAlerm(boolean sendBatteryAlerm) {
        this.sendBatteryAlerm = sendBatteryAlerm;
    }

    public static SettingsBean from (Cursor cursor){
        String entryId = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID));
        String sendTo = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_TO_PHONE_NUM));
        boolean sendSms = cursor.getInt(cursor.getColumnIndexOrThrow(
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_SMS)) == 1;
        boolean sendPhone = cursor.getInt(cursor.getColumnIndexOrThrow(
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_PHONE)) == 1;
        boolean sendBattery = cursor.getInt(cursor.getColumnIndexOrThrow(
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_BATTERY)) == 1;

       return new SettingsBean(entryId,sendTo,sendSms,sendPhone,sendBattery,"10");
    }

    public static SettingsBean from (ContentValues values){
        String entryId = values.getAsString(PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID);
        String sendTo = values.getAsString(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_TO_PHONE_NUM);
        boolean sendSms = values.getAsInteger(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_SMS) == 1;
        boolean sendPhone = values.getAsInteger(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_PHONE) == 1;
        boolean sendBattery = values.getAsInteger(PersistenceContract.SettingEntry.COLUMN_NAME_SEND_BATTERY) == 1;

        return new SettingsBean(entryId,sendTo,sendSms,sendPhone,sendBattery,"10");
    }
}
