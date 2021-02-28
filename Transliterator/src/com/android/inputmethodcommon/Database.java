package com.example.smtec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    public static List<String>  getInputTexts() {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "testuser";
        String password = "test623";

        String query = "SELECT Text FROM MyTextTable";

        List<String> txtsToBeReturned = new ArrayList<String>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
               // System.out.println();
                  txtsToBeReturned.add(rs.getString("Text"));
            }
        } catch (SQLException ex) {
            //Logger lgr = Logger.getLogger(JdbcMySQLVersion.class.getName());
           // lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return txtsToBeReturned;
    }
}