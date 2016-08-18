package com.chenney.smsforward.model.bean;

import android.database.Cursor;

import com.chenney.smsforward.model.source.PersistenceContract;

/**
 * Created by Administrator on 2016/8/11.
 */
public class PhoneBean {
    private String id;
    private String phone;
    private String time;
    private String contactId;
    private String userName;

    public PhoneBean(String id, String phone, String time, String contactId, String userName) {
        this.id = id;
        this.phone = phone;
        this.time = time;
        this.contactId = contactId;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static PhoneBean from (Cursor cursor){
        String entryId = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.CallEntry._ID));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.CallEntry.NUMBER));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.CallEntry.DATE));
        String person = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.CallEntry.NEW));
        String read = cursor.getString(cursor.getColumnIndexOrThrow(
                PersistenceContract.CallEntry.IS_READ));

        return new PhoneBean(entryId,phone,date,person,"");
    }

    @Override
    public String toString() {
        return "PhoneBean{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", contactId='" + contactId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
