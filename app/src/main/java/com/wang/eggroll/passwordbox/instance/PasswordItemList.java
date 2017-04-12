package com.wang.eggroll.passwordbox.instance;

import com.wang.eggroll.passwordbox.model.PasswordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggroll on 29/03/2017.
 */

public  class PasswordItemList {

    private static volatile List<PasswordItem> passwordItemList;

    private PasswordItemList(){}

    public static List<PasswordItem> getInstance(){
        if(passwordItemList == null){
            synchronized (PasswordItemList.class){
                if(passwordItemList == null){
                    passwordItemList = new ArrayList<>();
                }
            }
        }
        return passwordItemList;
    }
}
