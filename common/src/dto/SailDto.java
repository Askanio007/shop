package dto;

import entity.Sail;
import utils.StateSail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SailDto {

    private Long id;

    private Date date;

    private Date dateChangeState;

    private Integer amount;

    private BigDecimal totalsum;

    private StateSail state;

    private Integer cashbackPercent;

    private String buyerName;

    public SailDto() {}

    private SailDto(Sail sail) {
        this.id = sail.getId();
        this.date = sail.getDate();
        this.dateChangeState = sail.getDateChangeState();
        this.amount = sail.getAmount();
        this.totalsum = sail.getTotalsum().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.state = sail.getState();
        this.cashbackPercent = sail.getCashbackPercent();
        this.buyerName = sail.getBuyer().getName();
    }

    public static SailDto convertToDto(Sail sail) {
        return new SailDto(sail);
    }

    public static List<SailDto> convertToDto(List<Sail> sails) {
        List<SailDto> sailDtos = new ArrayList<>();
        sails.stream().forEach((p) -> sailDtos.add(convertToDto(p)));
        return sailDtos;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateChangeState() {
        return dateChangeState;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getTotalsum() {
        return totalsum;
    }

    public StateSail getState() {
        return state;
    }

    public Integer getCashbackPercent() {
        return cashbackPercent;
    }

    public String getBuyerName() {
        return buyerName;
    }
}
