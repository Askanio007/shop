package models;

import entity.Buyer;
import entity.BuyerInfo;
import entity.Sail;
import entity.SoldProduct;
import view.ViewFormat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

public class Referral {

    private Long id;
    private String name;
    private Collection<Sail> sails;
    private BuyerInfo info;
    private String tracker;
    private BigDecimal profit;

    private String sailsToString;

    public Referral(Buyer b) {
        this.id = b.getId();
        this.name = b.getName();
        this.sails = Collections.unmodifiableCollection(b.getSails());
        this.info = b.getInfo();
        this.tracker = b.getTracker();
    }
    
    public Referral(Buyer b, BigDecimal profit) {
        this(b);
        this.profit = profit;
    }
    
    private Referral() {
    }

    public String sailsToString() {
        if(sailsToString == null) {
            StringBuilder str = new StringBuilder();
            for (Sail s : this.sails) {
                int i = 0;
                str.append("Date: ")
                        .append(s.getDate().toString());
                for (SoldProduct sp : s.getProducts()) {
                    str.append(++i)// TODO: Kirill просто вариант ::: принял к сведению. Разобрался когда инкремент в префиксе и постфиксе
                            .append(") Name: ")
                            .append(sp.getName())
                            .append(";")
                            .append(" cost: ")
                            .append(sp.getCost())
                            .append(";")
                            .append(" amount: ")
                            .append(sp.getAmount())
                            .append(";")
                            .append(" discount: ")
                            .append(sp.getDiscount())
                            .append("; ");
                    i++;
                }
            }
            sailsToString = str.toString();
        }
        return sailsToString;
    }

    public long getCountSails() {
        return this.sails.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Sail> getSails() {
        return sails;
    }

    public BuyerInfo getInfo() {
        return info;
    }

    public void setInfo(BuyerInfo info) {
        this.info = info;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public String getViewProfit()
    {
        return profit.toString();
    }
}
