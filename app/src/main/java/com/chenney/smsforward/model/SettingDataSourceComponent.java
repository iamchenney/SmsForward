package com.chenney.smsforward.model;

import com.chenney.smsforward.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/8/15.
 */
@Singleton
@Component(modules = {SettingDataSourceModule.class, ApplicationModule.class})
public interface SettingDataSourceComponent {
    SettingDataSource getSettingDataSource();
}
