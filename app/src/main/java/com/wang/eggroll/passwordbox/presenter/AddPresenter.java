package com.wang.eggroll.passwordbox.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.instance.SelectedList;
import com.wang.eggroll.passwordbox.model.MyOrmHelper;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.utils.AESHelper;
import com.wang.eggroll.passwordbox.utils.ImageHelper;
import com.wang.eggroll.passwordbox.view.IAddActivity;
import com.wang.eggroll.passwordbox.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggroll on 28/03/2017.
 */

public class AddPresenter implements IAddPresenter {

    Context context;
    IAddActivity addActivity;


    public AddPresenter(Context context, IAddActivity addActivity) {
        this.context = context;
        this.addActivity = addActivity;
    }


    @Override
    public void addToDatabase(String name, String pwdBeforeEncrypt) {
        if (name.equals("") && pwdBeforeEncrypt.equals("")) {
            addActivity.onAddFailed("名称及密码不能为空");
        }
        else if (name.equals("")) {
            addActivity.onAddFailed("名称及密码不能为空");
        }
        else if (pwdBeforeEncrypt.equals("")) {
            addActivity.onAddFailed("密码不能为空");
        }
        else if(MyOrmHelper.getInstance(context).queryItem(name).size() != 0){
            addActivity.onAddFailed("有重名项，请重新命名");
        }else {
            PasswordItem passwordItem = new PasswordItem();
            passwordItem.setItem(name);
            Log.e("encryptPassword", App.getSharedPreferences().getString("PASSWORD", "NULL"));
            String pwdAfterEncrypt = AESHelper.encrypt(pwdBeforeEncrypt, App.getSharedPreferences().getString("PASSWORD", "NULL"));
            passwordItem.setPassword(pwdAfterEncrypt);
            MyOrmHelper.getInstance(context).addItem(passwordItem);
            addActivity.onDataChangeed();
            addActivity.onAddSuccess();
        }
    }

    @Override
    public void updateItem(PasswordItem passwordItem) {

        if (passwordItem.getItem().equals("") && passwordItem.getPassword().equals("")) {
            addActivity.onUpdateFailed("名称及密码不能为空");
        }
        else if (passwordItem.getItem().equals("")) {
            addActivity.onUpdateFailed("名称及密码不能为空");
        }
        else if (passwordItem.getPassword().equals("")) {
            addActivity.onUpdateFailed("密码不能为空");
        } else {
            String pwdBeforeEncrypt = passwordItem.getPassword();
            String pwdAfterEncrypt = AESHelper.encrypt(pwdBeforeEncrypt, App.getSharedPreferences().getString("PASSWORD", "NULL"));
            passwordItem.setPassword(pwdAfterEncrypt);
            MyOrmHelper.getInstance(context).updateItem(passwordItem);
            addActivity.onDataChangeed();
            addActivity.onUpdateSuccess();
        }
    }

    @Override
    public void removeItem(PasswordItem passwordItem) {
        MyOrmHelper.getInstance(context).removeItem(passwordItem);
        addActivity.onDataChangeed();
        addActivity.onRemovedSuccess();
    }

    @Override
    public List<PasswordItem> queryAllItem() {
        return MyOrmHelper.getInstance(context).queryAll();
    }

    @Override
    public void onBackFromGallery(String result) {

    }

    @Override
    public void decodeQRCode(String result) {
        SelectedList list = JSON.parseObject(result, SelectedList.class);
        List<PasswordItem> sharedItemList = list.getSelectedPasswordItemList();
        String oldPassword = list.getPassword();
        Log.e("oldPassword", oldPassword);
        addActivity.onListCreated(sharedItemList, oldPassword);
        Log.e("selectedList", sharedItemList.get(0).getItem());
    }


}
