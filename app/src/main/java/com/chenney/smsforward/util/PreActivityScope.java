package com.chenney.smsforward.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Administrator on 2016/8/16.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PreActivityScope {
}
