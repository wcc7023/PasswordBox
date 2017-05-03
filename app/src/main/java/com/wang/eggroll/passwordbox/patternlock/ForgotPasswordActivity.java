package com.wang.eggroll.passwordbox.patternlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wang.eggroll.passwordbox.App;

/**
 * Created by eggroll on 12/04/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
    }
}
