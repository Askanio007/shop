package models;


import entity.SoldProduct;

public class ProductProfit {

    private SoldProduct product;
    private Double profit;

    public ProductProfit(SoldProduct product, int cashbackPercent) {
        this.product = product;
        this.profit = product.getCost() * product.getAmount() * cashbackPercent/100;
    }

    public SoldProduct getProduct() {
        return product;
    }

    public void setProduct(SoldProduct product) {
        this.product = product;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getViewProfit()
    {
        return String.format("%.2f", this.profit);
    }
}
