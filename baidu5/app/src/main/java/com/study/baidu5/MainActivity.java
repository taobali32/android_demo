package com.study.baidu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.PoiRegion;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    private LocService locService;


    private static final int MAP_TYPE_NORMAL = 101;
    private static final int MAP_TYPE_SATELLITE = 102;
    private static final int LocationActivity = 103;

    private static final int StopActivity = 104;


    private TextView LocationResult;

    private TextView LocationDiagnostic;


    private BDLocation mLastBDLocation;


    private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        LocationResult = (TextView) findViewById(R.id.textView1);
        LocationDiagnostic = (TextView) findViewById(R.id.textView2);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
        //
        mBaiduMap.setMyLocationEnabled(true);
        locService = ((LocationApplication) getApplication()).locService;
        LocationClientOption mOption = locService.getDefaultLocationClientOption();

        mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mOption.setCoorType("bd09ll");
        locService.setLocationOption(mOption);
        locService.registerListener(listener);
        locService.start();

    }


    @Override
    protected void onStart() {
        super.onStart();

        // -----------location config ------------
        locService = ((LocationApplication) getApplication()).locService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locService.setLocationOption(locService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locService.start();
        }

    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                int tag = 1;
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nsysTime : ");
                sb.append(SystemClock.elapsedRealtime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlongtitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nProvince : ");// 获取省份
                sb.append(location.getProvince());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nTown : ");// 获取镇信息
                sb.append(location.getTown());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nStreetNumber : ");// 获取街道号码
                sb.append(location.getStreetNumber());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append("poiName:");
                        sb.append(poi.getName() + ", ");
                        sb.append("poiTag:");
                        sb.append(poi.getTags() + "\n");
                    }
                }
                if (location.getPoiRegion() != null) {
                    sb.append("PoiRegion: ");// 返回定位位置相对poi的位置关系，仅在开发者设置需要POI信息时才会返回，在网络不通或无法获取时有可能返回null
                    PoiRegion poiRegion = location.getPoiRegion();
                    sb.append("DerectionDesc:"); // 获取POIREGION的位置关系，ex:"内"
                    sb.append(poiRegion.getDerectionDesc() + "; ");
                    sb.append("Name:"); // 获取POIREGION的名字字符串
                    sb.append(poiRegion.getName() + "; ");
                    sb.append("Tags:"); // 获取POIREGION的类型
                    sb.append(poiRegion.getTags() + "; ");
                    sb.append("\nSDK版本: ");
                }
                sb.append(locService.getSDKVersion()); // 获取SDK版本
                int permission = checkPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
                sb.append("\npermsission: " + permission);
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                logMsg(sb.toString(), tag);
            }
        }



        /**
         * 显示请求字符串
         *
         * @param str
         */
        public void logMsg(final String str, final int tag) {

            Log.i("aaaaa",str);
            try {
                if (LocationResult != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LocationResult.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (tag == Utils.RECEIVE_TAG) {
                                        LocationResult.setText(str);
                                    } else if (tag == Utils.DIAGNOSTIC_TAG) {
                                        LocationDiagnostic.setText(str);
                                    }
                                }
                            });
                        }
                    }).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
            int tag = 2;
            StringBuffer sb = new StringBuffer(256);
            sb.append("诊断结果: ");
            if (locType == BDLocation.TypeNetWorkLocation) {
                if (diagnosticType == 1) {
                    sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 2) {
                    sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType == 3) {
                    sb.append("定位失败，请您检查您的网络状态");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeCriteriaException) {
                if (diagnosticType == 4) {
                    sb.append("定位失败，无法获取任何有效定位依据");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 5) {
                    sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位");
                    sb.append(diagnosticMessage);
                } else if (diagnosticType == 6) {
                    sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 7) {
                    sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 9) {
                    sb.append("定位失败，无法获取任何有效定位依据");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeServerError) {
                if (diagnosticType == 8) {
                    sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限");
                    sb.append("\n" + diagnosticMessage);
                }
            }
            logMsg(sb.toString(), tag);
        }
    };




    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    BDAbstractLocationListener listener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub

            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                Message locMsg = locHander.obtainMessage();
                Bundle locData;
                locData = Algorithm(location);
                if (locData != null) {
                    locData.putParcelable("loc", location);
                    locMsg.setData(locData);
                    locHander.sendMessage(locMsg);
                }
            }
        }

    };

    /***
     * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
     * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
     * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
     *
     * @param location
     * @return Bundle
     */
    private Bundle Algorithm(BDLocation location) {
        Bundle locData = new Bundle();
        double curSpeed = 0;
        if (locationList.isEmpty() || locationList.size() < 2) {
            LocationEntity temp = new LocationEntity();
            temp.location = location;
            temp.time = System.currentTimeMillis();
            locData.putInt("iscalculate", 0);
            locationList.add(temp);
        } else {
            if (locationList.size() > 5)
                locationList.removeFirst();
            double score = 0;
            for (int i = 0; i < locationList.size(); ++i) {
                LatLng lastPoint = new LatLng(locationList.get(i).location.getLatitude(),
                        locationList.get(i).location.getLongitude());
                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
                curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).time) / 1000;
                score += curSpeed * Utils.EARTH_WEIGHT[i];
            }
            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
                location.setLongitude(
                        (locationList.get(locationList.size() - 1).location.getLongitude() + location.getLongitude())
                                / 2);
                location.setLatitude(
                        (locationList.get(locationList.size() - 1).location.getLatitude() + location.getLatitude())
                                / 2);
                locData.putInt("iscalculate", 1);
            } else {
                locData.putInt("iscalculate", 0);
            }
            LocationEntity newLocation = new LocationEntity();
            newLocation.location = location;
            newLocation.time = System.currentTimeMillis();
            locationList.add(newLocation);
        }
        return locData;
    }


    /***
     * 接收定位结果消息，并显示在地图上
     */
    @SuppressLint("HandlerLeak")
    private Handler locHander = new Handler() {

        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                BDLocation location = msg.getData().getParcelable("loc");
                int iscal = msg.getData().getInt("iscalculate");
                if (location != null) {
                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                    // 构建Marker图标
                    BitmapDescriptor bitmap = null;
                    if (iscal == 0) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark); // 非推算结果
                    } else {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark); // 推算结果
                    }

                    // 构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                    // 在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                }
            } catch (Exception e) {
                // TODO: handle exception
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStop() {

        locService.unregisterListener(mListener); //注销掉监听
        locService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locService.unregisterListener(listener);
        locService.stop();
        mMapView.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,MAP_TYPE_NORMAL,0,"切换普通地图");
        menu.add(Menu.NONE,MAP_TYPE_SATELLITE,0,"切换卫星地图");
        menu.add(Menu.NONE,LocationActivity,0,"开始定位");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case MAP_TYPE_NORMAL:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case MAP_TYPE_SATELLITE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case LocationActivity:
                //  开始定位

                locService.start();// 定位SDK

                break;

            case StopActivity:
                locService.stop();

                break;


        }
        return super.onOptionsItemSelected(item);
    }


    public static int checkPermission(Context context, String permission) {

        boolean allowedByPermission = true;
        try {
            int result = context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
            allowedByPermission = (result == PackageManager.PERMISSION_GRANTED);
        } catch (Exception e) {
            allowedByPermission = true;
        }
        if (!allowedByPermission) {
            return 0;
        } else {
            return 1;
        }
    }



    /**
     * 封装定位结果和时间的实体类
     *
     * @author baidu
     *
     */
    class LocationEntity {
        BDLocation location;
        long time;
    }

}