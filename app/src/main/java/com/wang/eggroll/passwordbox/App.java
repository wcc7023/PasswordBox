package com.wang.eggroll.passwordbox;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.uuzuche.lib_zxing.decoding.Intents;

import java.util.LinkedList;

/**
 * Created by eggroll on 29/03/2017.
 */

public class App extends Application {
    private static Context mContext;
    private static boolean isHide = true;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences stealthModeSharedPreference;
    private static LinkedList<Activity> activityLinkedList = new LinkedList<>();


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

    public static void addActivity(Activity activity){
        if (activityLinkedList != null && !activityLinkedList.contains(activity)){
            activityLinkedList.add(activity);
        }
    }

    public static void exit(){
        if (activityLinkedList != null && activityLinkedList.size() > 0){
            for (Activity activity : activityLinkedList){
                activity.finish();
            }
        }
    }
}
