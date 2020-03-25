package com.example.deliveryboy;

public class HistoryPojo {

public String shopname;
public  String delivery;
    public  String pickup;
    public  String product;


    public HistoryPojo(){
        this.shopname=shopname;
        this.delivery=delivery;
        this.pickup=pickup;
        this.product=product;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
