package models;

import entity.Buyer;
import entity.BuyerInfo;
import entity.Sail;
import entity.SoldProduct;
import view.ViewFormat;

import java.util.Collection;

public class Referral {

    private Long id;
    private String name;
    private Collection<Sail> sails;
    private BuyerInfo info;
    private String tracker;
    private Double profit;
    private String viewProfit;

    public Referral(Buyer b) {
        this.id = b.getId();
        this.name = b.getName();
        this.sails = b.getSails();
        this.info = b.getInfo();
        this.tracker = b.getTracker();
    }
    
    public Referral(Buyer b, Double profit) {
        this(b);
        this.profit = profit;
        viewProfit = ViewFormat.money(profit);
    }
    
    private Referral() {
    }

    public String sailsToString() {
        String sailStr = "";
        for (Sail s : this.sails) {
            int i = 0;
            sailStr = "Date: " + s.getDate().toString();
            for (SoldProduct sp : s.getProducts()) {
                sailStr += (i+1) + ") Name: "+sp.getName() + "; cost: " +sp.getCost()+ "; amount: " + sp.getAmount() + "; discount: " + sp.getDiscount()+"; ";
                i++;
            }
        }
        return sailStr;
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

    public void setSails(Collection<Sail> sails) {
        this.sails = sails;
    }

    public BuyerInfo getInfo() {
        return info;
    }

    public void setInfo(BuyerInfo info) {
        this.info = info;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
        viewProfit = ViewFormat.money(profit);
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public String getViewProfit()
    {
        return viewProfit;
    }
}
