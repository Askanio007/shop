package models;

import entity.Sail;

import java.util.List;

public class SailProfit {

    private Sail sail;
    private List<ProductProfit> products;
    private Double profit = 0.0;

    public SailProfit(Sail sail, List<ProductProfit> products) {
        this.sail = sail;
        this.products = products;
        setProfit(products);
    }

    public void setProfit(List<ProductProfit> products) {
        Double profit = 0.0;
        for (ProductProfit prod : products) {
            profit += prod.getProfit();
        }
        this.profit = profit;
    }

    public String getProductListWithProfit()
    {
        String str = "";
        for (ProductProfit product : this.products) {
            str = str + product.getProduct().getName() + " " + product.getProfit()+ ";";
        }
        return str;
    }
    public Sail getSail() {
        return sail;
    }

    public List<ProductProfit> getProducts() {
        return products;
    }

    public Double getProfit() {
        return profit;
    }

    public String getViewProfit()
    {
        return String.format("%.2f", this.profit);
    }
}
