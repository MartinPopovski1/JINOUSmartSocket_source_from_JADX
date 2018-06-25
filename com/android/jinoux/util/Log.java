package com.android.jinoux.util;

public class Log {
    private static final String TAG = "PowerSupplySocket";

    public static void m2i(String msg) {
        android.util.Log.i(TAG, msg);
    }

    public static void m0d(String msg) {
        android.util.Log.d(TAG, msg);
    }

    public static void m1e(String msg) {
        android.util.Log.e(TAG, msg);
    }
}
