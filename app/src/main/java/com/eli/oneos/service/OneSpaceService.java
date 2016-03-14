package com.eli.oneos.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.eli.oneos.MyApplication;
import com.eli.oneos.db.BackupFileKeeper;
import com.eli.oneos.db.greendao.BackupFile;
import com.eli.oneos.model.oneos.OneOSFile;
import com.eli.oneos.model.oneos.backup.file.BackupAlbumManager;
import com.eli.oneos.model.oneos.backup.file.BackupFileManager;
import com.eli.oneos.model.oneos.transfer.DownloadElement;
import com.eli.oneos.model.oneos.transfer.DownloadManager;
import com.eli.oneos.model.oneos.transfer.UploadElement;
import com.eli.oneos.model.oneos.transfer.UploadManager;
import com.eli.oneos.model.oneos.user.LoginManage;
import com.eli.oneos.model.oneos.user.LoginSession;

import java.io.File;
import java.util.ArrayList;

public class OneSpaceService extends Service {
    private static final String TAG = OneSpaceService.class.getSimpleName();

    private Context context;
    private ServiceBinder mBinder;
    private DownloadManager mDownloadManager;
    private UploadManager mUploadManager;
    private BackupAlbumManager mBackupAlbumManager;
    private BackupFileManager mBackupFileManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = MyApplication.getAppContext();

