package com.chenney.smsforward.model;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.chenney.smsforward.model.source.PersistenceContract;
import com.google.common.base.Preconditions;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/17.
 */
public class CallDataSource {

    private ContentResolver mContentResolver;
    private Context mContext;

    @Inject
    public CallDataSource(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        mContext = context;
        mContentResolver = context.getContentResolver();
    }


    public void readCall(String id) {
        Preconditions.checkNotNull(id);

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.CallEntry.IS_READ, Integer.valueOf(1));
        values.put(PersistenceContract.CallEntry.NEW, Integer.valueOf(0));

        String selection = PersistenceContract.CallEntry._ID + " LIKE ?";
        String[] selectionArgs = {id};

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mContentResolver.update(PersistenceContract.CallEntry.CONTENT_URI, values, selection, selectionArgs);
    }


}
