package models;


import entity.SoldProduct;
import view.ViewFormat;

// TODO: 16.10.2016 генерить сеттеры\геттеры на автомате не верно. надо делать это тогда, когда они нужны ::: я помню, мы об этом говорили. Я так и стараюсь делать
// в некоторых моделях и энтитях они используются только вьюшкой.
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
