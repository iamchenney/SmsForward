package com.chenney.smsforward.forward;

import android.support.v4.app.LoaderManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/8/16.
 */
@Module
public class ForwardModule {

    private final ForwardContract.View mView;
    private final LoaderManager mLoaderManager;

    public ForwardModule(ForwardContract.View view,LoaderManager loaderManager){
        mView = view;
        mLoaderManager = loaderManager;
    }

    @Provides
    ForwardContract.View providerForwardContractView(){
        return mView;
    }

    @Provides
    LoaderManager provideLoaderManager(){
        return mLoaderManager;
    }

}
