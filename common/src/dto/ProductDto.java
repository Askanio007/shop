package dto;

import entity.PictureProduct;
import entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private long id;

    private String name;

    private BigDecimal cost;

    private List<String> picPath;

    public ProductDto() {}

    private ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.cost = product.getCost();
        for(PictureProduct pic : product.getPicList()) {
            this.picPath.add(pic.getPath());
        }
    }

    public static ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }

    public static List<ProductDto> convertToDto(List<Product> products) {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(convertToDto(product));
        }
        return dtos;
    }

    public Product convertToEntity() {
        Product prod = new Product(this.name, this.cost);
        if (this.picPath != null) {
            List<PictureProduct> pics = new ArrayList<>();
            for (String path : this.picPath) {
                PictureProduct pic = new PictureProduct(path);
                pic.setProd(prod);
                pics.add(pic);
            }
            prod.setPicList(pics);
        }
        return prod;
    }

    public Product transferDataToEntity(Product entity) {
        entity.setName(this.name);
        entity.setCost(this.cost);
        List<PictureProduct> pics = new ArrayList<>();
        for (String path : this.picPath) {
            pics.add(new PictureProduct(path));
        }
        entity.setPicList(pics);
        return entity;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public List<String> getPicPath() {
        return picPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setPicPath(List<String> picPath) {
        this.picPath = picPath;
    }
}
