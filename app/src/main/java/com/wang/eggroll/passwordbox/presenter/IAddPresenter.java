package com.wang.eggroll.passwordbox.presenter;

import com.wang.eggroll.passwordbox.model.PasswordItem;

import java.util.List;

/**
 * Created by eggroll on 28/03/2017.
 */

public interface IAddPresenter {
    void addToDatabase(String name, String pwdBeforeEncrypt);
    void updateItem(PasswordItem passwordItem);
    void removeItem(PasswordItem passwordItem);
    List<PasswordItem> queryAllItem();
    void onBackFromGallery(String result);
    void decodeQRCode(String result);
}
