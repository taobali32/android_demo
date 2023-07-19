package com.study.baidu5;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {
    private Activity mActivity;

    private int mReqCode;
    private CallBack mCallback;

    public static interface CallBack{
        void grantAll();
        void denied();
    }

    public PermissionUtils(Activity activity){
        mActivity = activity;
    }

    public void request(List<String> needPermissions, int reqCode, CallBack callback){
        if (needPermissions.isEmpty()){
            return;
        }

        if (Build.VERSION.SDK_INT < 23){
            callback.grantAll();
            return;
        }

        if (mActivity == null){
            throw new IllegalArgumentException("activity is null");
        }

        mReqCode = reqCode;
        mCallback = callback;

        List<String> reqPermissions = new ArrayList<>();

        for (String permission : needPermissions){
            if (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                reqPermissions.add(permission);
            }
        }

        if (needPermissions.isEmpty()){
            callback.grantAll();
            return;
        }

        mActivity.requestPermissions(reqPermissions.toArray(new String[]{}),reqCode);
    }

    //
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (permissions.length > 0){
            if (requestCode == mReqCode){
                boolean gratAll = true;

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        gratAll = false;

                        Toast.makeText(mActivity, permissions[i] + "未授权", Toast.LENGTH_SHORT).show();

                        break;
                    }
                }

                if (gratAll){
                    mCallback.grantAll();
                }else {
                    mCallback.denied();
                }
            }
        }else {
            mCallback.grantAll();
        }


    }
}