        mDownloadManager = DownloadManager.getInstance();
        mUploadManager = UploadManager.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_SERVICE, "OneSpaceService destroy.");
        mDownloadManager.destroy();
        mUploadManager.destroy();
        stopBackupAlbum();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) {
            mBinder = new ServiceBinder();
        }
        return mBinder;
    }

    public class ServiceBinder extends Binder {
        public OneSpaceService getService() {
            return OneSpaceService.this;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    }

    public void notifyUserLogin() {
        startBackupAlbum();
        startBackupFile();
    }

    public void notifyUserLogout() {
        cancelDownload();
        cancelUpload();
        stopBackupAlbum();
        stopBackupFile();
    }

    // ==========================================Auto Backup File==========================================
    public void startBackupFile() {
        LoginSession loginSession = LoginManage.getInstance().getLoginSession();
        if (!loginSession.getUserSettings().getIsAutoBackupFile()) {
            Log.e(TAG, "Do not open auto backup file");
            return;
        }

        if (mBackupFileManager == null) {
            mBackupFileManager = new BackupFileManager(loginSession, context);
            mBackupFileManager.startBackup();
            Log.d(TAG, "======Start BackupFile=======");
        }
    }

    public void addOnBackupFileListener(BackupFileManager.OnBackupFileListener listener) {
        if (null != mBackupFileManager) {
            mBackupFileManager.setOnBackupFileListener(listener);
        }
    }

    public boolean deleteBackupFile(BackupFile file) {
        if (null != mBackupFileManager) {
            return mBackupFileManager.deleteBackupFile(file);
        }

        return false;
    }

    public boolean stopBackupFile(BackupFile file) {
        if (null != mBackupFileManager) {
            return mBackupFileManager.stopBackupFile(file);
        }

        return false;
    }

    public boolean addBackupFile(BackupFile file) {
        if (null != mBackupFileManager) {
            return mBackupFileManager.addBackupFile(file);
        }

        return false;
    }

    public boolean isBackupFile() {
        if (null != mBackupFileManager) {
            return mBackupFileManager.isBackup();
        }

        return false;
    }

    public void stopBackupFile() {
        if (mBackupFileManager != null) {
            mBackupFileManager.stopBackup();
            mBackupFileManager = null;
        }
    }
    // ==========================================Auto Backup File==========================================


    // ==========================================Auto Backup Album==========================================
    public void startBackupAlbum() {
        LoginSession loginSession = LoginManage.getInstance().getLoginSession();
        if (!loginSession.getUserSettings().getIsAutoBackupAlbum()) {
            Log.e(TAG, "Do not open auto backup photo");
            return;
        }
        if (mBackupAlbumManager != null) {
            mBackupAlbumManager.stopBackup();
        }

        mBackupAlbumManager = new BackupAlbumManager(loginSession, context);
        mBackupAlbumManager.startBackup();
        Log.d(TAG, "======Start BackupAlbum=======");
    }

    public void stopBackupAlbum() {
        if (mBackupAlbumManager != null) {
            mBackupAlbumManager.stopBackup();
            mBackupAlbumManager = null;
        }
    }

    public void resetBackupAlbum() {
        stopBackupAlbum();
        LoginSession loginSession = LoginManage.getInstance().getLoginSession();
        BackupFileKeeper.resetBackupAlbum(loginSession.getUserInfo().getId());
        startBackupAlbum();
    }

    public int getBackupFileCount() {
        if (mBackupAlbumManager == null) {
            return 0;
        }
        return mBackupAlbumManager.getBackupListSize();
    }
    // ==========================================Auto Backup Album==========================================


    // ========================================Download and Upload file======================================
    // Download Operation
    public long addDownloadTask(OneOSFile file, String savePath) {
        DownloadElement element = new DownloadElement(file, savePath);
        return mDownloadManager.enqueue(element);
    }

    public ArrayList<DownloadElement> getDownloadList() {
        return mDownloadManager.getDownloadList();
    }

    public void pauseDownload(String fullName) {
        Log.d("OneSpaceService", "pause download: " + fullName);
        mDownloadManager.pauseDownload(fullName);
    }

    public void pauseDownload() {
        Log.d("OneSpaceService", "pause all download task");
        mDownloadManager.pauseDownload();
    }

    public void continueDownload(String fullName) {
        mDownloadManager.continueDownload(fullName);
    }

    public void continueDownload() {
        Log.d("OneSpaceService", "continue all download task");
        mDownloadManager.continueDownload();
    }

    public void cancelDownload(String path) {
        mDownloadManager.removeDownload(path);
    }

    public void cancelDownload() {
        mDownloadManager.removeDownload();
    }

    // Upload Operation
    public long addUploadTask(File file, String savePath) {
        UploadElement element = new UploadElement(file, savePath);
        return mUploadManager.enqueue(element);
    }

    public ArrayList<UploadElement> getUploadList() {
        return mUploadManager.getUploadList();
    }

    public void pauseUpload(String filepath) {
        Log.d("OneSpaceService", "pause upload: " + filepath);
        mUploadManager.pauseUpload(filepath);
    }

    public void pauseUpload() {
        Log.d("OneSpaceService", "pause all upload task");
        mUploadManager.pauseUpload();
    }

    public void continueUpload(String filepath) {
        Log.d("OneSpaceService", "continue upload: " + filepath);
        mUploadManager.continueUpload(filepath);
    }

    public void continueUpload() {
        Log.d("OneSpaceService", "continue all upload task");
        mUploadManager.continueUpload();
    }

    public void cancelUpload(String filepath) {
        mUploadManager.removeUpload(filepath);
    }

    public void cancelUpload() {
        mUploadManager.removeUpload();
    }

    /**
     * add download complete listener
     */
    public boolean addDownloadCompleteListener(DownloadManager.OnDownloadCompleteListener listener) {
        if (null != mDownloadManager) {
            return mDownloadManager.addDownloadCompleteListener(listener);
        }

        return true;
    }

    /**
     * remove download complete listener
     */
    public boolean removeDownloadCompleteListener(DownloadManager.OnDownloadCompleteListener listener) {
        if (null != mDownloadManager) {
            return mDownloadManager.removeDownloadCompleteListener(listener);
        }

        return true;
    }

    /**
     * add upload complete listener
     */
    public boolean addUploadCompleteListener(UploadManager.OnUploadCompleteListener listener) {
        if (null != mUploadManager) {
            return mUploadManager.addUploadCompleteListener(listener);
        }

        return true;
    }

    /**
     * remove upload complete listener
     */
    public boolean removeUploadCompleteListener(UploadManager.OnUploadCompleteListener listener) {
        if (null != mUploadManager) {
            return mUploadManager.removeUploadCompleteListener(listener);
        }

        return true;
    }
    // ========================================Download and Upload file======================================
}
