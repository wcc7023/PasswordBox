package com.wang.eggroll.passwordbox.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by eggroll on 18/04/2017.
 */

public class CustomDialog extends android.support.v4.app.DialogFragment {

    //回调获取显示的Dialog
    MyDialog mMyDialog;

    public interface MyDialog{
        //把创建Dialog的流程放到MyDialog.getDialog下
        Dialog getDialog(Context context, LayoutInflater layoutInflater);
    }

    //外部方法在需要显示Dialog时，只需调用newInstance并实现MyDialog接口，在该接口下创建Dialog即可
    public static CustomDialog newInstance(MyDialog myDialog){
        CustomDialog customDialog = new CustomDialog();
        customDialog.mMyDialog = myDialog;
        return customDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (null == mMyDialog){
            super.onCreate(savedInstanceState);
        }

        return mMyDialog.getDialog(getActivity(), getActivity().getLayoutInflater());
    }
}
