package in.co.rays.proj3.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * DataUtility class is used to convert data from one format to another.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class DataUtility {
	
	public static final String APP_DATE_FORMAT="dd-MM-yyyy";
	
	public static final String APP_TIME_FORMAT="dd-MM-yyyy HH:mm:ss";
	
	private static final DateTimeFormatter localDateformatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static final SimpleDateFormat formatter=new SimpleDateFormat(APP_DATE_FORMAT);
	
	private static final SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat timeFormatter=new SimpleDateFormat(APP_TIME_FORMAT);
	
	public static String getString(String val) {
		if(DataValidator.isNotNull(val)) {
			return val.trim();
		}else {
			return val;
		}
	}
	
	public static String getStringData(Object val) {
		if(val!=null) {
			return val.toString();
		}else {
			return "";
		}
	}
	
	public static int getInt(String val) {
		if(DataValidator.isNotNull(val)){
			return Integer.parseInt(val);
		}else {
			return 0;
		}
	}
	
	public static BigDecimal getBigDecimal(String val) {
		if(DataValidator.isNotNull(val)){
			return new BigDecimal(val.trim());
		}else {
			return null;
		}
	}
	
	public static Long getLong(String val) {
		System.out.println("........in dataUtility..........."+val);
		if (DataValidator.isLong(val)) {
		System.out.println("........in dataUtility"+val+",,,,,,"+Long.parseLong(val));
		return Long.parseLong(val);
		} else {
		return (long) 0;
		}	}
	
	public static Date getDate(String val) {
		Date date=null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date;
	}
	
	public static LocalDate getLocalDate(String val) {
		LocalDate date=null;
		try {
			date = LocalDate.parse(val, localDateformatter);
			//date=LocalDate.parse(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("........in dataUtility..localDate"+date);
		return date;
	}
	
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	public static String getLocalDateString(LocalDate date) {
		try {
			return localDateformatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	public static Timestamp getTimestamp(String val) {
		Timestamp timestamp=null;
		try {
			timestamp=new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return timestamp;
	}
	
	public static Timestamp getTimestamp(long l) {
		Timestamp timestamp=null;
		try {
			timestamp = new Timestamp(l);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return timestamp;
	}
	
	public static Timestamp getCurrentTimeStamp() {
		Timestamp timestamp=null;
		try {
			timestamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timestamp;
	}
	
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
}
