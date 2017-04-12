package com.wang.eggroll.passwordbox.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.ListViewAdapter;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.MyOrmHelper;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IAddActivity{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    ListView listView;
    ListViewAdapter adapter;
    AddPresenter addPresenter;
    PasswordItem currentPasswordItem;
//    boolean isHide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPresenter = new AddPresenter(getApplicationContext(), this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_search:
                        Toast.makeText(getApplicationContext(), "搜索", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_add:
                        AddDialog addDialog = new AddDialog();
                        addDialog.show(getSupportFragmentManager(), "add");
                }
                return true ;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item_scan:
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_share:
                        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_set:
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_clode){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        App.setHide(false);
                        adapter.notifyDataSetChanged();
                        break;
                    case MotionEvent.ACTION_UP:
                        App.setHide(true);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });



        PasswordItemList.getInstance().addAll(addPresenter.queryAllItem());
        Log.d("itemFirstInDB", MyOrmHelper.getInstance(this).queryAll().get(0).getPassword());

//        passwordItemList = addPresenter.queryAllItem();
        adapter = new ListViewAdapter(PasswordItemList.getInstance());
        listView = (ListView) findViewById(R.id.pwd_list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createItemSetDialog(PasswordItemList.getInstance().get(position));
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        Log.d("Main", "onPause");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onAddFailed(String resultMessage) {
        Toast.makeText(getApplicationContext(), "插入失败，" + resultMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess() {
        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataChangeed() {
        PasswordItemList.getInstance().clear();
        PasswordItemList.getInstance().addAll(addPresenter.queryAllItem());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRemovedSuccess() {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateFailed(String resultMessage) {
        Toast.makeText(this, "更改失败，" + resultMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public ListViewAdapter getAdapter() {
        return this.adapter;
    }

    public AddPresenter getAddPresenter(){
        return addPresenter;
    }

    public PasswordItem getPasswordItem() {
        return currentPasswordItem;
    }

    private void createItemSetDialog(final PasswordItem passwordItem){
        final String[] items = new String[] {"更改", "删除"};
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
       builder.setItems(items, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               switch (which){
                   case 0:
                       currentPasswordItem = passwordItem;
                       UpdateDialog updateDialog = new UpdateDialog();
                       updateDialog.show(getSupportFragmentManager(), "update");
                       break;
                   case 1:
                       addPresenter.removeItem(passwordItem);
                       break;
               }
           }
       });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        PasswordItemList.getInstance().clear();
        super.onDestroy();
    }
}
