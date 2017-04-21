package com.wang.eggroll.passwordbox.presenter;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.MyOrmHelper;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.utils.AESHelper;
import com.wang.eggroll.passwordbox.utils.MD5Helper;
import com.wang.eggroll.passwordbox.patternlock.ISetPatternActivity;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by eggroll on 12/04/2017.
 */

public class PatternPresenter implements IPatternPresenter {
    ISetPatternActivity setPatternActivity;
    String oldPassword = App.getSharedPreferences().getString("PASSWORD", "NULL");

    public PatternPresenter(ISetPatternActivity setPatternActivity) {
        this.setPatternActivity = setPatternActivity;
    }

    @Override
    public void savePattern(List<PatternView.Cell> pattern) {
        String newPassword = MD5Helper.patternToMD5String(pattern);

        if (PasswordItemList.getInstance().size() != 0){
            resetPassword(newPassword);
        }else{
            firstTimeSetPassword(newPassword);
        }
    }

    private void firstTimeSetPassword(String newPassword){
        SharedPreferences.Editor editor = App.getSharedPreferences().edit();
        editor.putString("PASSWORD", newPassword);
        editor.commit();
        setPatternActivity.savePatternSuccess();
    }

    private void resetPassword(String newPassword){
        for (int i = 0; i < PasswordItemList.getInstance().size(); i++){
            long id = PasswordItemList.getInstance().get(i).getId();
            String name = PasswordItemList.getInstance().get(i).getItem();
            String pwdAfterEncrypt = PasswordItemList.getInstance().get(i).getPassword();
            String pwdBeforeEcrypt = AESHelper.decrypt(pwdAfterEncrypt, oldPassword);
            String pwdAtferEncrypyAgain = AESHelper.encrypt(pwdBeforeEcrypt, newPassword);
            PasswordItem passwordItem = new PasswordItem();
            passwordItem.setItem(name);
            passwordItem.setPassword(pwdAtferEncrypyAgain);
            passwordItem.setId(id);
            MyOrmHelper.getInstance(App.getContext()).updateItem(passwordItem);
        }
        SharedPreferences.Editor editor = App.getSharedPreferences().edit();
        editor.putString("PASSWORD", newPassword);
        editor.commit();
        setPatternActivity.resetPatternSuccess();
    }
}
