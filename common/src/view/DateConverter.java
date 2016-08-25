package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateConverter {

    private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>();

    public static DateFormat getFormatView() {
        DateFormat format = df.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            df.set(format);
        }
        return format;
    }
}
