/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import epossystem.controller.*;

/**
 *
 * @author Chandra
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private ProductButton productButton;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productButton = new ProductButton();
        this.productButton.setName(name);
        this.productButton.setPrice(price);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductButton getProductButton() {
        return productButton;
    }

    public void setProductButton(ProductButton productButton) {
        this.productButton = productButton;
    }
}
