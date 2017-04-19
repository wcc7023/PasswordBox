package com.wang.eggroll.passwordbox.old;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.presenter.IAddPresenter;
import com.wang.eggroll.passwordbox.view.MainActivity;

/**
 * Created by eggroll on 25/03/2017.
 */

public class AddDialog extends DialogFragment{
//    AddPresenter addPresenter;
//
//    public AddDialog(AddPresenter addPresenter) {
//        this.addPresenter = addPresenter;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
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
                ((MainActivity)getActivity()).getAddPresenter().addToDatabase(name, password);
//                addPresenter.addToDatabase(name, password);
//                Toast.makeText(getContext(), name + password, Toast.LENGTH_SHORT).show();

                //TODO:传递两个参数，进入P层进行判断和数据添加操作
                //P层具有add方法用于添加数据，其中需要到M层实体用于具体数据操作
                //也需要V层(Activity)
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
}
