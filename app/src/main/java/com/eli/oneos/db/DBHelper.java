package com.eli.oneos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eli.oneos.MyApplication;
import com.eli.oneos.db.greendao.DaoMaster;
import com.eli.oneos.db.greendao.DaoSession;

/**
 * Created by gaoyun@eli-tech.com on 2016/1/7.
 */
public class DBHelper {

    private static final String DB_NAME = "oneos_db";
    private static DaoMaster daoMaster = null;
    private static DaoSession daoSession = null;

    /**
     * Get Writable Database
     *
     * @return Writable Database
     */
    private static SQLiteDatabase getWritableDB() {
        Context context = MyApplication.getAppContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);

        return helper.getWritableDatabase();
    }

    /**
     * Get GreenDao Session
     *
     * @return Single {@link DaoSession}
     */
    public static DaoSession getDaoSession() {
        if (null == daoSession) {
            if (null == daoMaster) {
                daoMaster = new DaoMaster(getWritableDB());
            }

            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }
}
