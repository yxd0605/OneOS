package com.eli.oneos.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DEVICE_INFO.
 */
public class DeviceInfoDao extends AbstractDao<DeviceInfo, String> {

    public static final String TABLENAME = "DEVICE_INFO";

    /**
     * Properties of entity DeviceInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Ip = new Property(0, String.class, "ip", true, "IP");
        public final static Property Mac = new Property(1, String.class, "mac", false, "MAC");
        public final static Property Time = new Property(2, Long.class, "time", false, "TIME");
        public final static Property Port = new Property(3, String.class, "port", false, "PORT");
        public final static Property Model = new Property(4, String.class, "model", false, "MODEL");
        public final static Property Version = new Property(5, String.class, "version", false, "VERSION");
        public final static Property IsLAN = new Property(6, Boolean.class, "isLAN", false, "IS_LAN");
    }

    ;


    public DeviceInfoDao(DaoConfig config) {
        super(config);
    }

    public DeviceInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'DEVICE_INFO' (" + //
                "'IP' TEXT PRIMARY KEY NOT NULL ," + // 0: ip
                "'MAC' TEXT NOT NULL ," + // 1: mac
                "'TIME' INTEGER," + // 2: time
                "'PORT' TEXT NOT NULL ," + // 3: port
                "'MODEL' TEXT NOT NULL ," + // 4: model
                "'VERSION' TEXT NOT NULL ," + // 5: version
                "'IS_LAN' INTEGER);"); // 6: isLAN
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DEVICE_INFO'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, DeviceInfo entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getIp());
        stmt.bindString(2, entity.getMac());

        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
        stmt.bindString(4, entity.getPort());
        stmt.bindString(5, entity.getModel());
        stmt.bindString(6, entity.getVersion());

        Boolean isLAN = entity.getIsLAN();
        if (isLAN != null) {
            stmt.bindLong(7, isLAN ? 1l : 0l);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DeviceInfo readEntity(Cursor cursor, int offset) {
        DeviceInfo entity = new DeviceInfo( //
                cursor.getString(offset + 0), // ip
                cursor.getString(offset + 1), // mac
                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // time
                cursor.getString(offset + 3), // port
                cursor.getString(offset + 4), // model
                cursor.getString(offset + 5), // version
                cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0 // isLAN
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, DeviceInfo entity, int offset) {
        entity.setIp(cursor.getString(offset + 0));
        entity.setMac(cursor.getString(offset + 1));
        entity.setTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPort(cursor.getString(offset + 3));
        entity.setModel(cursor.getString(offset + 4));
        entity.setVersion(cursor.getString(offset + 5));
        entity.setIsLAN(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected String updateKeyAfterInsert(DeviceInfo entity, long rowId) {
        return entity.getIp();
    }

    /**
     * @inheritdoc
     */
    @Override
    public String getKey(DeviceInfo entity) {
        if (entity != null) {
            return entity.getIp();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
