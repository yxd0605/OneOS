package com.eli.oneos.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.eli.oneos.MyApplication;
import com.eli.oneos.R;
import com.eli.oneos.utils.DialogUtils;
import com.eli.oneos.utils.SystemBarManager;
import com.eli.oneos.widget.LoadingView;
import com.eli.oneos.widget.TipView;

/**
 * Base Activity for OneSpace
 * <p/>
 * Created by gaoyun@eli-tech.com on 2016/1/7.
 */
public class BaseActivity extends FragmentActivity {

    protected View mRootView;
    private SystemBarManager mTintManager;
    private LoadingView mLoadingView;
    private TipView mTipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        mLoadingView = LoadingView.getInstance();
        mTipView = TipView.getInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DialogUtils.dismiss();
        dismissLoading();
    }

    /**
     * Modify System Status Bar Style
     */
    protected void initSystemBarStyle() {
        initSystemBarStyle(R.color.status_bar);
    }

    /**
     * Modify System Status Bar Style
     *
     * @param colorId Status Bar background color resource id
     */
    protected void initSystemBarStyle(int colorId) {
        if (null == mTintManager) {
            mTintManager = new SystemBarManager(this);
        }
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(colorId);
        mTintManager.setNavigationBarTintResource(colorId);
    }

    public void showLoading() {
        mLoadingView.show(this);
    }

    public void showLoading(int msgId) {
        mLoadingView.show(this, msgId);
    }

    public void showLoading(int msgId, boolean isCancellable) {
        mLoadingView.show(this, msgId, isCancellable);
    }

    public void showLoading(int msgId, int timeout, DialogInterface.OnDismissListener listener) {
        mLoadingView.show(this, msgId, timeout, listener);
    }

    public void showLoading(int msgId, boolean isCancellable, DialogInterface.OnDismissListener listener) {
        mLoadingView.show(this, msgId, isCancellable, -1, listener);
    }

    public void dismissLoading() {
        mLoadingView.dismiss();
    }

    public void showTipView(int msgId, boolean isPositive) {
        dismissLoading();
        mTipView.show(this, mRootView, msgId, isPositive);
    }

    public void showTipView(String msg, boolean isPositive) {
        dismissLoading();
        mTipView.show(this, mRootView, msg, isPositive);
    }

    public boolean controlActivity(String action) {
        return false;
    }
}
