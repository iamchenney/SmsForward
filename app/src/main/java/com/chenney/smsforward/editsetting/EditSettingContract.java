package com.chenney.smsforward.editsetting;

import com.chenney.smsforward.IBaseView;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.IBasePresenter;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface EditSettingContract {

    interface View extends IBaseView<EditSettingContract.Persenter>{

        void showSettings(SettingsBean settingsBean);
        void showForward();
        void showError();

    }

    interface Persenter extends IBasePresenter{

        void saveSettings(SettingsBean settingsBean);

    }
}
