package com.study.baidu5;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.basestruct.Point;

public class DistanceUtil {
    public DistanceUtil() {
    }

    public static double getDistance(LatLng p1LL, LatLng p2LL) {
        if (p1LL != null && p2LL != null) {
            Point p1 = CoordUtil.ll2point(p1LL);
            Point p2 = CoordUtil.ll2point(p2LL);
            return p1 != null && p2 != null ? CoordUtil.getDistance(p1, p2) : -1.0;
        } else {
            return -1.0;
        }
    }
}
