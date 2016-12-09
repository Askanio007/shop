package dto;

import entity.Discount;

import java.util.ArrayList;
import java.util.List;

public class DiscountDto {

    private long id;

    private String nameBuyer;

    private boolean active;

    private byte dscnt;

    private long productId;

    public DiscountDto() {}

    private DiscountDto(Discount discount) {
        this.id = discount.getId();
        this.active = discount.getActive();
        this.dscnt = discount.getDiscount();
        this.productId = discount.getProductId();
        if (discount.getBuyer() != null)
            this.nameBuyer = discount.getBuyer().getName();
    }

    public static DiscountDto convertToDto(Discount discount) {
        return new DiscountDto(discount);
    }

    public static List<DiscountDto> convertToDto(List<Discount> discounts) {
        List<DiscountDto> list = new ArrayList<>();
        discounts.stream().forEach((p) -> list.add(convertToDto(p)));
        return list;
    }

    public long getId() {
        return id;
    }

    public String getNameBuyer() {
        return nameBuyer;
    }

    public void setNameBuyer(String nameBuyer) {
        this.nameBuyer = nameBuyer;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public byte getDiscount() {
        return dscnt;
    }

    public void setDiscount(byte dscnt) {
        this.dscnt = dscnt;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
