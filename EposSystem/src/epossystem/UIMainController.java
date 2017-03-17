/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UIMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button products;

    @FXML
    private Button addProduct;

    @FXML
    private Button addCategory;

    @FXML
    private Button stock;

    @FXML
    private Button pos;

    @FXML
    private void hanldeProductsButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) products.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIProduct.fxml")));
        window.setScene(main);
        window.show();

    }
    @FXML
    private void hanldeAddProductButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) addProduct.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIAdd_Product.fxml")));
        window.setScene(main);
        window.show();

    }
    @FXML
    private void hanldeSettingButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) addCategory.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIAdd_Category.fxml")));
        window.setScene(main);
        window.show();

    }
    
    @FXML
    private void hanldeStockButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) stock.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIStock.fxml")));
        window.setScene(main);
        window.show();

    }
    @FXML
    private void hanldePoSButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) pos.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIPos.fxml")));
        window.setScene(main);
        window.show();

    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
