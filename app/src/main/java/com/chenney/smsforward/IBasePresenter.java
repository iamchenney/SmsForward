package com.chenney.smsforward;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface IBasePresenter {

    /**
     * 当Activity onCreate时调用
     */
    void onCreate();

    /**
     * 当Activity onDestory时调用
     */
    void onDestroy();

    void dismiss();

    void showToast(String msg);

    void onNetWorkError();
}
