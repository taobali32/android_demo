package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Menu;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuViewholder> {

    protected Context context;
    protected List<Menu> menus;

    public  MainMenuAdapter(Context context, List<Menu> menus){
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public MainMenuViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  布局渲染成视图
        return new MainMenuViewholder(LayoutInflater.from(context).inflate(R.layout.item_main_menu,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewholder holder, int position) {

        Menu menu = menus.get(position);

        holder.mImgMenuIcon.setImageResource(menu.icon);
        holder.mTxtMenuName.setText(menu.menuName);
    }

    @Override
    public int getItemCount() {
        return null != menus ? menus.size() : 0;
    }
}

class MainMenuViewholder extends RecyclerView.ViewHolder{

    public ImageView mImgMenuIcon;

    public TextView mTxtMenuName;

    public MainMenuViewholder(@NonNull View itemView) {
        super(itemView);

        mImgMenuIcon = (ImageView)itemView.findViewById(R.id.img_menu_icon);
        mTxtMenuName = (TextView)itemView.findViewById(R.id.txt_menu_name);
    }
}