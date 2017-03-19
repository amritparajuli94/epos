/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epossystem;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * 
 */
public class UIAdd_CategoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button back;

    @FXML
    private TextField categoryName;

    //action handler for Back Button to take back to main page
    @FXML
    private void hanldeBackButtonAction(ActionEvent event) throws IOException {

        Stage window = (Stage) back.getScene().getWindow();
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
        window.setScene(main);
        window.show();
    }

    //action handler for add button
    @FXML
    private void hanldeAddButtonAction(ActionEvent event) throws IOException{
        //setting a object named name and getting the value from categoryName
        String name = categoryName.getText();

        EPOSDB eposdb = new EPOSDB();
        Connection db;
        try {
            //running the connection
            db = eposdb.getDBConnection();
            System.out.println("Adding a new category..");

            ////setting a statement for a db to do.
            String addCategorySQL = "insert into tCategory (name) values (?)";
            PreparedStatement preparedStatement = db.prepareStatement(addCategorySQL);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            
            //once sucessfully added, Textfield is set empty again
            categoryName.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(UIAdd_CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
