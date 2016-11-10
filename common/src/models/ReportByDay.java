package models;

import view.DateConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportByDay {

    // TODO: 16.10.2016 пора бы уже разобраться как это работает для чего нужно и так далее
    // TODO: классы, объекты, статические бла бла бла и прочяя неинтересная фигня    :::
    // я вынес отсюда все threadLocal, потому что у меня есть отдельный класс. DateConverter
    // Но я так понял, проблема быда в том, что переменные были не static и каждый раз создавались с классом

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
        return DateConverter.getFormatView();
    }

    public DateFormat getFormatRequest() {
        return DateConverter.getFormatRequest();
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
