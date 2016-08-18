package com.chenney.smsforward.editsetting;

import android.support.v4.app.LoaderManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/8/16.
 */
@Module
public class EditSettingPresenterModule {

    private final EditSettingContract.View mView;
    private final LoaderManager mLoaderManager;

    EditSettingPresenterModule(EditSettingContract.View view,LoaderManager loaderManager){
        mView = view;
        mLoaderManager = loaderManager;
    }

    @Provides
    public EditSettingContract.View provideEditSettingContractView(){
        return mView;
    }

    @Provides
    public LoaderManager provideLoaderManager(){
        return mLoaderManager;
    }
}
