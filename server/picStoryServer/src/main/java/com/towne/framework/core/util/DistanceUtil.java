package com.towne.framework.core.util;

public class DistanceUtil {

	/**
	 * 根据经纬度计算距离
	 * @param lo1 经度1
	 * @param la1 纬度1
	 * @param lo2 经度2
	 * @param la2 纬度2
	 * @return
	 */
	public static double distanceByLBS(double lo1, double la1, double lo2, double la2) {
		double radLat1 = la1 * Math.PI / 180;
		double radLat2 = la2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lo1 * Math.PI / 180 - lo2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(DistanceUtil.distanceByLBS(121.479358, 31.23928, 121.484928, 31.237646));
	}
}
