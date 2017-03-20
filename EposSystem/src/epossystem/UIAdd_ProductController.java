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
import java.util.HashMap;
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

    HashMap<String, Integer> categoriesMap;

    @FXML
    private void hanldeAddButtonAction(ActionEvent event) throws IOException, SQLException {

        //attributes
        String name = nameField.getText();
        String priceInString = priceField.getText();
        String category = (String) categoryList.getValue();

        if ("".equals(name) || "".equals(priceInString) || "".equals(category)) {
            System.err.println("Invalid product name or product price");
        } else {
            double price = Double.parseDouble(priceInString);

            try {

                System.out.println("Adding a new product..");

                System.err.println(category);
                int category_id = categoriesMap.get(category);

                String addProductSQL = "insert into tProduct (name, price, category_id) values (?, ?, ?)";
                PreparedStatement preparedStatement = db.prepareStatement(addProductSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, category_id);
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
        ObservableList categoriesName = FXCollections.observableArrayList();
        categoriesMap = new HashMap<>();
        try {
            db = eposdb.getDBConnection();
            String sql = "select id, name from tCategory;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //adding values to the right column on the db table.
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categoriesName.add(name);
                categoriesMap.put(name, id);
            }
            System.out.println("Got Categories");
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        categoryList.getItems().addAll(categoriesName);
    }

}
