package com.wang.eggroll.passwordbox;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.uuzuche.lib_zxing.decoding.Intents;

/**
 * Created by eggroll on 29/03/2017.
 */

public class App extends Application {
    private static Context mContext;
    private static boolean isHide = true;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences stealthModeSharedPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sharedPreferences = getSharedPreferences("PASSWORD", MODE_PRIVATE);
        stealthModeSharedPreference = getSharedPreferences("STEALTH_MODE", MODE_PRIVATE);
        ZXingLibrary.initDisplayOpinion(this);
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

    public static SharedPreferences getStealthModeSharedPreference(){
        return stealthModeSharedPreference;
    }

    public static boolean isHide() {
        return isHide;
    }
}
