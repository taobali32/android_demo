package com.example.t1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t1.R;
import com.example.t1.bean.Chapter;
import com.example.t1.bean.ChapterItem;

import java.util.List;

public class ChapterAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private final List<Chapter> mDatas;

    private final LayoutInflater myInflater;

    public ChapterAdapter(Context context, List<Chapter> datas) {
        mContext = context;
        mDatas = datas;

        myInflater = LayoutInflater.from(context);
    }



    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mDatas.get(i).getChildren().size();
    }

    @Override
    public Object getGroup(int i) {
        return mDatas.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDatas.get(i).getChildren().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {

        ParentViewHolder parentViewHolder = null;

        if (view == null){
            view = myInflater.inflate(R.layout.item_parent_chapter, viewGroup, false);

            parentViewHolder = new ParentViewHolder();
            parentViewHolder.tvTitle = view.findViewById(R.id.id_tv_name);
            parentViewHolder.id_iv_indicator = view.findViewById(R.id.id_iv_indicator);
            view.setTag(parentViewHolder);
        }else {
            parentViewHolder = (ParentViewHolder) view.getTag();
        }

        Chapter chapter = mDatas.get(i);
        parentViewHolder.tvTitle.setText(chapter.getName());

        parentViewHolder.id_iv_indicator.setImageResource(R.drawable.group_indicator);
        parentViewHolder.id_iv_indicator.setSelected(isExpanded);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;

        if (view == null){
            view = myInflater.inflate(R.layout.item_child_chapter, null, false);

            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = view.findViewById(R.id.id_tv_name);
            view.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        ChapterItem chapterItem = mDatas.get(i).getChildren().get(i1);

        childViewHolder.tvTitle.setText(chapterItem.getName());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ParentViewHolder{
        TextView tvTitle;

        ImageView id_iv_indicator;
    }

    public static class ChildViewHolder{
        TextView tvTitle;
    }
}
