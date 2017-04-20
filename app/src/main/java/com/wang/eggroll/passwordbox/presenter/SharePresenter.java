package com.wang.eggroll.passwordbox.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.instance.SelectedList;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.view.IShareActivity;
import com.wang.eggroll.passwordbox.view.ShareActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.path;

/**
 * Created by eggroll on 13/04/2017.
 */

public class SharePresenter implements ISharePresenter {

    IShareActivity shareActivity;

    public SharePresenter(IShareActivity shareActivity) {
        this.shareActivity = shareActivity;
    }


    @Override
    public void onShareBtnClick() {
        List<PasswordItem> selectedList = new ArrayList<>();
        HashMap<Integer, Boolean> hashMap = shareActivity.getAdapter().getIsSelect();
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i)){
                PasswordItem temp = PasswordItemList.getInstance().get(i);
                PasswordItem selectItem = new PasswordItem();
                selectItem.setItem(temp.getItem());
                selectItem.setPassword(temp.getPassword());
                selectedList.add(selectItem);
            }
        }
        SelectedList list = new SelectedList();
        list.setSelectedPasswordItemList(selectedList);
        list.setPassword(App.getSharedPreferences().getString("PASSWORD", "NULL"));
        String JSONString = JSON.toJSONString(list);




        Log.e("onCreateQRCOde", list.getPassword());
        SelectedList selectedList1 = JSON.parseObject(JSONString, SelectedList.class);
        String password = selectedList1.getPassword();
        Log.e("onCreateQRCOde", password);





        Bitmap barCode = CodeUtils.createImage(JSONString, 400, 400, null);
        shareActivity.onBarcodeCreated(barCode);
    }

    @Override
    public void saveImageToGallery(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "PasswordBox");
        if (!appDir.exists()){
            appDir.mkdir();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HHmmss");
        Date currentData = new Date(System.currentTimeMillis());

        String fileName = simpleDateFormat.format(currentData) + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(App.getContext(), "成功保存图片到：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MediaStore.Images.Media.insertImage(App.getContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        App.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

    }
}
