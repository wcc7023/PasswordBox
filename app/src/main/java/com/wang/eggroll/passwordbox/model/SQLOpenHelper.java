package com.wang.eggroll.passwordbox.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wang.eggroll.passwordbox.model.orm.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by eggroll on 25/03/2017.
 */

public class SQLOpenHelper extends DaoMaster.OpenHelper {
    public SQLOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
