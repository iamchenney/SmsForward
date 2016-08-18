package com.chenney.smsforward.forward;

import com.chenney.smsforward.ApplicationModule;
import com.chenney.smsforward.model.SettingDataSourceComponent;
import com.chenney.smsforward.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Administrator on 2016/8/16.
 */
@FragmentScoped
@Component(dependencies = SettingDataSourceComponent.class, modules = {ForwardModule.class, ApplicationModule.class})
public interface ForwardComponent {
    void inject(ForwardActivity activity);
}
