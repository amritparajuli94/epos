/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import javafx.scene.control.TextField;

/**
 *
 * @author Chandra
 */
public class ProductQuantity {
    
    int productId;
    String productName;
    int currentStock;
    TextField updateStock;

    public TextField getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(TextField updateStock) {
        this.updateStock = updateStock;
    }

    public ProductQuantity(int productId, String productName, int currentStock) {
        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.updateStock = new TextField();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
}
