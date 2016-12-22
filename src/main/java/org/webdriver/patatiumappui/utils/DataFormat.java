package org.webdriver.patatiumappui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat {

	public   static String formatDate(Date date,String format)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		System.out.println(formatter.format(date).toString());
		return formatter.format(date).toString();

	}
	public  static String formatDate(long date,String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		System.out.println(formatter.format(date));
		return formatter.format(date);
	}
	//支持YY-MM-DD转换成YYYY-MM-DD
	public  static String formatDate(String date,String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		String sss = null;
		try {
			sss = sdf2.format(sdf.parse(date));
			System.out.println(sss);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sss;
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
