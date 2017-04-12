package com.wang.eggroll.passwordbox.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wang.eggroll.passwordbox.model.orm.DaoMaster;
import com.wang.eggroll.passwordbox.model.orm.DaoSession;
import com.wang.eggroll.passwordbox.model.orm.PasswordItemDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggroll on 25/03/2017.
 */

public class MyOrmHelper {
    private String DB_NAME = "db_password.db";

    private static MyOrmHelper instance;

    private static DaoMaster daoMaster = null;
    private static DaoSession daoSession = null;

    public MyOrmHelper(Context context){
        SQLOpenHelper openHelper = new SQLOpenHelper(context, DB_NAME);
        daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static MyOrmHelper getInstance(Context context){
        if(instance==null){
            instance = new MyOrmHelper(context);
        }
        return instance;
    }


    public void addItem(PasswordItem passwordItem){
        PasswordItemDao passwordItemDao = daoSession.getPasswordItemDao();
        passwordItemDao.insert(passwordItem);
    }

    public List<PasswordItem> queryItem(String name){
        PasswordItemDao passwordItemDao = daoSession.getPasswordItemDao();
        QueryBuilder<PasswordItem> builder = passwordItemDao.queryBuilder();
        Query<PasswordItem> query = builder.where(PasswordItemDao.Properties.Item.eq(name)).build();
        return query.list();
    }

    public List<PasswordItem> queryAll(){
        PasswordItemDao passwordItemDao = daoSession.getPasswordItemDao();
        return passwordItemDao.loadAll();
    }


    public void removeItem (PasswordItem passwordItem){
        PasswordItemDao passwordItemDao = daoSession.getPasswordItemDao();
        passwordItemDao.delete(passwordItem);
    }

    public void updateItem(PasswordItem passwordItem){
        PasswordItemDao passwordItemDao = daoSession.getPasswordItemDao();
        passwordItemDao.update(passwordItem);
    }
}
