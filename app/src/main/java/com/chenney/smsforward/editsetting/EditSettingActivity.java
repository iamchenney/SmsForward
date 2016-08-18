package com.chenney.smsforward.editsetting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chenney.smsforward.ApplicationModule;
import com.chenney.smsforward.ForwardApplication;
import com.chenney.smsforward.R;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/12.
 */
public class EditSettingActivity extends AppCompatActivity {

    public static final int REQUEST_EDIT_CODE = 1;

    @Inject
    EditSettingPersenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsetting_act);

        EditSettingFragment fragment =
                (EditSettingFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        DaggerEditSettingComponent.builder()
                .settingDataSourceComponent(((ForwardApplication)getApplication()).getSettingDataSourceComponent())
                .applicationModule(new ApplicationModule(this))
                .editSettingPresenterModule(new EditSettingPresenterModule(fragment,getSupportLoaderManager()))
                .build().inject(this);
    }
}
