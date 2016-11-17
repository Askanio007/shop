package models;


import entity.SoldProduct;
import view.ViewFormat;

public class ProductProfit {

    private SoldProduct product;
    private Double profit;
    private final String viewProfit;

    public ProductProfit(SoldProduct product, int cashbackPercent) {
        this.product = product;
        this.profit = product.getCost() * product.getAmount() * cashbackPercent*1.0/100;
        viewProfit = ViewFormat.money(this.profit);
    }

    public SoldProduct getProduct() {
        return product;
    }

    public Double getProfit() {
        return profit;
    }

    public String getViewProfit()
    {
        return viewProfit;
    }
}
