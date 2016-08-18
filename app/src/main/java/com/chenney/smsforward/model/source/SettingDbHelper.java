package com.chenney.smsforward.model.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SettingDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SmsForward.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersistenceContract.SettingEntry.TABLE_NAME +" ( " +
                    PersistenceContract.SettingEntry._ID +INTEGER_TYPE + " PRIMARY KEY," +
                    PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SettingEntry.COLUMN_NAME_SEND_TO_PHONE_NUM + TEXT_TYPE + COMMA_SEP +
                    PersistenceContract.SettingEntry.COLUMN_NAME_SEND_SMS + BOOLEAN_TYPE + COMMA_SEP +
                    PersistenceContract.SettingEntry.COLUMN_NAME_SEND_PHONE + BOOLEAN_TYPE + COMMA_SEP +
                    PersistenceContract.SettingEntry.COLUMN_NAME_SEND_BATTERY + BOOLEAN_TYPE +
            " )";

    private static final String SQL_DROP_ENTRIES =
            "DROP TABLE " + PersistenceContract.SettingEntry.TABLE_NAME;

    public SettingDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_ENTRIES);
        onCreate(db);
    }
}
