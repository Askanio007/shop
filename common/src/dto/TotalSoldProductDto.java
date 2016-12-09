package dto;

import entity.TotalSoldProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TotalSoldProductDto {

    private long id;

    private long productId;

    private String productName;

    private BigDecimal totalCost;

    private Integer totalAmount;

    private TotalSoldProductDto() {}

    private TotalSoldProductDto(TotalSoldProduct entity) {
        this.id = entity.getId();
        this.productId = entity.getProduct().getId();
        this.productName = entity.getProduct().getName();
        this.totalCost = entity.getTotalCost();
        this.totalAmount = entity.getTotalAmount();
    }

    public static TotalSoldProductDto convertToDto(TotalSoldProduct entity) {
        return new TotalSoldProductDto(entity);
    }

    public static List<TotalSoldProductDto> convertToDto(List<TotalSoldProduct> entities) {
        List<TotalSoldProductDto> list = new ArrayList<>();
        entities.stream().forEach((p) -> list.add(convertToDto(p)));
        return list;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }
}
