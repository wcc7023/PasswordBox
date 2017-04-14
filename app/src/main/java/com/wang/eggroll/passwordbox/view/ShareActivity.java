package com.wang.eggroll.passwordbox.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.ShareListViewAdapter;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.ISharePresenter;
import com.wang.eggroll.passwordbox.presenter.SharePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by eggroll on 01/04/2017.
 */

public class ShareActivity extends AppCompatActivity implements IShareActivity, EasyPermissions.PermissionCallbacks{

    ListView shareList;
    static ShareListViewAdapter shareListViewAdapter = null;
    Bitmap bitmap = null;
    ISharePresenter sharePresenter = new SharePresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ISharePresenter sharePresenter = new SharePresenter(this);

        setContentView(R.layout.activity_share);

        shareList = (ListView) findViewById(R.id.list_view_share);
        shareListViewAdapter = new ShareListViewAdapter(PasswordItemList.getInstance());
        shareList.setAdapter(shareListViewAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_share);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:引入rxJava
                sharePresenter.onShareBtnClick();
            }
        });
    }

    @Override
    public ShareListViewAdapter getAdapter() {
        return shareListViewAdapter;
    }

    @Override
    public void onBarcodeCreated(Bitmap bitmap) {
        //TODO:使用fragment显示二维码
        this.bitmap = bitmap;
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.show(getSupportFragmentManager(), "share");
    }

    @Override
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Override
    @AfterPermissionGranted(1)
    public void getPermissionAndSave() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(App.getContext(), perms)){
            sharePresenter.saveImageToGallery(App.getContext(), bitmap);
        } else{
            EasyPermissions.requestPermissions(this, "需要储存权限来保存二维码", 1, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(App.getContext(), "permissionsDenied", Toast.LENGTH_SHORT).show();
        Log.e("permission", "in");
        //对永久拒绝的情况进行判断
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(App.getContext(), "相应权限已取得", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
