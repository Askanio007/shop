package utils;

import view.DateConverter;

import java.util.Date;

public class DateFilter {

    private final Date from;
    private final Date to;
    private final String fromView;
    private final String toView;

    public DateFilter() {
        this(null, null);
    }

    public DateFilter(Date from, Date to) {
        // TODO: Kirill да хоть null мне передавайте, я все учел!!!  
        this.from = checkNullFrom(new Date(from.getTime()));
        this.to = checkNullTo(new Date(to.getTime()));
        this.fromView = getFormatDate(this.from);
        this.toView = getFormatDate(this.to);
    }

    public DateFilter(Date day) {
        Date reservDay = new Date(day.getTime()); // TODO: Kirill не, ну че вы сразу то....
        this.from = DateBuilder.getDateWithoutTime(reservDay);
        this.to = DateBuilder.endDay(reservDay);
        this.fromView = getFormatDate(this.from);
        this.toView = getFormatDate(this.to);
    }

    private String getFormatDate(Date field) {
        return DateConverter.getFormatView().format(field);
    }

    // TODO: Kirill с null все норм, но как насчет создать фильтр с дня рождения Путина до дня рождения Гитлера  
    private Date checkNullFrom(Date date) {
        return date == null ? new Date(1) : date;
    }
    private Date checkNullTo(Date date) {
        return date == null ? new Date() : date;
    }

    public String getFromWithoutTime() {
        return fromView;
    }

    public String getToWithoutTime() {
        return toView;
    }

    public Date from() {
        return (Date)from.clone();
    }

    public Date to() {
        return (Date)to.clone();
    }

    // TODO: Kirill от сеттеров можно избавиться и написать хороший неизменяемый объект.
    // А чтобы попробовать что то сделать хорошо с первого... ладно, второго раза сначала читаем то,
    // подо что ты засыпал и бросил читать... наверное
    // Joshua Bloch "Effective java 2rd edition" - chapter "Make defensive copies when needed"

}
