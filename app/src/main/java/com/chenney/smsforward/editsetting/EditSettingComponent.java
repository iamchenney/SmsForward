package com.chenney.smsforward.editsetting;

import com.chenney.smsforward.ApplicationModule;
import com.chenney.smsforward.model.SettingDataSourceComponent;
import com.chenney.smsforward.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Administrator on 2016/8/16.
 */
@FragmentScoped
@Component(dependencies = SettingDataSourceComponent.class,modules = {EditSettingPresenterModule.class, ApplicationModule.class})
public interface EditSettingComponent {
    void inject(EditSettingActivity activity);
}
