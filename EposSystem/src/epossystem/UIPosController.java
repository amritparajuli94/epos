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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import static jdk.nashorn.internal.objects.NativeMath.round;

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

    @FXML
    private GridPane salePane;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Button saleButton;

    private Connection db;

    private double total_amount = 0;
    int row = 0;
    private ArrayList<Product> saleProducts = new ArrayList<Product>();

    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }

    private void handleProductButtonAction(ActionEvent event) {
        ProductButton btn = (ProductButton) event.getTarget();
        Product sale = new Product(btn.getProductId(), btn.getName(), btn.getPrice());
        saleProducts.add(sale);
        total_amount += sale.getPrice();

        Label saleRow = new Label(sale.getName() + ":       " + btn.getPrice());
        salePane.add(saleRow, 0, row);
        totalAmountLabel.setText(Double.toString(total_amount));
        row++;
    }

    @FXML
    private void handleSaleButtonAction(ActionEvent event) throws SQLException, IOException {
        System.out.println("Clicked");
        for (Product p : saleProducts) {
            String addSaleSQL = "insert into tSale (product_id, day_of_week, amount) values (?, ?, ?)";
            PreparedStatement preparedStatement = db.prepareStatement(addSaleSQL);
            preparedStatement.setInt(1, p.getId());
            preparedStatement.setInt(2, getDayOfWeekInNum());
            preparedStatement.setDouble(3, p.getPrice());
            preparedStatement.execute();
            System.out.println("Updated sale");
            
            String updateSql = "update tStock set quantity=quantity - 1 where product_id=?";
            PreparedStatement ps = db.prepareStatement(updateSql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            System.out.println("Updated stock");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EPOSDB eposdb = new EPOSDB();
        //Connection db;
        try {
            db = eposdb.getDBConnection();

            String sql = "select p.id, p.name, p.price, s.quantity from tproduct p join tstock s on p.id = s.product_id order by id;";
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            int row = 0;
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                int remainingQuantity = rs.getInt("quantity");
                ProductButton btn = new ProductButton(productId, productName, productPrice, remainingQuantity);
                posPane.add(btn, 0, row);
                row++;
                btn.setOnAction((event) -> handleProductButtonAction(event));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getDayOfWeekInNum() {
            Calendar cal = Calendar.getInstance();
            return cal.get(Calendar.DAY_OF_WEEK);
    }
}
