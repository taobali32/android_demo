package com.study.baidu5;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationData;

public class LocationInstance {
    public static LocationInstance.MyLocationListener MyLocationListener;
    public LocationClient mLocationClient;

    private MyLocationListener mListener;

    private static BaiduMap mBaiduMap = null;

    public LocationInstance(Context context,BaiduMap mBaiduMap2,MyLocationListener mListener) throws Exception {
        mBaiduMap = mBaiduMap2;

        mLocationClient = new LocationClient(context.getApplicationContext());

        mLocationClient.registerLocationListener(mListener);


        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //
        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);
    }


    public void start(){
        mLocationClient.start();
    }

    public void stop(){
        mLocationClient.stop();
    }


    public static class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null){
//                return;
//            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);


            double getLatitude = location.getLatitude();
            double getLongitude = location.getLongitude();

            float radius = location.getRadius();

            String coorType = location.getCoorType();

            int errorCode = location.getLocType();



        }

    }
}
