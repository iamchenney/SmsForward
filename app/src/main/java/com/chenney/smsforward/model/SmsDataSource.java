package com.chenney.smsforward.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.chenney.smsforward.model.bean.SmsBean;
import com.chenney.smsforward.model.source.PersistenceContract;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/16.
 */
public class SmsDataSource {

    public interface GetSmsCallBack{
        void onSmsLoaded(List<SmsBean> list);
        void onDataNotAvailable();
    }

    private ContentResolver mContentResolver;

    @Inject
    public SmsDataSource(@NonNull Context context){
        Preconditions.checkNotNull(context);
        mContentResolver = context.getContentResolver();
    }

    public void getSms(@NonNull GetSmsCallBack callBack){

    }

    public void readSms(String id){
        Preconditions.checkNotNull(id);

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.SmsEntry.COLUMN_NAME_READ,true);

        String selection = PersistenceContract.SmsEntry._ID + " LIKE ?";
        String[] selectionArgs = {id};

        mContentResolver.update(PersistenceContract.SmsEntry.buildUri(),values,selection,selectionArgs);
    }
}
