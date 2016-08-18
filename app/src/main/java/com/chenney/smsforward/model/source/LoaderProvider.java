package com.chenney.smsforward.model.source;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.google.common.base.Preconditions;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/15.
 */
public class LoaderProvider {

    @NonNull
    private final Context mContext;

    @Inject
    public LoaderProvider(@NonNull Context context){
        mContext = Preconditions.checkNotNull(context,"context cannot be null");
    }

    public Loader<Cursor> createSettingLoader(){
        return new CursorLoader(
                mContext,
                PersistenceContract.SettingEntry.buildTasksUri(),
                PersistenceContract.SettingEntry.SETTING_COLUMNS, null, null, null
        );
    }

    public Loader<Cursor> createSmsLoader(){
        String selection = PersistenceContract.SmsEntry.COLUMN_NAME_READ + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(0)};
        return new CursorLoader(
                mContext,
                PersistenceContract.SmsEntry.buildUri(),
                PersistenceContract.SmsEntry.SMS_COLUMNS,selection,selectionArgs,null
        );
    }

    public Loader<Cursor> createCallLoader(){
        String selection = PersistenceContract.CallEntry.TYPE + " = ?"
                + " and (" + PersistenceContract.CallEntry.IS_READ + " is null or "+ PersistenceContract.CallEntry.IS_READ +" = ?)";
        String[] selectionArgs = new String[]{String.valueOf(PersistenceContract.CallEntry.MISSED_TYPE),String.valueOf(0)};
        final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
//        selection = null;
//        selectionArgs = null;
        return new CursorLoader(
                mContext,
                PersistenceContract.CallEntry.CONTENT_URI,
                null,
                selection,selectionArgs,sortOrder
        );
    }

    public Loader<Cursor> createContactLoader(){
        return new CursorLoader(
                mContext,
                ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                null,null,null,null
        );
    }

}
