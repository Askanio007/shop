package models;


import entity.SoldProduct;
import view.ViewFormat;

import java.math.BigDecimal;

public class ProductProfit {

    private SoldProduct product;
    private BigDecimal profit;

    public ProductProfit(SoldProduct product, int cashbackPercent) {
        this.product = product;
        this.profit = product.getCost().multiply(BigDecimal.valueOf(product.getAmount().doubleValue() * cashbackPercent*1.0/100));
    }

    public SoldProduct getProduct() {
        return product;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public String getViewProfit()
    {
        return profit.toString();
    }
}
