package models;

import entity.Sail;
import entity.SoldProduct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: 16.10.2016 в классе есть серьезные ошибки :::
// метод setProfit был паблик, что могло повлечь за собой сторонее изменение профита, сделал private
// Определил переменную products, дабы избежать нуллПоинтерЭксепшн
public class SailProfit {

    private final String strProducts;
    private final Sail sail;
    private final Double profit;
    private final String viewProfit;

    public SailProfit(Sail sail, List<ProductProfit> products) {
        this.sail = sail;
        this.profit = calculateProfit(products);
        viewProfit = String.format("%.2f", this.profit);
        strProducts = buildListProductsToStr(products);

    }

    private Double calculateProfit(List<ProductProfit> products) {
        Double profitTemp = 0.0;
        for (ProductProfit prod : products) {
            profitTemp += prod.getProfit();
        }
        return profitTemp;
    }

    private String buildListProductsToStr(List<ProductProfit> products) {
        StringBuilder str = new StringBuilder();
        for (ProductProfit product : products) {
            str.append(product.getProduct().getName()).append(" ").append(product.getProfit()).append(";");
        }
        return str.toString();
    }

    public Sail getSail() {
        return sail;
    }

    public Double getProfit() {
        return profit;
    }

    public String getViewProfit()
    {
        return viewProfit;
    }

    public String getProducts()
    {
        return strProducts;
    }

    public static List<SailProfit> convertToSailProfit(Collection<Sail> sails) {
        List<SailProfit> sailProfit = new ArrayList<>();
        for (Sail sail : sails) {
            List<ProductProfit> productProfit = new ArrayList<>();
            for (SoldProduct product : sail.getProducts()) {
                productProfit.add(new ProductProfit(product, sail.getCashbackPercent()));
            }
            sailProfit.add(new SailProfit(sail, productProfit));
        }
        return sailProfit;
    }
}
