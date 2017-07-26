package blog.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** 
 * 日期处理的辅助类
 * @author zjz
 */
public class DateUtil {
	private static DateFormat df_1;
	private static DateFormat df_2;
	private static DateFormat df_3;
	private static DateFormat df_4;
	
	private static final long DAY_MILLIS = 86400000L;  //一天中的秒数
	
	static{
		df_1 = new SimpleDateFormat("HH:mm");
		df_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		df_3 = new SimpleDateFormat("yyyy-MM-dd");
		df_4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 确定当日日期上下限
	 * @param up 是否是上限
	 * @return
	 */
	public static Timestamp getTimeBorderToday(boolean up){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now = df.format(new Timestamp(System.currentTimeMillis()));
		if(up){
			now += " 00:00:00";
		}else{
			now += " 23:59:59";
		}
		return Timestamp.valueOf(now);
	}
	
	/**
	 * 日期按格式转化为字符串
	 * @param time 输入日期
	 * @return
	 */
	public static String getConvertedTime(Timestamp time){
		String res = null;
		String timeStr = df_3.format(time);
		long timeMillis = Timestamp.valueOf(timeStr + " 00:00:00").getTime();
		if(System.currentTimeMillis() - timeMillis >= DAY_MILLIS){
			res = df_2.format(time);
		}else{
			res = df_1.format(time);
		}
		return res;
	}
	
	/**
	 * 日期转化为适合插入数据库的字符串
	 * @param time
	 * @return
	 */
	public static String timeToSQLString(Timestamp time){
		return df_4.format(time);
	}
}
