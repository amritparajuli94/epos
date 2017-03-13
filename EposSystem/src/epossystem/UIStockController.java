/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import datamodel.Product;
import datamodel.ProductQuantity;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UIStockController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button back;

    @FXML
    private TableView<ProductQuantity> productStockList;
    @FXML
    private TableColumn<ProductQuantity, String> productName;
    @FXML
    private TableColumn<ProductQuantity, Integer> currentStock;

    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<ProductQuantity> products = loadProductData();

        productName.setCellValueFactory(new PropertyValueFactory<ProductQuantity, String>("productName"));
        currentStock.setCellValueFactory(new PropertyValueFactory<ProductQuantity, Integer>("currentStock"));

        productStockList.getItems().setAll(products);
    }

    private ObservableList<ProductQuantity> loadProductData() {

        ObservableList<ProductQuantity> products = FXCollections.observableArrayList();

        EPOSDB eposdb = new EPOSDB();
        Connection db;
        try {
            db = eposdb.getDBConnection();

            String sql = "select p.id, p.name, s.quantity from tProduct p join tStock s on p.id = s.product_id order by p.id;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                products.add(new ProductQuantity(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity")));
            }
            System.out.println(products);
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

}
