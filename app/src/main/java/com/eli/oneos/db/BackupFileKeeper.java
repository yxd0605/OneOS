package com.eli.oneos.db;

import com.eli.oneos.db.greendao.BackupFile;
import com.eli.oneos.db.greendao.BackupFileDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by gaoyun@eli-tech.com on 2016/1/7.
 */
public class BackupFileKeeper {

    /**
     * List Backup Info by mac and username
     *
     * @param uid user ID
     * @return
     */
    public static List<BackupFile> all(long uid) {
        BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where(BackupFileDao.Properties.Uid.eq(uid));

        return queryBuilder.list();
    }

    /**
     * Get Backup Info from database by user, mac and path
     *
     * @param uid  user ID
     * @param path backup path
     * @return {@link BackupFile} or {@code null}
     */
    public static BackupFile getBackupInfo(long uid, String path) {
        BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where(BackupFileDao.Properties.Uid.eq(uid));
        queryBuilder.where(BackupFileDao.Properties.Path.eq(path));

        return (BackupFile) queryBuilder.unique();
    }

    /**
     * Insert a user into Database if it does not exist or replace it.
     *
     * @param info
     * @return insertOrReplace result
     */
    public static boolean insertOrReplace(BackupFile info) {
        if (info != null) {
            BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
            return dao.insertOrReplace(info) > 0;
        }

        return false;
    }

    /**
     * Reset Backup by mac and username
     *
     * @param uid user ID
     * @return
     */
    public static boolean reset(long uid) {
        BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where(BackupFileDao.Properties.Uid.eq(uid));

        List<BackupFile> list = queryBuilder.list();
        if (null != list) {
            for (BackupFile info : list) {
                info.setTime(0L);
                update(info);
            }
        }

        return true;
    }

    /**
     * Delete a user from Database
     *
     * @param info
     * @return unActive result
     */
    public static boolean delete(BackupFile info) {
        if (info != null) {
            BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
            dao.delete(info);

            return true;
        }

        return false;
    }

    /**
     * Update user information
     *
     * @param user
     * @return
     */
    public static boolean update(BackupFile user) {
        if (null == user) {
            return false;
        }

        BackupFileDao dao = DBHelper.getDaoSession().getBackupFileDao();
        dao.update(user);
        return true;
    }
}