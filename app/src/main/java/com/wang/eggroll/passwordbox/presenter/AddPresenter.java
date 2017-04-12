package com.wang.eggroll.passwordbox.presenter;

import android.content.Context;
import android.util.Log;

import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.MyOrmHelper;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.view.IAddActivity;
import com.wang.eggroll.passwordbox.view.MainActivity;

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
            passwordItem.setPassword(pwdBeforeEncrypt);
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
}
