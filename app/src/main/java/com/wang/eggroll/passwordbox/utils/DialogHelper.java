package com.wang.eggroll.passwordbox.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.AddFromQRCodeAdapter;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.view.MainActivity;
import com.wang.eggroll.passwordbox.view.ShareActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eggroll on 18/04/2017.
 */

public class DialogHelper {

    static final String ADD_DIALOG = "add_dialog";
    static final String MODIFY_DIALOG = "modify_dialog";
    static final String SHARE_DIALOG = "share_dialog";
    static final String ADD_FROM_QR_DIALOG = "add_from_qrcode_dialog";


    public static void showAddDialog(android.support.v4.app.FragmentManager fragmentManager, final AddPresenter addPresenter){

        CustomDialog customDialog = CustomDialog.newInstance(new CustomDialog.MyDialog() {
            @Override
            public Dialog getDialog(Context context, LayoutInflater layoutInflater) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = layoutInflater.inflate(R.layout.dialog_add, null);
                builder.setView(view);

                final EditText itemEditText = (EditText) view.findViewById(R.id.item_edit_text);
                final EditText passwdEditText = (EditText) view.findViewById(R.id.passwd_edit_text);

                builder.setTitle("新建记录");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = itemEditText.getText().toString();
                        String password = passwdEditText.getText().toString();
                        addPresenter.addToDatabase(name, password);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                    }
                });
                return builder.create();
            }
        });
        customDialog.show(fragmentManager, ADD_DIALOG);
    }

    public static void showModifyDialog(android.support.v4.app.FragmentManager fragmentManager,
                                        final AddPresenter addPresenter, final PasswordItem passwordItem){
        CustomDialog customDialog = CustomDialog.newInstance(new CustomDialog.MyDialog() {
            @Override
            public Dialog getDialog(Context context, LayoutInflater layoutInflater) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = layoutInflater.inflate(R.layout.dialog_update, null);
                builder.setView(view);

                final EditText itemEditText = (EditText) view.findViewById(R.id.item_update_edit_text);
                final EditText pwdEditText = (EditText) view.findViewById(R.id.passwd_update_edit_text);

                itemEditText.setText(passwordItem.getItem());

                String passwordAfterEncrypt = passwordItem.getPassword();
                String passwordBeforeEncrypt = AESHelper.decrypt(passwordAfterEncrypt, App.getSharedPreferences().getString("PASSWORD", "NULL"));

                pwdEditText.setText(passwordBeforeEncrypt);

                builder.setTitle("更改");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PasswordItem newPasswordItem = new PasswordItem();
                        newPasswordItem.setId(passwordItem.getId());
                        newPasswordItem.setItem(itemEditText.getText().toString());
                        newPasswordItem.setPassword(pwdEditText.getText().toString());
                        addPresenter.updateItem(newPasswordItem);
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                return builder.create();
            }
        });
        customDialog.show(fragmentManager, MODIFY_DIALOG);
    }

    public static void showQRCodeDialog(android.support.v4.app.FragmentManager fragmentManager,
                                        final Bitmap bitmap, final ShareActivity shareActivity){
        CustomDialog customDialog = CustomDialog.newInstance(new CustomDialog.MyDialog() {
            @Override
            public Dialog getDialog(Context context, LayoutInflater layoutInflater) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = layoutInflater.inflate(R.layout.dialog_share, null);
                builder.setView(view);
                builder.setTitle("请扫描二维码");

                ImageView imageView = (ImageView) view.findViewById(R.id.image_barcode);
                imageView.setImageBitmap(bitmap);

                builder.setPositiveButton("保存到手机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareActivity.getPermissionAndSave(bitmap);
                    }
                });

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                return  builder.create();
            }
        });
        customDialog.show(fragmentManager, SHARE_DIALOG);
    }

    public static void showAddFromQRCodeDialog(android.support.v4.app.FragmentManager fragmentManager,
                                               final AddPresenter addPresenter, final List<PasswordItem> listFromeQRCode,
                                               final String oldPassword){

        CustomDialog customDialog = CustomDialog.newInstance(new CustomDialog.MyDialog() {
            @Override
            public Dialog getDialog(final Context context, LayoutInflater layoutInflater) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = layoutInflater.inflate(R.layout.dialog_add_from_qrcode, null);
                builder.setView(view);

//                final String oldPassword = ((MainActivity)getActivity()).getOldPassword();

                ListView listView = (ListView) view.findViewById(R.id.list_add_from_qrcode);
                final AddFromQRCodeAdapter adapter = new AddFromQRCodeAdapter(listFromeQRCode);
                listView.setAdapter(adapter);
                builder.setTitle("请选择要导入的项目");

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<Integer, Boolean> hashMap = adapter.getIsSelect();
                        List<PasswordItem> selectedList = new ArrayList<PasswordItem>();
                        for (int i = 0; i < hashMap.size(); i++){
                            if (hashMap.get(i)){
                                selectedList.add(listFromeQRCode.get(i));
                            }
                        }

                        if (selectedList.size() == 0){
                            Toast.makeText(context, "你没有选择任何项目", Toast.LENGTH_SHORT).show();
                        }

                        for (int i = 0; i < selectedList.size(); i++) {
                            PasswordItem passwordItem = selectedList.get(i);
                            String name = passwordItem.getItem();
                            String passwdAfterEncrypt = passwordItem.getPassword();
                            Log.e("decryptPassword", oldPassword);
                            String passwdBeforeEncrypt = AESHelper.decrypt(passwdAfterEncrypt, oldPassword);
                            addPresenter.addToDatabase(name, passwdBeforeEncrypt);
                        }
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                return builder.create();
            }
        });
        customDialog.show(fragmentManager,ADD_FROM_QR_DIALOG);
    }
}
