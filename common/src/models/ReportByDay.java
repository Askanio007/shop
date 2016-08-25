package models;

import entity.ClickByLink;
import org.hibernate.annotations.Filter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportByDay {

    private ThreadLocal<DateFormat> dfView = new ThreadLocal<DateFormat>();
    private ThreadLocal<DateFormat> dfRequest = new ThreadLocal<DateFormat>();

    private Date date;
    private Long clickLinkAmount;
    private Long enterCodeAmount;
    private Long sailAmount;
    private Long registrationAmount;
    private Double profit;

    public ReportByDay() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getClickLinkAmount() {
        return clickLinkAmount;
    }

    public void setClickLinkAmount(Long clickLinkAmount) {
        this.clickLinkAmount = clickLinkAmount;
    }

    public Long getEnterCodeAmount() {
        return enterCodeAmount;
    }

    public void setEnterCodeAmount(Long enterCodeAmount) {
        this.enterCodeAmount = enterCodeAmount;
    }

    public Long getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(Long registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Long getSailAmount() {
        return sailAmount;
    }

    public void setSailAmount(Long sailAmount) {
        this.sailAmount = sailAmount;
    }

    public DateFormat getFormatView() {
        DateFormat format = dfView.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            dfView.set(format);
        }
        return format;
    }

    public DateFormat getFormatRequest() {
        DateFormat format = dfRequest.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd");
            dfRequest.set(format);
        }
        return format;
    }

    public String getDateView() {
        return getFormatView().format(date);
    }

    public String getDateRequest() {
        return getFormatRequest().format(date);
    }

    public String getViewProfit()
    {
        return String.format("%.2f", this.profit);
    }
}
