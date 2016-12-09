package dto;

import entity.SoldProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SoldProductDto {

    private long id;

    private long productId;

    private String name;

    private Integer amount;

    private BigDecimal cost;

    private byte discount;

    private String buyerName;

    private SoldProductDto() {}

    private SoldProductDto(SoldProduct entity) {
        this.id = entity.getId();
        this.productId = entity.getProductId();
        this.name = entity.getName();
        this.amount = entity.getAmount();
        this.cost = entity.getCost();
        this.discount = entity.getDiscount();
        this.buyerName = entity.getSail().getBuyer().getName();
    }

    public static SoldProductDto convertToDto(SoldProduct entity) {
        return new SoldProductDto(entity);
    }

    public static List<SoldProductDto> convertToDto(List<SoldProduct> entities) {
        List<SoldProductDto> list = new ArrayList<>();
        entities.stream().forEach((p) -> list.add(convertToDto(p)));
        return list;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public byte getDiscount() {
        return discount;
    }

    public String getBuyerName() {
        return buyerName;
    }
}
