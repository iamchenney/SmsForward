package com.chenney.smsforward.model.source;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CallLog;

import com.chenney.smsforward.BuildConfig;

/**
 * Created by Administrator on 2016/8/15.
 */
public final class PersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    public PersistenceContract(){}

    public abstract static class SettingEntry implements BaseColumns {

        public static final String TABLE_NAME = "setting";
        public static final String COLUMN_NAME_ENTRY_ID = "settingid";
        public static final String COLUMN_NAME_SEND_TO_PHONE_NUM = "sendto";
        public static final String COLUMN_NAME_SEND_SMS = "sendsms";
        public static final String COLUMN_NAME_SEND_PHONE = "sendphone";
        public static final String COLUMN_NAME_SEND_BATTERY = "battery";
        public static final Uri CONTENT_SETTING_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static String[] SETTING_COLUMNS = new String[]{
                PersistenceContract.SettingEntry._ID,
                PersistenceContract.SettingEntry.COLUMN_NAME_ENTRY_ID,
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_TO_PHONE_NUM,
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_SMS,
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_PHONE,
                PersistenceContract.SettingEntry.COLUMN_NAME_SEND_BATTERY
        };

        public static Uri buildTasksUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_SETTING_URI, id);
        }

        public static Uri buildTasksUriWith(String id) {
            Uri uri = CONTENT_SETTING_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildTasksUri() {
            return CONTENT_SETTING_URI.buildUpon().build();
        }

    }

    public static class SmsEntry implements BaseColumns{

        public static final String CONTENT_INBOX = "inbox";
        public static final String CONTENT_SMS_AUTHORITY = "sms";
        public static final Uri CONTENT_SMS_URI = Uri.parse(CONTENT_SCHEME + CONTENT_SMS_AUTHORITY).buildUpon()
                .appendPath(CONTENT_INBOX).build();

        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_PERSON = "person";
        public static final String COLUMN_NAME_READ = "read";

        public static String[] SMS_COLUMNS = new String[]{
                PersistenceContract.SmsEntry._ID,
                PersistenceContract.SmsEntry.COLUMN_NAME_ADDRESS,
                PersistenceContract.SmsEntry.COLUMN_NAME_BODY,
                PersistenceContract.SmsEntry.COLUMN_NAME_DATE,
                PersistenceContract.SmsEntry.COLUMN_NAME_PERSON,
                PersistenceContract.SmsEntry.COLUMN_NAME_READ
        };

        public static Uri buildUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_SMS_URI, id);
        }

        public static Uri buildUriWith(String id) {
            Uri uri = CONTENT_SMS_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildUri() {
            return CONTENT_SMS_URI.buildUpon().build();
        }

    }

    public static class CallEntry extends CallLog.Calls{
        public static String[] CALL_COLUMNS = new String[]{
                PersistenceContract.CallEntry._ID,
                CallEntry.NUMBER,
                CallEntry.TYPE,
                CallEntry.DATE,
                CallEntry.IS_READ,
                CallEntry.NEW
        };
    }

}
