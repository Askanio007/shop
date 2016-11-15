package view;

import entity.Sail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
}
