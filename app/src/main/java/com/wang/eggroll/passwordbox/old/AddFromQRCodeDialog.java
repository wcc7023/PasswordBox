package com.wang.eggroll.passwordbox.old;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.AddFromQRCodeAdapter;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.instance.SelectedList;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.presenter.IAddPresenter;
import com.wang.eggroll.passwordbox.utils.AESHelper;
import com.wang.eggroll.passwordbox.utils.MD5Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eggroll on 15/04/2017.
 */

public class AddFromQRCodeDialog extends DialogFragment{

//    IAddPresenter addPresenter = new AddPresenter(getContext(), (MainActivity) getActivity());

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.dialog_add_from_qrcode, null);
//        builder.setView(view);
//
//        final String oldPassword = ((MainActivity)getActivity()).getOldPassword();
//
//        ListView listView = (ListView) view.findViewById(R.id.list_add_from_qrcode);
//        final AddFromQRCodeAdapter adapter = new AddFromQRCodeAdapter(((MainActivity)getActivity()).getSharedItemList());
//        listView.setAdapter(adapter);
//        builder.setTitle("请选择要导入的项目");
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                HashMap<Integer, Boolean> hashMap = adapter.getIsSelect();
//                List<PasswordItem> selectedList = new ArrayList<PasswordItem>();
//                for (int i = 0; i < hashMap.size(); i++){
//                    if (hashMap.get(i)){
//                        selectedList.add(((MainActivity)getActivity()).getSharedItemList().get(i));
//                    }
//                }
//
//                if (selectedList.size() == 0){
//                    Toast.makeText(getContext(), "你没有选择任何项目", Toast.LENGTH_SHORT).show();
//                }
//
//                for (int i = 0; i < selectedList.size(); i++) {
//                    PasswordItem passwordItem = selectedList.get(i);
//                    String name = passwordItem.getItem();
//                    String passwdAfterEncrypt = passwordItem.getPassword();
//                    Log.e("decryptPassword", oldPassword);
//                    String passwdBeforeEncrypt = AESHelper.decrypt(passwdAfterEncrypt, oldPassword);
//                    ((MainActivity)getActivity()).getAddPresenter().addToDatabase(name, passwdBeforeEncrypt);
//                }
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        return builder.create();
        return null;
    }
}
