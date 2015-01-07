package com.abttsupport.main.db;

import com.abttsupport.main.gui.SqlViewer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    //TODO: Get server info from pref file
    Connection connection;
    private JTextArea errorConsole;

    public DatabaseConnector() {

    }

    public Connection makeSqlConnection(String server, String port, String database, String username, String password) {
        errorConsole = SqlViewer.getInstance().getErrorConsole();
        String url = "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to db");
                errorConsole.append("Connected to Database\n");
                return connection;
            } catch (SQLException s) {
               errorConsole.append(s.toString() + "\n");
            }
        } catch (ClassNotFoundException e) {
            errorConsole.append(e.toString() + "\n");
        }
        return null;
    }

    public Connection makeOdbcConnection(String database, String username, String password) {
        errorConsole = SqlViewer.getInstance().getErrorConsole();
        String url = "jdbc:odbc:" + database;
        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url, username, password);
                errorConsole.append("Connected to db\n");
                return connection;
            } catch (SQLException s) {
                errorConsole.append(s.toString() + "\n");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception \n" + e);
        }
        return null;
    }

    public Connection getOracleConnector(String server, String port, String database, String username, String password) {
        errorConsole = SqlViewer.getInstance().getErrorConsole();
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to db");
                errorConsole.append("Connected to Database\n");
                return connection;
            } catch (SQLException s) {
                errorConsole.append(s.toString() + "\n");
            }
        } catch (ClassNotFoundException e) {
            errorConsole.append(e.toString() + "\n");
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}