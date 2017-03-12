/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eposdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chandra
 */
public class EPOSDB {

    private Connection eposDBConnection = null;
    private static final String USERNAME = "amrit";
    private static final String PASSWORD = "PhewaTaal007";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/myepos";

    public Connection getDBConnection() throws SQLException {
        if (eposDBConnection == null) {

            try {
                Connection con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                System.err.println("Connected");
                eposDBConnection = con;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        return eposDBConnection;
    }
}
