package com.wang.eggroll.passwordbox.patternlock;

import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.presenter.IPatternPresenter;
import com.wang.eggroll.passwordbox.presenter.PatternPresenter;
import com.wang.eggroll.passwordbox.view.ISetPatternActivity;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public class SetPatternActivity extends me.zhanghai.android.patternlock.SetPatternActivity implements ISetPatternActivity {
    IPatternPresenter patternPresenter = new PatternPresenter();

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);

        patternPresenter.savePattern(pattern);
    }

    @Override
    public void savePatternSuccess() {
        Toast.makeText(App.getContext(), "保存密码成功", Toast.LENGTH_SHORT).show();
    }
}
