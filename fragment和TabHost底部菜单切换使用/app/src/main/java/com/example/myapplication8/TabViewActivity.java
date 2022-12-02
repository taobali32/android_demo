package com.example.myapplication8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class TabViewActivity extends AppCompatActivity implements TabHost.TabContentFactory {

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    private TabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_view_page);

        //          // 初始化总布局
        tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        // 1. init data
        int[] titleIds = {
                R.string.home,
                R.string.msg,
                R.string.me,
        };

        //
        int[] drawableIDS = {
                R.drawable.main_tab_icon_home,
                R.drawable.main_tab_icon_msg,
                R.drawable.main_tab_icon_me
        };

        // data --> view

        for (int index = 0; index < titleIds.length; index++) {
            View view = getLayoutInflater().inflate(R.layout.tab_layout,null,false);

            ImageView icon = (ImageView) view.findViewById(R.id.main_tab_icon);

            TextView title = (TextView) view.findViewById(R.id.main_tab_txt);

            View tab = view.findViewById(R.id.tab_bg);

            //
            icon.setImageResource(drawableIDS[index]);
            title.setText(titleIds[index]);

            //
            tab.setBackgroundColor(getResources().getColor(R.color.white));

            //
            tabHost.addTab(
                    tabHost.newTabSpec(getString(titleIds[index]))
                            .setIndicator(view)
                            .setContent(this)
            );


        }


        //  3个fragment组成的viewpager
        Fragment[] fragments = new Fragment[]{
                TestFragment.newInstance("home"),
                TestFragment.newInstance("message"),
                TestFragment.newInstance("me")
        };

        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (tabHost != null){
                    tabHost.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

                if (tabHost != null){
                    int position = tabHost.getCurrentTab();

                    viewPager.setCurrentItem(position);
                }

            }
        });

    }

    @Override
    public View createTabContent(String s) {
        View view = new View(this);

        view.setMinimumHeight(0);
        view.setMinimumWidth(0);

        return view;
    }
}
