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
        this.from = new Date(checkNullFrom(from).getTime());
        this.to = new Date(checkNullTo(to).getTime());
        this.fromView = formatView(this.from);
        this.toView = formatView(this.to);
    }

    public DateFilter(Date day) {
        // TODO: Kirill не, ну че вы сразу то....  
        this.from = new Date(DateBuilder.startDay(day).getTime());
        this.to = new Date(DateBuilder.endDay(day).getTime());
        // TODO: Artyom этот конструктор вызывается 1 раз и в дальнейшем объекту view поля не нужны. Решил, что можно делать так
        this.fromView = null;
        this.toView = null;
    }

    // TODO: Kirill с null все норм, но как насчет создать фильтр с дня рождения Путина до дня рождения Гитлера  ::: не очень понял, но сделал чтобы первый день текущего месяца отдавался
    private Date checkNullFrom(Date date) {
        return date == null ? DateBuilder.firstDayCurrentMonth() : date;
    }
    private Date checkNullTo(Date date) {
        return date == null ? new Date() : date;
    }

    private String formatView(Date date) {
        return DateConverter.getFormatView().format(date);
    }

    public String getFromWithoutTime() {
        return fromView;
    }

    public String getToWithoutTime() {
        return toView;
    }

    public Date getFrom() {
        return (Date)from.clone();
    }

    public Date getTo() {
        return (Date)to.clone();
    }

    // TODO: Kirill от сеттеров можно избавиться и написать хороший неизменяемый объект. ::: сделал как по книге) усёк, что нужно стараться изолиировать от изменений изменяемые объекты.
    // А чтобы попробовать что то сделать хорошо с первого... ладно, второго раза сначала читаем то,
    // подо что ты засыпал и бросил читать... наверное
    // Joshua Bloch "Effective java 2rd edition" - chapter "Make defensive copies when needed"

}
