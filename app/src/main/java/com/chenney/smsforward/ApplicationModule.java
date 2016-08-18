package com.chenney.smsforward;

import android.content.Context;

import com.chenney.smsforward.model.source.LoaderProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/8/16.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context){
        mContext = context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }

    @Provides
    LoaderProvider provideLoadProvider(){
        return new LoaderProvider(mContext);
    }
}
