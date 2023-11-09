package com.example.bizbot;

import java.util.Date;

public class productData {
    private Integer id;
    private String product_id;
    private String product_name;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer stock;
    public  productData(Integer id, String product_id, String product_name,
                        Double price, String status, String image, Date date, Integer stock){
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
        this.stock = stock;
    }
    public productData(Integer id, String product_id, String product_name, Double price, String image){
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.image = image;
    }
    public Integer getId(){
        return id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getStock() {
        return stock;
    }
}
