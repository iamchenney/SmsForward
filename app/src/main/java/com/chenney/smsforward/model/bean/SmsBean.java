package com.chenney.smsforward.model.bean;

import android.database.Cursor;

import com.chenney.smsforward.model.source.PersistenceContract;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SmsBean {
    private String id;
    private String senderNum;
    private String contactId;
    private String senderName;
    private String body;
    private String time;
    private String senderTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SmsBean(String id, String senderNum, String contactId, String senderName, String body, String time, String senderTo) {
        this.id = id;
        this.senderNum = senderNum;
        this.contactId = contactId;
        this.senderName = senderName;
        this.body = body;
        this.time = time;
        this.senderTo = senderTo;
    }

    public String getSenderTo() {
        return senderTo;
    }

    public void setSenderTo(String senderTo) {
        this.senderTo = senderTo;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSenderNum() {
        return senderNum;
    }

    public void setSenderNum(String senderNum) {
        this.senderNum = senderNum;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static SmsBean from (Cursor cursor){
        String entryId = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry._ID));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry.COLUMN_NAME_ADDRESS));
        String msg = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry.COLUMN_NAME_BODY));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry.COLUMN_NAME_DATE));
        String person = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry.COLUMN_NAME_PERSON));
        String read = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.SmsEntry.COLUMN_NAME_READ));

        return new SmsBean(entryId,address,person,"",msg,date,"");
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "id='" + id + '\'' +
                ", senderNum='" + senderNum + '\'' +
                ", contactId='" + contactId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", body='" + body + '\'' +
                ", time='" + time + '\'' +
                ", senderTo='" + senderTo + '\'' +
                '}';
    }
}
