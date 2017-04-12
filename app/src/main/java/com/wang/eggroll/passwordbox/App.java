package com.wang.eggroll.passwordbox;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eggroll on 29/03/2017.
 */

public class App extends Application {
    private static Context mContext;
    private static boolean isHide = true;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sharedPreferences = getSharedPreferences("PASSWORD", MODE_PRIVATE);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setHide(boolean hide) {
        isHide = hide;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static boolean isHide() {
        return isHide;
    }
}
