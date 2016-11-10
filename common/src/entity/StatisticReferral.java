package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "click_statistic")
public class StatisticReferral {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "click_link_amount")
    private Integer clickLinkAmount;

    @Column(name = "registration_amount")
    private Integer regAmount;

    @Column(name = "enter_code_amount")
    private Integer enterCodeAmount;

    @Column(name = "sail_amount")
    private Integer sailAmount;

    @Column(name = "profit")
    private Double profit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @Column(name = "tracker")
    private String tracker;

    private StatisticReferral() {

    }

    private StatisticReferral(Builder builder) {
        this.date = builder.date;
        this.clickLinkAmount = builder.clickLinkAmount;
        this.sailAmount = builder.sailAmount;
        this.tracker = builder.tracker;
        this.enterCodeAmount = builder.enterCodeAmount;
        this.regAmount = builder.regAmount;
        this.buyer = builder.buyer;
        this.profit = builder.profit;

    }

    public static class Builder {
        private Date date;
        private Buyer buyer;
        private String tracker;

        private Integer clickLinkAmount = 0;
        private Integer regAmount = 0;
        private Integer enterCodeAmount = 0;
        private Integer sailAmount = 0;
        private Double profit = 0.0;

        public Builder(Date date, Buyer buyer, String tracker) {
            this.date = date;
            this.buyer = buyer;
            this.tracker = tracker;
        }

        public StatisticReferral.Builder clickLinkAmount(int clickLinkAmount){
            this.clickLinkAmount = clickLinkAmount;
            return this;
        }

        public StatisticReferral.Builder regAmount(int regAmount){
            this.regAmount = regAmount;
            return this;
        }

        public StatisticReferral.Builder enterCodeAmount(int enterCodeAmount){
            this.enterCodeAmount = enterCodeAmount;
            return this;
        }

        public StatisticReferral.Builder sailAmount(int sailAmount){
            this.sailAmount = sailAmount;
            return this;
        }

        public StatisticReferral.Builder profit(Double profit){
            this.profit = profit;
            return this;
        }

        public StatisticReferral build() {
            return new StatisticReferral(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public Integer getClickLinkAmount() {
        return clickLinkAmount;
    }

    public void setClickLinkAmount(Integer clickLinkAmount) {
        this.clickLinkAmount = clickLinkAmount;
    }

    public Integer getEnterCodeAmount() {
        return enterCodeAmount;
    }

    public void setEnterCodeAmount(Integer enterCodeAmount) {
        this.enterCodeAmount = enterCodeAmount;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Integer getSailAmount() {
        return sailAmount;
    }

    public void setSailAmount(Integer sailAmount) {
        this.sailAmount = sailAmount;
    }

    public Integer getRegAmount() {
        return regAmount;
    }

    public void setRegAmount(Integer regAmount) {
        this.regAmount = regAmount;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
