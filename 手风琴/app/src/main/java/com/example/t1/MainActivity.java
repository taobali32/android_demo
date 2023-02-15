package com.example.t1;


import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.t1.adapter.ChapterAdapter;
import com.example.t1.bean.Chapter;
import com.example.t1.biz.ChapterBiz;

import java.util.ArrayList;
import java.util.List;


/**
 * 用着还行
 */
public class MainActivity extends AppCompatActivity  {

    private ExpandableListView expandableListView;

    private BaseExpandableListAdapter adapter;

    private final List<Chapter> mDatas = new ArrayList<>();

    private ChapterBiz chapterBiz = new ChapterBiz();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = getFilesDir() + "/stu.db";
        initViews();

        initEvents();

        loadDatas();
    }


    private void loadDatas(){
        chapterBiz.loadDatas(this,new ChapterBiz.Callback() {
            @Override
            public void onSucceed(List<Chapter> chapters) {
                mDatas.clear();
                mDatas.addAll(chapters);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception ex) {
                ex.printStackTrace();
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        },false);
    }

    private void initEvents() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this, "groupPosition = " + groupPosition + " childPosition = " + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(MainActivity.this, "groupPosition = " + groupPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //  收缩
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(MainActivity.this, "groupPosition = " + groupPosition + " collapse", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(MainActivity.this, "groupPosition = " + groupPosition + " expand", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews()
    {
        expandableListView = findViewById(R.id.id_expandableListView);
        mDatas.clear();

//        mDatas.addAll(ChapterLab.generateMockDatas());
        adapter = new ChapterAdapter(this,mDatas);

        expandableListView.setAdapter(adapter);
    }

}