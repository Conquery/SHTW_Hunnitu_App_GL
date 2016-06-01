package com.shtoone.glhnt.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommFunctions {
	@SuppressLint("SimpleDateFormat")
	public static String ChangeTimeToLong(String time) {
		if(time.length() == 10)
			time = time + " 00:00:00";
		String longTime = "";
		time = time.replace("��", "-").replace("��", "-").replace("��", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			longTime = String.valueOf(date.getTime() / 1000);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return longTime;
	}

	public static String[] GetTimeFromTo() throws ParseException {
		// ����Ĵ���
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endtime = format.format(date);
		date = format.parse(endtime);
		endtime = String.valueOf(date.getTime() / 1000);

		// ��ǰһ��
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);

		// ��1��ǰʱ��ת��Ϊʱ���
		Date dateFrom = cal.getTime();
		String starttime = format.format(dateFrom);
		dateFrom = format.parse(starttime);
		starttime = String.valueOf(dateFrom.getTime() / 1000);

		// ����1��ǰ�ͽ���
		String[] arr = new String[] { starttime, endtime };

		return arr;
	}

	public static String[] GetTimeFromToYYYYMMM() throws ParseException {
		// ����Ĵ���
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endtime = format.format(date).replace(" ", "%20");

		// ��ǰһ��
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);

		// ��1��ǰʱ��ת��Ϊʱ���
		Date dateFrom = cal.getTime();
		String starttime = format.format(dateFrom).replace(" ", "%20");

		// ����1��ǰ�ͽ���
		String[] arr = new String[] { starttime, endtime };

		return arr;
	}

	/**
	 * <li>����������ʱ������õ�����
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		beginDateStr = beginDateStr.replace("��", "-").replace("��", "-").replace("��", "");
		endDateStr = endDateStr.replace("��", "-").replace("��", "-").replace("��", "");
		beginDateStr = beginDateStr.split(" ")[0];
		endDateStr = endDateStr.split(" ")[0];
		Log.d("day1", beginDateStr);
		Log.d("day2", endDateStr);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("���������="+day);
		} catch (ParseException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		return day;
	}

}
