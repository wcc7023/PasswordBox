package com.wang.eggroll.passwordbox.instance;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.model.PasswordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggroll on 15/04/2017.
 */

public class SelectedList {
    private List<PasswordItem> selectedPasswordItemList = new ArrayList<>();
//    private String password = App.getSharedPreferences().getString("PASSWORD", "NULL");
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PasswordItem> getSelectedPasswordItemList() {
        return selectedPasswordItemList;
    }

    public void setSelectedPasswordItemList(List<PasswordItem> selectedPasswordItemList) {
        this.selectedPasswordItemList = selectedPasswordItemList;
    }

    public String getPassword() {
        return password;
    }
}
