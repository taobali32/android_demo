package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.LessonInfo;

import java.util.ArrayList;
import java.util.List;

public class RequestDataAdapter extends BaseAdapter {
    List<LessonInfo> mLessionInfos = new ArrayList<>();

    Context mContext;

    public RequestDataAdapter(Context context,List<LessonInfo> infos){
        mLessionInfos = infos;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mLessionInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mLessionInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_app_list_view,null);

            viewHolder.appIconImageView = (ImageView)convertView.findViewById(R.id.app_icon_image_view);
            viewHolder.appTextView = (TextView) convertView.findViewById(R.id.app_name_iamge_view);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.appTextView.setText(mLessionInfos.get(i).getmName());
        viewHolder.appIconImageView.setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder{
        public ImageView appIconImageView;
        public TextView appTextView;
    }
}
