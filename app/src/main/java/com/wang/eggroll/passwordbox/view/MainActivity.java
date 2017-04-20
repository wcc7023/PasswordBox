package com.wang.eggroll.passwordbox.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.adapter.ListViewAdapter;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.settings.SettingsActivity;
import com.wang.eggroll.passwordbox.utils.DialogHelper;
import com.wang.eggroll.passwordbox.utils.Statics;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements IAddActivity,SearchView.OnQueryTextListener,EasyPermissions.PermissionCallbacks{



    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    ListView listView;
    ListViewAdapter adapter;
    static AddPresenter addPresenter;
    SearchView searchView;

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
                    case R.id.btn_add:
                        DialogHelper.showAddDialog(getSupportFragmentManager(), addPresenter);
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
                        getCameraPermission();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_share:
                        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_set:
                        Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intentSettings);
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
                        Toast.makeText(App.getContext(), "DOWN", Toast.LENGTH_SHORT).show();
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
        adapter = new ListViewAdapter(PasswordItemList.getInstance());
        listView = (ListView) findViewById(R.id.pwd_list);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
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

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("搜索");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (adapter instanceof Filterable){
            Filter filter = adapter.getFilter();
            if (newText == null || newText.length() == 0){
                filter.filter(null);
            }else {
                filter.filter(newText);
            }
        }
        return true;
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

//    @Override
//    public ListViewAdapter getAdapter() {
//        return this.adapter;
//    }

    @Override
    public void onListCreated(List<PasswordItem> passwordItemList, String oldPassword) {
        DialogHelper.showAddFromQRCodeDialog(getSupportFragmentManager(), addPresenter, passwordItemList, oldPassword);
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
                       DialogHelper.showModifyDialog(getSupportFragmentManager(),
                                                        addPresenter, passwordItem);
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


    //以下权限相关
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void getCameraPermission(){
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.VIBRATE};

        if (EasyPermissions.hasPermissions(App.getContext(), perms)){
            Intent intent = new Intent(this, ScanActivity.class);
            startActivityForResult(intent, Statics.SCAN_REQUEST);
        }else {
            EasyPermissions.requestPermissions(this, "需要相机权限以扫描二维码", Statics.REQUEST_CAMERA_PERMS, perms);
        }
    }


    //返回值处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Statics.SCAN_REQUEST){
            if (resultCode == RESULT_OK){
                if (data.getIntExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED) == CodeUtils.RESULT_SUCCESS){
                    String result = data.getStringExtra(CodeUtils.RESULT_STRING);
                    addPresenter.decodeQRCode(result);
                }else {
                    Toast.makeText(App.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == Statics.IMAGE_ANALYZED_SUCCESS){
                String result = data.getStringExtra("result");
                addPresenter.decodeQRCode(result);
            }
            if (resultCode == Statics.IMAGE_ANALYZED_FAILED){
                Toast.makeText(App.getContext(), "解析失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
