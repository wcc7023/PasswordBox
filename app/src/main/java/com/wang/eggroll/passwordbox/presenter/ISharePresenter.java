package com.wang.eggroll.passwordbox.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.wang.eggroll.passwordbox.model.PasswordItem;

/**
 * Created by eggroll on 13/04/2017.
 */

public interface ISharePresenter {
    void onShareBtnClick();
    void saveImageToGallery(Context context, Bitmap bmp);
}
