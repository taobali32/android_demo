package com.example.a02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class ImoocBroadcaseReciver  extends BroadcastReceiver {
    TextView mtextView;

    private static final String TAG = "ImoocBroadcaseReciver";

    public ImoocBroadcaseReciver(TextView textView) {
        mtextView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ImoocBroadcaseReciver onReceive");

        //  接收广播
        if (intent != null){
            // 接收到的什么广播
            String action = intent.getAction();

            Log.d(TAG, "onReceive: " + action);

            // 判断是什么广播，是不是我们自己定义的广播
            if (TextUtils.equals(action,MainActivity.com_imooc_demo_afdsabf)){
                //  接收到的数据
                if (mtextView != null){
                    //  设置数据
                    String data = intent.getStringExtra("data");

                    String ss = "接收到到action:" + action + "的广播，数据为：" + data;

                    mtextView.setText(ss);
                }
            }
        }
    }
}
