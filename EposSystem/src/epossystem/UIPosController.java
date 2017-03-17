/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import datamodel.Product;
import datamodel.ProductQuantity;
import eposdb.EPOSDB;
import epossystem.controller.ProductButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UIPosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button back;

    @FXML
    private GridPane posPane;

    private Connection db;

    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EPOSDB eposdb = new EPOSDB();
        //Connection db;
        try {
            db = eposdb.getDBConnection();

            String sql = "select * from tproduct order by id;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            int row = 0;
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                
                Label name = new Label(productName);
                Label price = new Label(Double.toString(productPrice));
                ProductButton btn = new ProductButton(productId, productName, productPrice, "Add");
                posPane.add(name, 0, row);
                posPane.add(price, 1, row);
                posPane.add(btn, 2, row);
                row++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
