package com.wang.eggroll.passwordbox.old;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.presenter.ISharePresenter;
import com.wang.eggroll.passwordbox.presenter.SharePresenter;
import com.wang.eggroll.passwordbox.view.ShareActivity;

import java.lang.reflect.Field;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by eggroll on 13/04/2017.
 */

public class ShareDialog extends DialogFragment{

    ISharePresenter sharePresenter = new SharePresenter((ShareActivity) getActivity());

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_share, null);
//        builder.setView(view);
//        builder.setTitle("请扫描二维码");
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.image_barcode);
//        imageView.setImageBitmap(((ShareActivity)getActivity()).getBitmap());
//
//        builder.setPositiveButton("保存到手机", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ((ShareActivity) getActivity()).getPermissionAndSave();
//            }
//        });
//
//        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//
//        return  builder.create();
        return null;
    }
}
