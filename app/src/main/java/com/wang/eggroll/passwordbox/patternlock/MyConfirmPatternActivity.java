package com.wang.eggroll.passwordbox.patternlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.utils.MD5Helper;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 20/04/2017.
 */

public class MyConfirmPatternActivity extends MyBasePatternActivity implements PatternView.OnPatternListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText("请校验旧密码");
        patternView.setOnPatternListener(this);
        App.addActivity(this);
    }

    @Override
    public void onPatternStart() {
        removeClearPatternRunnable();
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
        patternView.setInStealthMode(isStealthModeEnabled());
    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<PatternView.Cell> pattern) {
    }

    @Override
    public void onPatternDetected(List<PatternView.Cell> pattern) {
        Log.e("onPatternDetected", "Detect");
        if (isPatternCorrect(pattern)){
            Log.e("onPatternDetected", "Correct");
            onConfirmed();
        }else{
            textView.setText("密码错误，请重试");
            patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
            postClearPatternRunnable();
        }
    }

    protected boolean isStealthModeEnabled() {
        return App.getStealthModeSharedPreference().getBoolean("STEALTH_MODE", false);
    }

    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return TextUtils.equals(MD5Helper.patternToMD5String(pattern), App.getSharedPreferences().getString("PASSWORD", "NULL"));
    }

    protected void onConfirmed() {
        setResult(RESULT_OK);
        finish();
    }
}
