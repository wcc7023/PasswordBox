package com.wang.eggroll.passwordbox.patternlock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.utils.MD5Helper;
import com.wang.eggroll.passwordbox.view.MainActivity;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public class ConfirmPatternActivity extends me.zhanghai.android.patternlock.ConfirmPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);

        if (App.getSharedPreferences().getString("PASSWORD", "NULL") == "NULL"){
            Intent intent = new Intent(this, SetPatternActivity.class);
            //TODO:是否有返回值，有的话进行result处理
            startActivity(intent);
        }
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return App.getStealthModeSharedPreference().getBoolean("STEALTH_MODE", false);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        if (TextUtils.equals(App.getSharedPreferences().getString("PASSWORD", "NULL"),
                MD5Helper.patternToMD5String(pattern))){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else{
            return false;
        }
}

    @Override
    protected void onConfirmed() {
        super.onConfirmed();
    }

    @Override
    protected void onForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCancel() {
        super.onCancel();
    }
}
