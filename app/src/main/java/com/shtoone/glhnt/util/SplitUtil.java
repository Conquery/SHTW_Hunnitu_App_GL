package com.shtoone.glhnt.util;

import com.shtoone.glhnt.entity.COM_O2O;
import com.shtoone.glhnt.entity.COM_XY;

import java.util.ArrayList;
import java.util.List;

public class SplitUtil {
    public static List<COM_O2O> HNTQD_CurveSplit(String hezai,String qiangdu, String str1,String str2){
        List<COM_O2O> items = new ArrayList<>();
        String[] arr_hz = hezai.split("&");
        String[] arr_qd = qiangdu.split("&");
        String[] arr1 = str1.split("&");
        String[] arr2 = str2.split("&");
        for (int i = 0; i < arr1.length; i++) {
            COM_O2O item1 = new COM_O2O();
            item1.setName1(arr_hz[i]);
            item1.setName2(arr_qd[i]);
            //此处绘制ArrayList<COM_XY>
            List<COM_XY> serials = new ArrayList<COM_XY>();
            String[] dataX = arr1[i].split(",");
            String[] dataY = arr2[i].split(",");
            for(int j=0; j<dataX.length;j++) {
                if((!"null".equals(dataY[j])) && (!"".equals(dataY[j]))){
                    COM_XY tmp = new COM_XY();
                    tmp.setName1(String.valueOf(Math.round(Double.valueOf(dataY[j]))));
                    tmp.setName2(Double.valueOf(dataX[j]));
                    serials.add(tmp);
                }
            }
            item1.setListXYs(serials);
            items.add(item1);
        }
        return items;
    }

    public static List<COM_O2O> GJ_CurveSplit(String data1,String data2,String data3,String data4, String str1,String str2){
        List<COM_O2O> items = new ArrayList<>();
        String[] arr_data1 = data1.split("&");
        String[] arr_data2 = data2.split("&");
        String[] arr_data3 = data3.split("&");
        String[] arr_data4 = data4.split("&");
        String[] arr1 = str1.split("&");
        String[] arr2 = str2.split("&");
        for (int i = 0; i < arr1.length; i++) {
            COM_O2O item1 = new COM_O2O();
            item1.setName1(arr_data1[i]);
            item1.setName2(arr_data2[i]);
            if(arr_data3.length > i)
                item1.setName3(arr_data3[i]);
            if(arr_data4.length > i)
                item1.setName4(arr_data4[i]);
            //此处绘制ArrayList<COM_XY>
            List<COM_XY> serials = new ArrayList<COM_XY>();
            String[] dataX = arr1[i].split(",");
            String[] dataY = arr2[i].split(",");
            for(int j=0; j<dataX.length;j++) {
                COM_XY tmp = new COM_XY();
                if((!"null".equals(dataY[j])) && (!"".equals(dataY[j]))){
                    tmp.setName1(String.valueOf(Math.round(Double.valueOf(dataY[j]))));
                    tmp.setName2(Double.valueOf(dataX[j]));
                    serials.add(tmp);
                }
            }

            item1.setListXYs(serials);
            items.add(item1);
        }
        return items;
    }
}
