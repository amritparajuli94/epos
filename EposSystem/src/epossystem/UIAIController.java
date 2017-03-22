/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

import ai.WeeklyReport;
import datamodel.Product;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UIAIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button back;
    
    @FXML
    private Label reportLabel;
    
    //action handler for Back Button to take back to main page
    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }
    
    @FXML
    private void handleMostSoldProdcutButtonAction(ActionEvent event) throws SQLException {
        WeeklyReport weeklyReport = new WeeklyReport();
        Product product = weeklyReport.mostProductSold();
        
        String text = "The most sold prodcut is " + product.getName() + ". \n";
        text = text + "This falls in " + product.getCategory() + ". \n";
        text = text + "We suggest adding more items that falls in " + product.getCategory() + " category.";
        System.out.println(text);
        reportLabel.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
