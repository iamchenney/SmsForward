package com.chenney.smsforward.presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ContactPresenter{
    public static String getContact(ContentResolver contentResolver,String id){

        String name = "";

        String selection = ContactsContract.Contacts._ID +  " = ?";
        String[] selectionArgs = new String[]{id};

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,
                selection,selectionArgs,null);

        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.d("contactName",name);
        }
        return name;
    }
}
