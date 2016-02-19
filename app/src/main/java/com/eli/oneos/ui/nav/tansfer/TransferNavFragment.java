package com.eli.oneos.ui.nav.tansfer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eli.oneos.R;
import com.eli.oneos.ui.MainActivity;
import com.eli.oneos.ui.nav.BaseNavFragment;
import com.eli.oneos.utils.Utils;
import com.eli.oneos.widget.MenuPopupView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyun@eli-tech.com on 2016/02/19.
 */
public class TransferNavFragment extends BaseNavFragment implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = TransferNavFragment.class.getSimpleName();

    private static final int[] TRANS_CONTROL_TITLE = new int[]{R.string.start_all, R.string.pause_all, R.string.delete_all};

    private static final int[] TRANS_CONTROL_ICON = new int[]{R.drawable.ic_title_menu_download, R.drawable.ic_title_menu_pause, R.drawable.ic_title_menu_delete};
    private static final int[] RECORD_CONTROL_TITLE = new int[]{R.string.delete_all};
    private static final int[] RECORD_CONTROL_ICON = new int[]{R.drawable.ic_title_menu_delete};

    private RadioGroup mUploadOrDownloadGroup;
    private RadioGroup mTransOrCompleteGroup;
    private RadioButton mTransferBtn, mCompleteBtn;

    private MenuPopupView mMenuView;

    private boolean isDownload;
    private boolean isTransfer;
    private BaseTransferFragment mCurFragment;
    private List<BaseTransferFragment> mFragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "On Create View");

        mMainActivity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_nav_transfer, container, false);

        initView(view);
        initFragment();

        return view;
    }

    private void initView(View view) {
        mUploadOrDownloadGroup = (RadioGroup) view.findViewById(R.id.segmented_radiogroup);
        mUploadOrDownloadGroup.setOnCheckedChangeListener(this);

        mTransOrCompleteGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        mTransOrCompleteGroup.setOnCheckedChangeListener(this);

        mTransferBtn = (RadioButton) view.findViewById(R.id.radio_transfe);
        mCompleteBtn = (RadioButton) view.findViewById(R.id.radio_complete);

        ImageButton mControlBtn = (ImageButton) view.findViewById(R.id.btn_control);
        mControlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                mMenuView = new MenuPopupView(getActivity(), Utils.dipToPx(130));
                if (mCurFragment instanceof TransmissionFragment) {
                    mMenuView.setMenuItems(TRANS_CONTROL_TITLE, TRANS_CONTROL_ICON);
                } else if (mCurFragment instanceof RecordsFragment) {
                    mMenuView.setMenuItems(RECORD_CONTROL_TITLE, RECORD_CONTROL_ICON);
                }
                mMenuView.setOnMenuClickListener(new MenuPopupView.OnMenuClickListener() {
                    @Override
                    public void onMenuClick(int index, View view) {
                        mCurFragment.onMenuClick(index, view);
                    }
                });
                mMenuView.showPopupDown(v, -1, true);
            }
        });
    }

    private void initFragment() {
        TransmissionFragment mDownloadingFragment = new TransmissionFragment(true);
        mFragmentList.add(mDownloadingFragment);
        RecordsFragment mDownloadedFragment = new RecordsFragment(true);
        mFragmentList.add(mDownloadedFragment);
        TransmissionFragment mUploadingFragment = new TransmissionFragment(false);
        mFragmentList.add(mUploadingFragment);
        RecordsFragment mUploadedFragment = new RecordsFragment(false);
        mFragmentList.add(mUploadedFragment);

        isDownload = true;
        isTransfer = true;
        onChangeFragment(isDownload, isTransfer);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurFragment.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurFragment.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.segmented_radiogroup) {
            if (checkedId == R.id.segmented_download) {
                isDownload = true;
            } else {
                isDownload = false;
            }
        } else {
            if (checkedId == R.id.radio_transfe) {
                isTransfer = true;
            } else {
                isTransfer = false;
            }
        }

        onChangeRadioBtnText(isDownload);
        onChangeFragment(isDownload, isTransfer);
    }

    public void onChangeRadioBtnText(boolean isDownload) {
        if (isDownload) {
            mTransferBtn.setText(R.string.downloading_list);
            mCompleteBtn.setText(R.string.download_record);
        } else {
            mTransferBtn.setText(R.string.uploading_list);
            mCompleteBtn.setText(R.string.upload_record);
        }
    }

    /**
     * chang fragment layout
     *
     * @param isDownload Download/Upload
     * @param isTransfer Transfer/Complete
     */
    public void onChangeFragment(boolean isDownload, boolean isTransfer) {
        BaseTransferFragment mFragment = mFragmentList.get(getFragmentIndex(isDownload, isTransfer));

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (mCurFragment != null) {
            mCurFragment.onPause();
        }

        if (!mFragment.isAdded()) {
            transaction.add(R.id.transfer_frame_layout, mFragment);
        } else {
            mFragment.onResume();
        }

        for (Fragment fragment : mFragmentList) {
            if (mFragment == fragment) {
                transaction.show(fragment);
                mCurFragment = mFragment;
            } else {
                transaction.hide(fragment);
            }
        }

        transaction.commit();
    }

    private int getFragmentIndex(boolean isDownload, boolean isTranfer) {
        if (isDownload) {
            if (isTranfer) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (isTranfer) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * Use to handle parent Activity back action
     *
     * @return If consumed returns true, otherwise returns false.
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    /**
     * Network State Changed
     *
     * @param isAvailable
     * @param isWifiAvailable
     */
    @Override
    public void onNetworkChanged(boolean isAvailable, boolean isWifiAvailable) {

    }
}
