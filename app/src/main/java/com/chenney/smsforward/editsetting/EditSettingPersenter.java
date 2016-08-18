package com.chenney.smsforward.editsetting;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.chenney.smsforward.model.SettingDataSource;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.model.source.LoaderProvider;
import com.chenney.smsforward.util.StringUtil;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/15.
 */
public class EditSettingPersenter implements EditSettingContract.Persenter,LoaderManager.LoaderCallbacks<Cursor>,SettingDataSource.GetSettingCallBack {

    public static final int SETTING_LOADER = 1;

    private LoaderProvider mLoaderProvider;
    private SettingDataSource mSettingDataSource;
    private EditSettingContract.View mView;
    private LoaderManager mLoaderManager;

    @Inject
    public EditSettingPersenter(LoaderProvider mLoaderProvider,
                                SettingDataSource mSettingDataSource,
                                EditSettingContract.View mView, LoaderManager mLoaderManager) {
        this.mLoaderProvider = mLoaderProvider;
        this.mSettingDataSource = mSettingDataSource;
        this.mView = mView;
        this.mLoaderManager = mLoaderManager;
    }

    @Inject
    void setPresenter(){
        mView.setPresenter(this);
    }

    @Override
    public void onSettingLoaded(SettingsBean settingsBean) {
        mLoaderManager.initLoader(SETTING_LOADER,null,this);
    }

    @Override
    public void onDataNotAvailable() {
    }

    @Override
    public void onCreate() {
        loadSetting();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onNetWorkError() {

    }

    private void loadSetting(){
        mSettingDataSource.getSetting(this);
    }

    private void showSetting(Cursor cursor){
        mView.showSettings(SettingsBean.from(cursor));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createSettingLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            if(data.moveToLast()){
                showSetting(data);
            }
        }else{
            onDataNotAvailable();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void saveSettings(SettingsBean settingsBean) {

        if(!StringUtil.isPhone(settingsBean.getReceiverPhone())){
            mView.showError();
        }else{

            mSettingDataSource.saveSetting(settingsBean);
            mView.showForward();
        }

    }
}
