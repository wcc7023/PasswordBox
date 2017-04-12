package com.wang.eggroll.passwordbox.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.ShareListViewAdapter;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.PasswordItem;

/**
 * Created by eggroll on 01/04/2017.
 */

public class ShareActivity extends AppCompatActivity implements IShareActivity{

    ListView shareList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        shareList = (ListView) findViewById(R.id.list_view_share);
        ShareListViewAdapter shareListViewAdapter = new ShareListViewAdapter(PasswordItemList.getInstance());
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
    }
}
