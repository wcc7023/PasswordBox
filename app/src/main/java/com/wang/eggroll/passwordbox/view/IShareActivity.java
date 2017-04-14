package com.wang.eggroll.passwordbox.view;

import android.graphics.Bitmap;

import com.wang.eggroll.passwordbox.adapter.ShareListViewAdapter;

/**
 * Created by eggroll on 01/04/2017.
 */

public interface IShareActivity {
    ShareListViewAdapter getAdapter();
    void onBarcodeCreated(Bitmap bitmap);
    Bitmap getBitmap();
    void getPermissionAndSave();
}
