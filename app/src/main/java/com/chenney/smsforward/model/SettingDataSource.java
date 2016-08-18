package com.chenney.smsforward.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.model.source.ContentValuesUtil;
import com.chenney.smsforward.model.source.PersistenceContract;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by Administrator on 2016/8/15.
 */
@Singleton
public class SettingDataSource {

    private ContentResolver mContentResolver;

    public interface GetSettingCallBack{
        void onSettingLoaded(SettingsBean settingsBean);
        void onDataNotAvailable();
    }

    @Inject
    SettingDataSource(@NonNull Context context){
        checkNotNull(context);
        mContentResolver = context.getContentResolver();
    }

    public void saveSetting(@NonNull SettingsBean settingsBean){
        checkNotNull(settingsBean);

        ContentValues values = ContentValuesUtil.fromSetting(settingsBean);

        mContentResolver.insert(PersistenceContract.SettingEntry.buildTasksUri(),values);
    }

    public void getSetting(@NonNull GetSettingCallBack callBack){
        // no-op since the data is loaded via Cursor Loader
        callBack.onSettingLoaded(null);
    }
}
