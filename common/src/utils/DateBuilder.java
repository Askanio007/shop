package utils;

import java.util.Calendar;
import java.util.Date;

public class DateBuilder {

    private static Calendar calendar = Calendar.getInstance();

    public static Date endDay(Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.setTime(getDateWithoutTime(calendar.getTime()));
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }// TODO: Kirill это метод "конец дня".. ::: отрефакторил
    // а это "дата без времени"...
    public static Date getDateWithoutTime(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static DateFilter getLastMonth() {        
        // TODO: 16.10.2016 вообще создаешь в методе календарь один и давай сним работать. ::: исправил
        // TODO: Kirill я плакаю, подумай хорошо и приходи расскажи, что ты сделал.  ::: не задано было время откуда высчитывать прошлый месяц
        DateFilter dateFilter = new DateFilter();
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, - 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateFilter.setFrom(calendar.getTime());
        calendar.setTime(today); // TODO: 16.10.2016 уже с датой ::: убрал setTime(new Date())
        calendar.add(Calendar.DATE, -1);
        dateFilter.setTo(calendar.getTime());
        return dateFilter;
    }
}
