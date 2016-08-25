package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "click_statistic")
public class ClickByLink {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "click_link_amount")
    private Integer clickLinkAmount;

    @Column(name = "enter_code_amount")
    private Integer enterCodeAmount;

    @Column(name = "sail_amount")
    private Integer sailAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @Column(name = "tracker")
    private String tracker;

    public ClickByLink(Integer clickLinkAmount, Integer enterCodeAmount, Integer sailAmount, Date date, String tracker, Buyer buyer) {
        this.date = date;
        this.clickLinkAmount = clickLinkAmount;
        this.sailAmount = sailAmount;
        this.tracker = tracker;
        this.enterCodeAmount = enterCodeAmount;
        this.buyer = buyer;
    }

    public ClickByLink() {
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
}
