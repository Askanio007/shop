package utils;

import java.util.Calendar;
import java.util.Date;

public class DateBuilder {

    private DateBuilder() {}

    private static Calendar calendar;

    static {
        calendar = Calendar.getInstance();
    }
    public static Date endDay(Date date) {

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.setTime(startDay(calendar.getTime()));
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }// TODO: Kirill это метод "конец дня".. ::: убрал лишнее

    // а это "дата без времени"...
    public static Date startDay(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date firstDayCurrentMonth() {
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static DateFilter getLastMonth() {        
        // TODO: 16.10.2016 вообще создаешь в методе календарь один и давай сним работать. ::: исправил
        // TODO: Kirill я плакаю, подумай хорошо и приходи расскажи, что ты сделал.
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date from = new Date(calendar.getTime().getTime());

        calendar.setTime(new Date()); // TODO: 16.10.2016 уже с датой ::: убрал setTime(new Date())
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date to =  new Date(calendar.getTime().getTime());
        return new DateFilter(from, to);
    }
}
