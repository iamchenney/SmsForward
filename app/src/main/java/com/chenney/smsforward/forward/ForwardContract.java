package com.chenney.smsforward.forward;

import com.chenney.smsforward.IBaseView;
import com.chenney.smsforward.model.bean.SettingsBean;
import com.chenney.smsforward.IBasePresenter;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface ForwardContract {
    interface View extends IBaseView<Presenter>{
        void showEditSetting();
        void showSetting(SettingsBean settingsBean);
        void showLoading(boolean isActive);
        void showEmptyView();
    }

    interface Presenter extends IBasePresenter{
        void loadSetting();
        void startSender();
        void stopSender();
    }
}
