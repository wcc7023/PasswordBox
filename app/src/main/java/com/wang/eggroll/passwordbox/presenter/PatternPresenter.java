package com.wang.eggroll.passwordbox.presenter;

import android.content.SharedPreferences;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.utils.MD5Helper;
import com.wang.eggroll.passwordbox.patternlock.ISetPatternActivity;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public class PatternPresenter implements IPatternPresenter {
    ISetPatternActivity setPatternActivity;

    public PatternPresenter(ISetPatternActivity setPatternActivity) {
        this.setPatternActivity = setPatternActivity;
    }

    @Override
    public void savePattern(List<PatternView.Cell> pattern) {
        String patternMD5 = MD5Helper.patternToMD5String(pattern);
        SharedPreferences.Editor editor = App.getSharedPreferences().edit();
        editor.putString("PASSWORD", patternMD5);
        editor.commit();
        setPatternActivity.savePatternSuccess();
    }
}
