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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chandra
 */
public class UILoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button login;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label wrongPasswordMsg;

    @FXML
    private void hanldeLoginButtonAction(ActionEvent event) throws IOException, SQLException {
        //attributes
        String username = usernameField.getText();
        String password = passwordField.getText();

        EPOSDB eposdb = new EPOSDB();
        Connection db;
        try {
            db = eposdb.getDBConnection();
            System.out.println(username);
            System.out.println(password);
            //statement for database to follow
            String sql = "select * from tuser where username='" + username + "' and password='" + password + "';";
            System.out.println(sql);
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery(sql);

//            if (!rs.next()) {
            if (false){
                System.out.println("here");
                wrongPasswordMsg.setText("You have entered invaild username or password.");
                
            //takes you to main window if you entered the right username and password
            } else {
                Stage window = (Stage) login.getScene().getWindow();
                //loading the page when login button is clicked.
                Scene main = new Scene(FXMLLoader.load(getClass().getResource("UIMain.fxml")));
                window.setScene(main);
                window.show();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
