package com.wang.eggroll.passwordbox.view;

import com.wang.eggroll.passwordbox.adapter.ListViewAdapter;

/**
 * Created by eggroll on 29/03/2017.
 */

public interface IAddActivity {

    void onAddFailed(String resultMessage);
    void onAddSuccess();
    void onDataChangeed();
    void onRemovedSuccess();
    void onUpdateSuccess();
    void onUpdateFailed(String resultMessage);
    ListViewAdapter getAdapter();
}
