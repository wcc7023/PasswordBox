package com.wang.eggroll.passwordbox.presenter;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public interface IPatternPresenter {
    void savePattern(List<PatternView.Cell> pattern);
}
