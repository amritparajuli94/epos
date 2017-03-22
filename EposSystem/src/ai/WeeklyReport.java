/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import datamodel.Product;
import eposdb.EPOSDB;
import epossystem.UIProductController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandra
 */
public class WeeklyReport {
    
    HashMap<Integer, Integer> [] weeklySale;
    HashMap<Integer, Integer> totalProductSale;
    Connection db;
    public WeeklyReport() {
        this.weeklySale = new HashMap[7];
        this.totalProductSale = new HashMap<>();
        for (int i=0; i < 7; i++) {
            this.weeklySale[i] = new HashMap<Integer, Integer>();
        }
        init();
    }
    
    public void init() {
        String sql = "select * from tsale;";
        EPOSDB eposdb = new EPOSDB();
        try {
            this.db = eposdb.getDBConnection();
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int day = rs.getInt("day_of_week");
                int product_id = rs.getInt("product_id");
                HashMap<Integer, Integer> map = weeklySale[day];
                Integer currentTotal = map.get(product_id);
                if (currentTotal == null) {
                    map.put(product_id, 1);
                } else {
                    int newTotal = currentTotal++;
                    map.put(product_id, newTotal);
                }
                
                Integer currTotalSale = this.totalProductSale.get(product_id);
                if (currTotalSale == null) {
                    totalProductSale.put(product_id, 1);
                }else {
                    int newtotalSale = currTotalSale + 1;
                    totalProductSale.put(product_id, newtotalSale);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Product mostProductSold() throws SQLException {
        int mostSoldProduct = 0;
        for (int key: this.totalProductSale.keySet()) {
            int totalProductSold = this.totalProductSale.get(key);
            if (mostSoldProduct < totalProductSold) {
                mostSoldProduct = key;
            }
        }
        
        Product product = null;
        if (mostSoldProduct != 0) {
            String sql = "select p.id, p.name, p.price, c.name as category_name from tProduct p join tCategory c on p.category_id = c.id where p.id=?";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setInt(1, mostSoldProduct);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                product.setCategory(rs.getString("category_name"));
            }
        }
        return product;
    }
}
