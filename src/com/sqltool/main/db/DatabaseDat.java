package com.abttsupport.main.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Created by dlaird on 11/5/14.
 */
public class DatabaseDat {

    private Connection connection;
    private JTextArea errorConsole;
    public static int TRANSACTION_COMITTED = 1;
    public static int TRANSACTION_ROLLEDBACK = 0;

    private static DatabaseDat instance = null;

    public static DatabaseDat getInstance() {
        if (instance == null) {
            instance = new DatabaseDat();
        }
        return instance;
    }


    public void setErrorConsole(JTextArea errorConsole) {
        this.errorConsole = errorConsole;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * This uses the connection assigned to the class *
     */
    public int executeUpdate(String query) {
        errorConsole.append(query + "\n");
        PreparedStatement updateStatement = null;

        try {
            connection.setAutoCommit(false);
            updateStatement = connection.prepareStatement(query);

            int rowsAffected = updateStatement.executeUpdate();

            //Confirm number of affected records
            int result = JOptionPane.showConfirmDialog(null, "Number of rows affected: " + rowsAffected + " Click YES to commit, NO to rollback", "Confirm Transaction", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                errorConsole.append("Transaction Committed\n");
                connection.commit();
                connection.setAutoCommit(true);
                return TRANSACTION_COMITTED;
            } else {
                errorConsole.append("Transaction rolled back\n");
                connection.rollback();
                connection.setAutoCommit(true);
                return TRANSACTION_ROLLEDBACK;
            }

        } catch (SQLException s) {
            //TODO: Create alert class
            JOptionPane.showMessageDialog(new JFrame(),
                    s, "Sql Error", JOptionPane.ERROR_MESSAGE);
        }
        return TRANSACTION_ROLLEDBACK;
    }

    /**
     * This uses the connection passed from he tab controller *
     */
    public int executeUpdate(String query, Connection connection) {
        errorConsole.append(query + "\n");
        PreparedStatement updateStatement = null;

        try {
            connection.setAutoCommit(false);
            updateStatement = connection.prepareStatement(query);

            int rowsAffected = updateStatement.executeUpdate();

            //Confirm number of affected records
            int result = JOptionPane.showConfirmDialog(null, "Number of rows affected: " + rowsAffected + " Click YES to commit, NO to rollback", "Confirm Transaction", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                errorConsole.append("Transaction Committed\n");
                connection.commit();
                connection.setAutoCommit(true);
                return TRANSACTION_COMITTED;
            } else {
                errorConsole.append("Transaction rolled back\n");
                connection.rollback();
                connection.setAutoCommit(true);
                return TRANSACTION_ROLLEDBACK;
            }

        } catch (SQLException s) {
            //TODO: Create alert class
            JOptionPane.showMessageDialog(new JFrame(),
                    s, "Sql Error", JOptionPane.ERROR_MESSAGE);
        }
        return TRANSACTION_ROLLEDBACK;
    }

    /**
     * This uses the connection assigned to the class *
     */
    public DefaultTableModel getTableModel(String query) {
        Statement getTime;

        try {
            getTime = connection.createStatement();
            errorConsole.append(query + "\n");
            ResultSet rs = getTime.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    //Check for null values and place null in table
                    Object object = rs.getObject(columnIndex);
                    if (object != null) {
                        vector.add(object);
                    } else {
                        vector.add("NULL");
                    }
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(new JFrame(),
                    s,
                    "Sql Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * This uses the connection passed from he tab controller *
     */
    public DefaultTableModel getTableModel(String query, Connection connection) {
        Statement getTime;

        try {
            getTime = connection.createStatement();
            errorConsole.append(query + "\n");
            ResultSet rs = getTime.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    //Check for null values and place null in table
                    if (rs.getObject(columnIndex) != null) {
                        vector.add(rs.getObject(columnIndex));
                    } else {
                        vector.add("NULL");
                    }
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(new JFrame(),
                    s,
                    "Sql Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
