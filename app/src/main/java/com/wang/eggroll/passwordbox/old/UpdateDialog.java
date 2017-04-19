package com.wang.eggroll.passwordbox.old;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.view.MainActivity;

/**
 * Created by eggroll on 30/03/2017.
 */

public class UpdateDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);

        //TODO:passwordItem通过传值的形式进来
        final PasswordItem currentPasswordItem = ((MainActivity)getActivity()).getPasswordItem();
        final AddPresenter presenter = ((MainActivity)getActivity()).getAddPresenter();

        final EditText itemEditText = (EditText) view.findViewById(R.id.item_update_edit_text);
        final EditText pwdEditText = (EditText) view.findViewById(R.id.passwd_update_edit_text);

        itemEditText.setText(currentPasswordItem.getItem());
        pwdEditText.setText(currentPasswordItem.getPassword());

        builder.setTitle("更改");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PasswordItem newPasswordItem = new PasswordItem();
                newPasswordItem.setId(currentPasswordItem.getId());
                newPasswordItem.setItem(itemEditText.getText().toString());
                newPasswordItem.setPassword(pwdEditText.getText().toString());
                presenter.updateItem(newPasswordItem);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
