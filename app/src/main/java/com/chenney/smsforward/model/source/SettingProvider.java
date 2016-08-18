package com.chenney.smsforward.model.source;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SettingProvider  extends ContentProvider{


    private SettingDbHelper mSettingDbHelper;

    @Override
    public boolean onCreate() {
        mSettingDbHelper = new SettingDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = mSettingDbHelper.getReadableDatabase().query(
                PersistenceContract.SettingEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mSettingDbHelper.getWritableDatabase();

        Uri retUri;

        Cursor exists = db.query(
                PersistenceContract.SettingEntry.TABLE_NAME,
                new String[]{PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID},
                PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID + " = ?",
                new String[]{values.getAsString(PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID)},
                null,
                null,
                null
        );

        if(exists.moveToLast()){
            long _id = db.update(
                    PersistenceContract.SettingEntry.TABLE_NAME,
                    values,
                    PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID + " =?",
                    new String[]{values.getAsString(PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID)}
            );

            if(_id > 0){
                retUri = PersistenceContract.SettingEntry.buildTasksUriWith(_id);
            }else {
                throw new SQLException("Failed to insert row into " + uri);
            }
        }else{
            long _id = db.insert(
                    PersistenceContract.SettingEntry.TABLE_NAME,
                    null,
                    values
            );
            if(_id > 0){
                retUri = PersistenceContract.SettingEntry.buildTasksUriWith(_id);
            }else {
                throw new SQLException("Failed to insert row into " + uri);
            }
        }

        exists.close();

        getContext().getContentResolver().notifyChange(uri,null);

        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted = mSettingDbHelper.getWritableDatabase().delete(
                PersistenceContract.SettingEntry.TABLE_NAME,
                selection,selectionArgs
        );

        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = mSettingDbHelper.getWritableDatabase().update(
                PersistenceContract.SettingEntry.TABLE_NAME,values,selection,selectionArgs
        );

        if(rowsUpdated > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
