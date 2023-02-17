package com.example.a02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button sendButton;
    private TextView resultTextView;
    private ImoocBroadcaseReciver imoocBroadcaseReciver;
    public static String com_imooc_demo_afdsabf = "com_imooc_demo_afdsabf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //option + command + f
        editText = findViewById(R.id.inputEditText);
        sendButton = findViewById(R.id.sendBroadcastButton);
        resultTextView = findViewById(R.id.resultTextView);

        //  新建广播接收器
        imoocBroadcaseReciver = new ImoocBroadcaseReciver(resultTextView);
        //  为广播添加Action
        IntentFilter intentFilter = new IntentFilter();

        // 广播action，可以理解为名字
        intentFilter.addAction(com_imooc_demo_afdsabf);

        //  注册广播
        registerReceiver(imoocBroadcaseReciver, intentFilter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  发送广播
                Intent intent = new Intent();
                intent.setAction(com_imooc_demo_afdsabf);
                intent.putExtra("data", editText.getText().toString());
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (imoocBroadcaseReciver != null){
            //  取消注册
            unregisterReceiver(imoocBroadcaseReciver);
        }
    }
}