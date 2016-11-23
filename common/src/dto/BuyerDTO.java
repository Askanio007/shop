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

    private double balance;

    private int percentCashback;

    private Collection<SailDTO> sails;

    private Collection<StatisticReferralDTO> clicks;

    private BuyerInfoDTO info;

    private Collection<BuyerDTO> referrals;

    private BuyerDTO() {}

    public BuyerDTO(Buyer buyer) {
        this.id = buyer.getId();
        this.name = buyer.getName();
        this.password = buyer.getPassword();
        this.enable = buyer.getEnable();
        this.dateReg = buyer.getDateReg();
        this.refId = buyer.getRefId();
        this.refCode = buyer.getRefCode();
        this.tracker = buyer.getTracker();
        this.balance = buyer.getBalance();
        this.percentCashback = buyer.getPercentCashback();
    }
}
