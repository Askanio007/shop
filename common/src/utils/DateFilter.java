package utils;

import view.DateConverter;

import java.util.Date;

public class DateFilter {

    private Date from;
    private Date to;

    public DateFilter() {
        this(null, null);
    }

    public DateFilter(Date from, Date to) {
        // TODO: Kirill да хоть null мне передавайте, я все учел!!!  
        this.from = checkNullFrom(from);
        this.to = checkNullTo(to);
    }

    public DateFilter(Date day) {
        // TODO: Kirill не, ну че вы сразу то....  
        this.from = DateBuilder.getDateWithoutTime(day);
        this.to = DateBuilder.endDay(day);
    }

    // TODO: Kirill с null все норм, но как насчет создать фильтр с дня рождения Путина до дня рождения Гитлера  
    private Date checkNullFrom(Date date) {
        return date == null ? new Date(1) : date;
    }
    private Date checkNullTo(Date date) {
        return date == null ? new Date() : date;
    }

    public String getFromWithoutTime() {
        return DateConverter.getFormatView().format(this.from);
    }

    public String getToWithoutTime() {
        return DateConverter.getFormatView().format(this.to);
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    // TODO: Kirill от сеттеров можно избавиться и написать хороший неизменяемый объект.
    // А чтобы попробовать что то сделать хорошо с первого... ладно, второго раза сначала читаем то,
    // подо что ты засыпал и бросил читать... наверное
    // Joshua Bloch "Effective java 2rd edition" - chapter "Make defensive copies when needed"

}
