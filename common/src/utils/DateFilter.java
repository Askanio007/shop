package utils;

import view.DateConverter;

import java.util.Date;

public class DateFilter {

    private Date from;
    private Date to;

    public DateFilter() {
        this.from = new Date(1);
        this.to = new Date();
    }

    public DateFilter(Date from, Date to) {
        this.from = checkNullFrom(from);
        this.to = checkNullTo(to);
    }

    public DateFilter(Date day) {
        this.from = DateBuilder.getDateWithoutTime(day);
        this.to = DateBuilder.endDay(day);
    }

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


}
