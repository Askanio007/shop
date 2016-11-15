package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>();
    private static ThreadLocal<DateFormat> dfRequest = new ThreadLocal<DateFormat>();

    public static DateFormat getFormatView() {
        DateFormat format = df.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            df.set(format);
        }
        return format;
    }

    public static DateFormat getFormatRequest() {
        DateFormat format = dfRequest.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd");
            dfRequest.set(format);
        }
        return format;
    }

    public static Date endDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
