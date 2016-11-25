package models;

import entity.Sail;
import entity.SoldProduct;
import view.ViewFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SailProfit {

    private final String strProducts;
    private final Sail sail;
    private final BigDecimal profit;

    public SailProfit(Sail sail, List<ProductProfit> products) {
        this.sail = sail;
        this.profit = calculateProfit(products);
        strProducts = buildListProductsToStr(products);

    }

    private BigDecimal calculateProfit(List<ProductProfit> products) {
        BigDecimal profitTemp = BigDecimal.ZERO;
        for (ProductProfit prod : products) {
            profitTemp.add(prod.getProfit());
        }
        return profitTemp;
    }

    private String buildListProductsToStr(List<ProductProfit> products) {
        StringBuilder str = new StringBuilder();
        // TODO: Kirill к сведению - такая еще штука есть String.join() и вообще для работы ::: А стоит ли использовать сторонние библотеки, если для моих целей хватает и этой?
        // со строками есть всякие либы типа apache StringUtils
        for (ProductProfit product : products) {
            str.append(product.getProduct().getName())
                    .append(" ")
                    .append(product.getProfit())
                    .append(";");
        }
        return str.toString();
    }

    public Sail getSail() {
        return sail;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public String getViewProfit()
    {
        return profit.toString();
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
