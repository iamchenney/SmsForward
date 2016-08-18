package com.chenney.smsforward;

import android.app.Application;

import com.chenney.smsforward.model.DaggerSettingDataSourceComponent;
import com.chenney.smsforward.model.SettingDataSourceComponent;
import com.chenney.smsforward.model.SettingDataSourceModule;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ForwardApplication extends Application {

    private SettingDataSourceComponent mSettingDataSourceComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        mSettingDataSourceComponent = DaggerSettingDataSourceComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .settingDataSourceModule(new SettingDataSourceModule()).build();
    }

    public SettingDataSourceComponent getSettingDataSourceComponent(){
        return mSettingDataSourceComponent;
    }
}
