/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import datamodel.ProductQuantity;
import eposdb.EPOSDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class UIAdd_ProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button add;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private Button back;

    @FXML
    private ComboBox categoryList;

    private Connection db;
    EPOSDB eposdb = new EPOSDB();

    @FXML
    private void hanldeAddButtonAction(ActionEvent event) throws IOException, SQLException {

        //attributes
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());

        try {

            System.out.println("Adding a new product..");

            String addProductSQL = "insert into tProduct (name, price) values (?, ?)";
            PreparedStatement preparedStatement = db.prepareStatement(addProductSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Adding product failed");
            } else {
                System.out.println("Product " + name + " with price £" + price + " is added.");
            }
            try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    int id = generatedId.getInt(1);
                    System.out.println("Generated ID: " + id);
                    String addStockSQL = "insert into tStock (product_id, quantity) values (?, ?)";
                    PreparedStatement addStockStatement = db.prepareStatement(addStockSQL);
                    addStockStatement.setInt(1, id);
                    addStockStatement.setInt(2, 0);
                    addStockStatement.execute();

                    nameField.setText("");
                    priceField.setText("");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList categories = FXCollections.observableArrayList();
        try {
            db = eposdb.getDBConnection();
            String sql = "select id, name from tCategory;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //adding values to the right column on the db table.
                categories.add(new Category(rs.getInt("id"), rs.getString("name")).getName());
            }
            System.out.println("Got Categories");
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        categoryList.getItems().addAll(categories);
    }

    private class Category {

        private final int id;
        private final String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

    }

}
