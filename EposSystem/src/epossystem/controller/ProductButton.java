/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem.controller;

import javafx.scene.control.Button;

/**
 *
 * @author Chandra
 */
public class ProductButton extends Button {

    private String name = null;
    private double price = 0.0;
    private int productId = 0;
    private int remQuantity = 0;

    public ProductButton(int id, String name, double price, int remQuantity) {
        this.productId = id;
        this.name = name;
        this.price = price;
        this.remQuantity = remQuantity;
        setButtonDetails();
    }
    
    public void setButtonDetails() {
        String text = name + ": " + price;
        this.setText(text);
        this.setMinWidth(150);
        if (remQuantity == 0) {
            System.out.println(remQuantity);
            this.setDisable(true);
        }
    }

    public int getProductId() {
        return productId;
    }

    public void setId(int productId) {
        this.productId = productId;
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

}
