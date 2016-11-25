package dto;

import entity.Buyer;
import entity.Sail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BuyerDTO {

    private long id;

    private String name;

    private String password;

    private boolean enable;

    private Date dateReg;

    private long refId;

    private String refCode;

    private String tracker;

    private BigDecimal balance;

    private int percentCashback;

    private Collection<SailDTO> sails;

    private Collection<StatisticReferralDTO> clicks;

    private BuyerInfoDTO info;

    private Collection<BuyerDTO> referrals;

    private BuyerDTO() {}


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setPercentCashback(int percentCashback) {
        this.percentCashback = percentCashback;
    }

    public void setSails(Collection<SailDTO> sails) {
        this.sails = sails;
    }

    public void setClicks(Collection<StatisticReferralDTO> clicks) {
        this.clicks = clicks;
    }

    public void setInfo(BuyerInfoDTO info) {
        this.info = info;
    }

    public void setReferrals(Collection<BuyerDTO> referrals) {
        this.referrals = referrals;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnable() {
        return enable;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public long getRefId() {
        return refId;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getTracker() {
        return tracker;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getPercentCashback() {
        return percentCashback;
    }

    public Collection<SailDTO> getSails() {
        return sails;
    }

    public Collection<StatisticReferralDTO> getClicks() {
        return clicks;
    }

    public BuyerInfoDTO getInfo() {
        return info;
    }

    public Collection<BuyerDTO> getReferrals() {
        return referrals;
    }
}
