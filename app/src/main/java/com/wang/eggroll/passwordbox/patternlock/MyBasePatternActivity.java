package com.wang.eggroll.passwordbox.patternlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.utils.Statics;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 20/04/2017.
 */

public class MyBasePatternActivity extends AppCompatActivity{

    protected PatternView patternView;
    protected TextView textView;
    private Toolbar toolbar;

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            patternView.clearPattern();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_base_pattern_view);
        App.addActivity(this);

        patternView = (PatternView) findViewById(R.id.my_base_pattern_view);
        textView = (TextView) findViewById(R.id.my_base_pattern_text);
        toolbar = (Toolbar) findViewById(R.id.my_base_pattern_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
    }



    protected void removeClearPatternRunnable() {
        patternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        patternView.postDelayed(clearPatternRunnable, Statics.CLEAR_PATTERN_DELAY_MILLI);
    }

    protected void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
}

    @Override
    public void onBackPressed() {
        onCancel();
    }
}
