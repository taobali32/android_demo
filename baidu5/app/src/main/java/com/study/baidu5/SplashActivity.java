package com.study.baidu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashActivity extends Activity {

    private final int DIALOG_KEY_BACK = 1;
    private final int DIALOG_PERMISSION = 2;

    private String permissionInfo;

    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //创建弹窗显示隐私政策
        if (!Utils.contains(this, Utils.SP_PRIVACY_DIALOG)) {
            createPrivacyDialog();
        } else {
            boolean status = Utils.getString(SplashActivity.this, Utils.SP_PRIVACY_STATUS).equals("1");
            initSDK(status);
        }

        //
        toMainActivity();
    }

    private void createPrivacyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String notifyString = "为进一步加强对最终用户个人信息的安全保护措施, 请仔细阅读如下隐私政策并确认是否同意：\n《服务隐私政策》";
        SpannableStringBuilder spannableString = new SpannableStringBuilder(notifyString);
        Pattern pattern = Pattern.compile("《服务隐私政策》");
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            setClickableSpan(spannableString, matcher);
        }

        View view = View.inflate(this, R.layout.notify_privacy_text, null);
        TextView notifyText = (TextView) view.findViewById(R.id.notify_text);
        notifyText.setText(spannableString);
        notifyText.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setView(view);
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                initSDK(true);
                Utils.putString(SplashActivity.this, Utils.SP_PRIVACY_DIALOG, Utils.SP_PRIVACY_DIALOG);
                Utils.putString(SplashActivity.this, Utils.SP_PRIVACY_STATUS, "1");
            }
        });

        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initSDK(false);
                Utils.putString(SplashActivity.this, Utils.SP_PRIVACY_STATUS, "0");
            }
        });


        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        layoutParams.weight = 10;
        positiveButton.setLayoutParams(layoutParams);
        negativeButton.setLayoutParams(layoutParams);
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog(String title, String message, final int type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (type == DIALOG_KEY_BACK) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                        }

                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    private void onUsePermission() {
        if (!Utils.contains(this, Utils.SP_PERMISSION_DIALOG)) {
            Utils.putString(this, Utils.SP_PERMISSION_DIALOG, Utils.SP_PERMISSION_DIALOG);
            showMissingPermissionDialog("定位SDK DEMO在使用时需要申请以下权限：", "1、定位权限，用于定位功能测试。\n2、读写权限，用于写入离线定位数据。\n", DIALOG_PERMISSION);
        }
    }

    private void setClickableSpan(SpannableStringBuilder span, Matcher matcher) {
        int start = matcher.start();
        int end = matcher.end();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                final Uri uri = Uri.parse("https://lbsyun.baidu.com/index.php?title=openprivacy");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        span.setSpan(new ForegroundColorSpan(Color.CYAN), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void initSDK(boolean status) {
        LocationClient.setAgreePrivacy(status);
        ((LocationApplication)getApplication()).locService = new LocService(getApplicationContext());

        onUsePermission();
        getPersimmions();
    }


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }


    private void toMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}