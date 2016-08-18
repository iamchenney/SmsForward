package com.chenney.smsforward.forward;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.chenney.smsforward.model.SettingDataSource;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.model.source.LoaderProvider;
import com.chenney.smsforward.presenter.BatterySenderPresenter;
import com.chenney.smsforward.presenter.CallSenderPresenter;
import com.chenney.smsforward.presenter.SmsSenderPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ForwardPresenter implements ForwardContract.Presenter,LoaderManager.LoaderCallbacks<Cursor>,SettingDataSource.GetSettingCallBack {

    public static final int SETTING_LOADER = 1;

    private LoaderProvider mLoaderProvider;
    private SettingDataSource mSettingDataSource;
    private ForwardContract.View mView;
    private LoaderManager mLoaderManager;

    private SettingsBean mSetting;

    @Inject
    SmsSenderPresenter mSenderPresenter;
    @Inject
    CallSenderPresenter mCallSenderPresenter;
    @Inject
    BatterySenderPresenter mBatterySenderPresenter;

    @Inject
    public ForwardPresenter(LoaderProvider mLoaderProvider,
                            SettingDataSource mSettingDataSource,
                            ForwardContract.View mView, LoaderManager mLoaderManager) {
        this.mLoaderProvider = mLoaderProvider;
        this.mSettingDataSource = mSettingDataSource;
        this.mView = mView;
        this.mLoaderManager = mLoaderManager;
    }

    @Inject
    void setupPresenter(){
        mView.setPresenter(this);
    }

    @Override
    public void loadSetting() {
        mView.showLoading(true);
        mSettingDataSource.getSetting(this);
    }

    private void showSetting(Cursor cursor){
        mView.showLoading(false);
        mSetting = SettingsBean.from(cursor);

        mView.showSetting(mSetting);

        mSenderPresenter.setSettingsBean(mSetting);
        mCallSenderPresenter.setSettingsBean(mSetting);
        mBatterySenderPresenter.setSettingsBean(mSetting);
    }

    private void showEmptySetting(){
        mView.showLoading(false);
        mView.showEmptyView();
    }

    @Override
    public void startSender() {
        if(mSetting.isSendNoReadSms()){
            mSenderPresenter.setSettingsBean(mSetting);
            mSenderPresenter.start();
        }
        if(mSetting.isSendNoReadCall()){
            mCallSenderPresenter.setSettingsBean(mSetting);
            mCallSenderPresenter.start();
        }
        if(mSetting.isSendBatteryAlerm()){
            mBatterySenderPresenter.setSettingsBean(mSetting);
            mBatterySenderPresenter.start();
        }
    }

    @Override
    public void stopSender() {
        mSenderPresenter.stop();
        mCallSenderPresenter.stop();
        mBatterySenderPresenter.stop();
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

    @Override
    public void onSettingLoaded(SettingsBean settingsBean) {
        mLoaderManager.initLoader(SETTING_LOADER,null,this);
    }

    @Override
    public void onDataNotAvailable() {
        mView.showEmptyView();
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
            }else{
                showEmptySetting();
            }
        }else{
            onDataNotAvailable();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
