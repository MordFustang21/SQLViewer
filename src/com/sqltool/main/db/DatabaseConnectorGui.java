package com.sqltool.main.db;

import com.sqltool.main.gui.QueryTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * Created by dlaird on 12/17/14.
 */
public class DatabaseConnectorGui extends JFrame {

    private Connection connection = null;

    private JCheckBox useOdbc = new JCheckBox("ODBC");
    private JCheckBox useOracle = new JCheckBox("Oracle");

    private JPanel settingsPanel = new JPanel();
    private JLabel serverLabel = new JLabel("Server:");
    private JTextField serverField = new JTextField();

    private JLabel portLabel = new JLabel("Port:");
    private JTextField portField = new JTextField();

    private JLabel databaseLabel = new JLabel("Database:");
    private JTextField databaseField = new JTextField();

    private JLabel userNameLabel = new JLabel("Username:");
    private JTextField userNameField = new JTextField();

    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField();

    private JButton connect = new JButton("Connect");
    private JButton cancel = new JButton("Cancel");

    public DatabaseConnectorGui(final QueryTab queryTab) {

        //Create panel
        settingsPanel.setLayout(new GridLayout(7, 2));
        settingsPanel.add(useOdbc);
        settingsPanel.add(useOracle);
        settingsPanel.add(serverLabel);
        settingsPanel.add(serverField);
        settingsPanel.add(portLabel);
        settingsPanel.add(portField);
        settingsPanel.add(databaseLabel);
        settingsPanel.add(databaseField);
        settingsPanel.add(userNameLabel);
        settingsPanel.add(userNameField);
        settingsPanel.add(passwordLabel);
        settingsPanel.add(passwordField);

        //Buttons
        settingsPanel.add(cancel);
        settingsPanel.add(connect);

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String server = serverField.getText();
                String port = portField.getText();
                String database = databaseField.getText();
                String username = userNameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (useOdbc.isSelected()) {
                    connection = new DatabaseConnector().makeOdbcConnection(database, username, password);
                } else if (useOracle.isSelected()) {
                    connection = new DatabaseConnector().makeOracleConnector(server, port, database, username, password);
                } else {
                    connection = new DatabaseConnector().makeSqlConnection(server, port, database, username, password);
                }

                if (connection != null) {
                    queryTab.setConnection(connection);
                    queryTab.setDatabaseLabel(database);
                }

                dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        setContentPane(settingsPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("New Connection");

        setSize(300, 225);

        setLocationRelativeTo(null);

        setVisible(true);
    }
}
