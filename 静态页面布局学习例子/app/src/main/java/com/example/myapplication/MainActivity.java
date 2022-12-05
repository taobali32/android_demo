package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.fragment.FindFragment;
import com.example.myapplication.fragment.MainFragment;
import com.example.myapplication.fragment.MeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected MainFragment $mainFragment = new MainFragment();
    protected FindFragment $findFragment = new FindFragment();
    protected MeFragment $meFragment     = new MeFragment();

    protected LinearLayout $mMenuMain;
    protected LinearLayout $mMainFind;
    protected LinearLayout $mMainMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 添加fragment
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content,$mainFragment)

                .add(R.id.container_content,$findFragment)
                    .hide($findFragment)

                .add(R.id.container_content,$meFragment)
                    .hide($meFragment)

                .commit();

        // 获取管理类
        // 事务添加 默认: 显示首页  其他页面:隐藏
        // 提交
    }


    public void initView()
    {
        $mMenuMain = (LinearLayout) this.findViewById(R.id.menu_main);
        $mMainFind = (LinearLayout) this.findViewById(R.id.menu_find);
        $mMainMe = (LinearLayout) this.findViewById(R.id.menu_me);


        $mMenuMain.setOnClickListener(this);
        $mMainFind.setOnClickListener(this);
        $mMainMe.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_main:
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show($mainFragment)
                        .hide($findFragment)
                        .hide($meFragment)
                        .commit();
                break;

            case R.id.menu_find:

                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide($mainFragment)
                        .show($findFragment)
                        .hide($meFragment)
                        .commit();
                break;

            case R.id.menu_me:
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide($mainFragment)
                        .hide($findFragment)
                        .show($meFragment)
                        .commit();
                break;
        }
    }
}

