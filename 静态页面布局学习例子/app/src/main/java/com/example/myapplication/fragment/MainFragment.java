package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MainHeaderAdAdapter;
import com.example.myapplication.adapter.MainMenuAdapter;
import com.example.myapplication.util.DataUtil;

/**
 * 主界面视图
 */
public class MainFragment  extends Fragment {

    protected ViewPager $mViewPagerHeaderAd;    //  广告头部

    protected int[] icons = {R.mipmap.header_pic_ad1,R.mipmap.header_pic_ad2,R.mipmap.header_pic_ad1};

    protected int[] menuIcons = {
            R.mipmap.menu_airport,
            R.mipmap.menu_car,
            R.mipmap.menu_course,
            R.mipmap.menu_hatol,

            R.mipmap.menu_nearby,
            R.mipmap.menu_car,
            R.mipmap.menu_ticket,
            R.mipmap.menu_hatol
    };

    protected RecyclerView $recyclerViewMenu;   //  主菜单
    private String[] menus; //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menus = this.getActivity().getResources().getStringArray(R.array.main_menu);

        $mViewPagerHeaderAd = (ViewPager) getView().findViewById(R.id.vpager_main_header_ad);
        $recyclerViewMenu = (RecyclerView)getView().findViewById(R.id.recyclwview_main_menu);


        MainHeaderAdAdapter adapter = new MainHeaderAdAdapter(getActivity(), DataUtil.getHeaderAddInfo(getActivity(),icons));
        $mViewPagerHeaderAd.setAdapter(adapter);

        //  菜单
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getActivity(),DataUtil.getMainMenus(menuIcons,menus));

        // 布局样式
        $recyclerViewMenu.setLayoutManager(new GridLayoutManager(getActivity(),4));

        $recyclerViewMenu.setAdapter(mainMenuAdapter);
    }
}
