package com.chenney.smsforward.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.telephony.SmsManager;

import com.chenney.smsforward.model.CallDataSource;
import com.chenney.smsforward.model.bean.PhoneBean;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.model.source.LoaderProvider;
import com.chenney.smsforward.util.StringUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/17.
 */
public class CallSenderPresenter implements BaseSenderPresenter,LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderManager mLoaderManager;
    private LoaderProvider mLoaderProvider;
    private CallDataSource mCallDataSource;

    private SettingsBean settingsBean;

    private SendThread mSendThread;

    private Map<String,String> sendedMap = new HashMap<>();

    @Inject
    Context mContext;

    @Inject
    public CallSenderPresenter(LoaderManager mLoaderManager, LoaderProvider mLoaderProvider, CallDataSource mCallDataSource) {
        this.mLoaderManager = mLoaderManager;
        this.mLoaderProvider = mLoaderProvider;
        this.mCallDataSource = mCallDataSource;
    }

    public SettingsBean getSettingsBean() {
        return settingsBean;
    }

    public void setSettingsBean(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    @Override
    public void start() {
        stop();
        mSendThread = new SendThread();
        mSendThread.start();
    }

    @Override
    public void stop() {
        if(mSendThread != null){
            mSendThread.setbRun(false);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createCallLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            if(data.moveToLast()){
                send(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void loadCall(){
        mLoaderManager.initLoader(3,null,this);
    }

    private void send(Cursor cursor){

        cursor.moveToPosition(-1);

        while (cursor.moveToNext()){
            PhoneBean phoneBean = PhoneBean.from(cursor);

            String callLogID = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls._ID));

            if(sendedMap.containsKey(callLogID)){
                continue;
            }

            String callNumber = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
            String callDate = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE));
            String callType = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE));
            String isCallNew = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NEW));
            String isRead = cursor.getString(cursor.getColumnIndex(CallLog.Calls.IS_READ));
            String contactId="";
            try{
                contactId = cursor.getString(cursor.getColumnIndexOrThrow("contactid"));

            }catch (Exception e){
                try{
                    contactId = cursor.getString(cursor.getColumnIndexOrThrow("contact_id"));

                }catch (Exception ex){

                }
            }

            String userName = ContactPresenter.getContact(mContext.getContentResolver(),contactId);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);

            long dayStartTime = calendar.getTimeInMillis();

            if(dayStartTime < Long.valueOf(callDate)){
                if(StringUtil.isPhone(settingsBean.getReceiverPhone())){

                    SmsManager smsManager = SmsManager.getDefault();

                    String msg = " 未接来电==" + callNumber+ ","+userName;

                    smsManager.sendTextMessage(settingsBean.getReceiverPhone(),null,msg,null,null);

                    sendedMap.put(callLogID,"1");
                }
            }

            mCallDataSource.readCall(phoneBean.getId());
        }


    }

    Handler MyHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            loadCall();
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
