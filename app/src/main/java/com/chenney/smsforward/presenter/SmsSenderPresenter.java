package com.chenney.smsforward.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.telephony.SmsManager;

import com.chenney.smsforward.forward.ForwardPresenter;
import com.chenney.smsforward.model.SmsDataSource;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.model.bean.SmsBean;
import com.chenney.smsforward.model.source.LoaderProvider;
import com.chenney.smsforward.util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/16.
 */
public class SmsSenderPresenter implements BaseSenderPresenter, LoaderManager.LoaderCallbacks<Cursor>,SmsDataSource.GetSmsCallBack {

    public static final int SMS_LOADER = 2;

    private LoaderProvider mLoaderProvider;
    private SmsDataSource mSmsDataSource;
    private LoaderManager mLoaderManager;

    private SendThread mSendThread;

    private SettingsBean settingsBean;

    private Map<String,String> sendedMap = new HashMap<>();

    ForwardPresenter forwardPresenter;

    public void setForwardPresenter(ForwardPresenter forwardPresenter) {
        this.forwardPresenter = forwardPresenter;
    }

    @Inject
    public SmsSenderPresenter(LoaderProvider mLoaderProvider, SmsDataSource mSmsDataSource, LoaderManager mLoaderManager) {
        this.mLoaderProvider = mLoaderProvider;
        this.mSmsDataSource = mSmsDataSource;
        this.mLoaderManager = mLoaderManager;
    }

    public SettingsBean getSettingsBean() {
        return settingsBean;
    }

    public void setSettingsBean(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    public void start(){
        stop();

        mSendThread = new SendThread();
        mSendThread.start();

    }

    public void stop(){
        if(mSendThread != null){
            mSendThread.setbRun(false);
        }
    }

    @Override
    public void onSmsLoaded(List<SmsBean> list) {

    }

    @Override
    public void onDataNotAvailable() {

    }

    private void loadSms(){
        mLoaderManager.initLoader(SMS_LOADER,null,this);
    }

    private void send(Cursor cursor){
        forwardPresenter.addLog("短信进入发送阶段");
        cursor.moveToPosition(-1);

        while (cursor.moveToNext()){
            SmsBean smsBean = SmsBean.from(cursor);

            if(sendedMap.containsKey(smsBean.getId())){
                continue;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);

            long dayStartTime = calendar.getTimeInMillis();

            forwardPresenter.addLog("短信收到时间："+smsBean.getTime());

            if(dayStartTime < Long.valueOf(smsBean.getTime())) {


                if(StringUtil.isPhone(settingsBean.getReceiverPhone())){
                    SmsManager smsManager = SmsManager.getDefault();

                    String msg = smsBean.getBody() + " ==收到来自" + smsBean.getSenderNum();

                    List<String> msgs = smsManager.divideMessage(msg);

                    for (String sms : msgs) {
                        smsManager.sendTextMessage(settingsBean.getReceiverPhone(),null,sms,null,null);
                    }

                    sendedMap.put(smsBean.getId(),"1");
                }


            }

            mSmsDataSource.readSms(smsBean.getId());
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createSmsLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        forwardPresenter.addLog("读取短信结束");
        if(data != null){
            forwardPresenter.addLog("短信数据不为空");
            if(data.moveToLast()){
                send(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    Handler MyHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            loadSms();
            return true;
        }
    });

    class SendThread extends Thread{

        private boolean bRun = true;

        public boolean isbRun() {
            return bRun;
        }

        public void setbRun(boolean bRun) {
            this.bRun = bRun;
        }

        @Override
        public void run() {
            while (bRun){

                Message logMessage = forwardPresenter.LogHandler.obtainMessage();
                logMessage.obj = "短信监控正在运行中....";
                forwardPresenter.LogHandler.sendMessage(logMessage);

                Message message = new Message();
                message.what = 1;
                MyHandler.sendMessage(message);
                try {
                    Thread.sleep(1000 * 4 *5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
