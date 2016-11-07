package com.tangce.fastcode.utils;

import android.util.Log;

/**
 * Created by Tanck on 11/3/2016.
 * <p/>
 * Describe:
 */
public class LogUtils {
    private boolean DEBUG = true;

    private static final String TAG = "Tanck";

    public static void d(String msg) {
        Log.d(TAG, msg);
    }
}
