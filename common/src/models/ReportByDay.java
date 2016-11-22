package models;

import view.DateConverter;
import view.ViewFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ReportByDay {
    private Date date;
    private Long clickLinkAmount;
    private Long enterCodeAmount;
    private Long sailAmount;
    private Long registrationAmount;
    private BigDecimal profit;
    private String viewDate;
    private String requestDate;

    public ReportByDay() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        viewDate = DateConverter.getFormatView().format(date);
        requestDate = DateConverter.getFormatRequest().format(date);
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

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getSailAmount() {
        return sailAmount;
    }

    public void setSailAmount(Long sailAmount) {
        this.sailAmount = sailAmount;
    }


    public String getDateView() {
        return viewDate;
    }

    public String getDateRequest() {
        return requestDate;
    }

    public String getViewProfit()
    {
        return profit.toString();
    }
}
