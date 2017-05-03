package com.wang.eggroll.passwordbox.patternlock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.utils.MD5Helper;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 14/04/2017.
 */

public class ConfirmForPwdResetActivity extends ConfirmPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return App.getStealthModeSharedPreference().getBoolean("STEALTH_MODE", false);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        if (TextUtils.equals(App.getSharedPreferences().getString("PASSWORD", "NULL"),
                MD5Helper.patternToMD5String(pattern))){
            return true;
        }else{
            return false;
        }
    }
}
