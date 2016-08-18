package com.chenney.smsforward.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/18.
 */
public class StringUtil {

    public static boolean isPhone(String phone){
        if(phone == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^(1[3|4|5|7|8][0-9]{1}){1}\\d{8}$");

        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }
}
