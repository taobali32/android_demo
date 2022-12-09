package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ListView appListView;
    private List<String> appNames;
    private List<ResolveInfo> appInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        appListView = (ListView) findViewById(R.id.app_list_view);

        appInfos = getAppInfos();
        appListView.setAdapter(new AppListAdapter(appInfos));

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View headerView = layoutInflater.inflate(R.layout.header_list_view,null);
        appListView.addHeaderView(headerView);

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String packageName = appInfos.get(i).activityInfo.packageName;

                //  com.coloros.alarmclock
                Log.e("packageName:",packageName);
                String className =  appInfos.get(i).activityInfo.name;

                //  com.coloros.alarmclock.AlarmClock
                Log.e("className:",className);

                ComponentName componentName = new ComponentName(packageName,className);

                Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }

    //  获取所有应用信息
    private List<ResolveInfo> getAppInfos(){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return getPackageManager().queryIntentActivities(intent,0);

    }

    public class AppListAdapter extends BaseAdapter{

        List<ResolveInfo> mAppname;
        public AppListAdapter(List<ResolveInfo> appNamse){
            mAppname = appNamse;
        }

        @Override
        public int getCount() {
            // 有多少条数据
            return mAppname.size();
        }

        @Override
        public Object getItem(int i) {
            return mAppname.get(i);
        }

        @Override
        public long getItemId(int i) {
            //  获取当前位置这一条数据ID
            return i;
        }

        @Override
        public View getView(int i, View converView, ViewGroup parent) {
            // 处理 view -- data 填充数据

            ViewHolder viewHolder = new ViewHolder();
            if (converView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                converView = layoutInflater.inflate(R.layout.item_app_list_view, null);

                viewHolder.appIconImageView = (ImageView)converView.findViewById(R.id.app_icon_image_view);
                viewHolder.appTextView = (TextView) converView.findViewById(R.id.app_name_iamge_view);

                converView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)converView.getTag();
            }

            viewHolder.appTextView.setText(mAppname.get(i).activityInfo.loadLabel(getPackageManager()));
            viewHolder.appIconImageView.setImageDrawable(mAppname.get(i).activityInfo.loadIcon(getPackageManager()));

            return converView;
        }


        public class ViewHolder{
            public ImageView appIconImageView;
            public TextView appTextView;
        }
    }
}