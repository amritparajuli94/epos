/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import datamodel.Product;
import eposdb.EPOSDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UIPosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Button back;
    
    @FXML private TableView<Product> posProductPriceList;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Double> productPrice;
     
     @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Product> products = loadProductData();
        
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        
        posProductPriceList.getItems().setAll(products);
        // TODO
    }
    
    private ObservableList<Product> loadProductData(){
        
        ObservableList<Product> products = FXCollections.observableArrayList();
        
        EPOSDB eposdb = new EPOSDB();
        Connection db;
        try {
            db = eposdb.getDBConnection();

            String sql = "select * from tproduct order by id;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return products;
    }
    
}
