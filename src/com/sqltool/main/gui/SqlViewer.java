package com.sqltool.main.gui;

import com.sqltool.main.db.DatabaseConnector;
import com.sqltool.main.db.DatabaseDat;
import com.sqltool.main.db.DatabaseConnector;
import com.sqltool.main.db.DatabaseDat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;


public class SqlViewer {

    public static SqlViewer instance = null;
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextArea queryText;
    private JButton executeButton;
    private JTable resultTable;
    private JTextField serverField;
    private JTextField portField;
    private JTextField databaseField;
    private JTextField usernameField;
    private JCheckBox useODBCCheckBox;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel statusLabel;
    private JScrollPane scrollTable;
    private JTextArea errorConsole;
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private DatabaseDat databaseDat = DatabaseDat.getInstance();
    private JLabel tableStatistics;
    private JButton loadServerSettingsButton;
    private JPanel queryPanel;
    private Connection connection;
    private DefaultTableModel queryModel = null;

    public static SqlViewer getInstance() {
        if (instance == null) {
            System.err.println("Creating new instance");
            instance = new SqlViewer();
        }
        return instance;
    }

    public SqlViewer() {

        databaseDat.setErrorConsole(errorConsole);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createConnection();
            }
        });

        //Key listener for connection
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //Check for "ENTER" key
                if (e.getKeyCode() == 10) {
                    createConnection();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //Execute query button
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery();
            }
        });

        //Key listener for executing query
        queryText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //Check if F5 was pressed
                if (e.getKeyCode() == 116) {
                    executeQuery();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        //Add popup menu
        JPopupMenu queryPopup = new JPopupMenu();
        queryText.setComponentPopupMenu(queryPopup);

        //Backup table
        JMenuItem backupTableMenu = new JMenuItem("Backup Table");
        backupTableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Back up table
                String tableName = getTableName();
                String backupQuery = "select * into " + tableName + "Bak from " + tableName;
                System.err.println(backupQuery);

                databaseDat.executeUpdate(backupQuery);
            }
        });
        queryPopup.add(backupTableMenu);

        //Restore table
        JMenuItem restoreTableMenu = new JMenuItem("Restore Backup");
        restoreTableMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = getTableName();

                String dropTableQuery = "drop table " + tableName;
                String restoreTableQuery = " select * into " + tableName + " from " + tableName + "Bak";
                System.err.println(dropTableQuery + restoreTableQuery);

                databaseDat.executeUpdate(dropTableQuery + restoreTableQuery);
            }
        });
        queryPopup.add(restoreTableMenu);

        //Drop backuptable
        JMenuItem dropBackupMenu = new JMenuItem("Drop BackupTable");
        dropBackupMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dropBackupTable = "drop table " + getTableName() + "Bak";
                System.err.println(dropBackupTable);
                databaseDat.executeUpdate(dropBackupTable);
            }
        });
        queryPopup.add(dropBackupMenu);

        //New tab
        JMenuItem addTab = new JMenuItem("New Tab");
        addTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Add tab at the
                int tabIndex = tabbedPane1.getTabCount() - 1;
                tabbedPane1.add(new QueryTab().getQueryPanel(), "Query Tab" + (tabIndex), tabIndex);
            }
        });
        queryPopup.add(addTab);

        //Menu item to close tabs
        JPopupMenu closeMenu = new JPopupMenu();
        tabbedPane1.setComponentPopupMenu(closeMenu);
        JMenuItem closeTab = new JMenuItem("Close");
        closeTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabbedPane1.getSelectedIndex() != 0 && tabbedPane1.getSelectedIndex() != tabbedPane1.getTabCount() - 1) {
                    tabbedPane1.remove(tabbedPane1.getSelectedComponent());
                }
            }
        });
        closeMenu.add(closeTab);


        //Oracle right click option
        JPopupMenu connectOracle = new JPopupMenu();
        connectButton.setComponentPopupMenu(connectOracle);

        JMenuItem connectOracleItem = new JMenuItem("Connect to Oracle db");
        connectOracleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                databaseDat.setConnection(databaseConnector.getOracleConnector(serverField.getText(), portField.getText(), databaseField.getText(), usernameField.getText(), String.valueOf(passwordField.getPassword())));
            }
        });
        connectOracle.add(connectOracleItem);

        JFrame frame = new JFrame("SQLViewer");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(960, 540);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }


    public void executeQuery() {
        final ProgressBar progressBar = new ProgressBar();
        new Thread(new Runnable() {
            @Override
            public void run() {

                String query = getQueryText();
                //If is update execute update
                if (isUpdate(query)) {
                    //execute update
                    databaseDat.executeUpdate(query);
                } else {
                    //Set table model
                    resultTable.setModel(databaseDat.getTableModel(query));
                    resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    //Update statistics to show number of rows
                    updateStatistics();
                }
                progressBar.dispose();
            }
        }).start();

    }

    public Boolean isUpdate(String query) {
        //Check if its update or select
        if (query.contains("update") && query.contains("set")) {
            return true;
        } else if (query.contains("delete") && query.contains("from")) {
            return true;
        } else if (query.contains("select") && query.contains("into")) {
            return true;
        } else {
            return false;
        }
    }

    //Get table name from string
    public String getTableName() {
        String[] queryContent = getQueryText().split(" ");
        for (int i = 0; i < queryContent.length; i++) {
            if (queryContent[i].equals("from")) {
                i++;
                System.err.println("Table: " + queryContent[i]);
                return queryContent[i];
            }
        }
        return "Test";
    }

    //Find selected text or all
    public String getQueryText() {
        //Load the query
        String query = null;
        if (queryText.getSelectedText() == null) {
            query = queryText.getText();
        } else {
            query = queryText.getSelectedText();
        }

        return query;
    }

    //Check to see if backup exists so that it wont be overwritten
    public Boolean checkForBackup() {
        //TODO: Check to see if backup exists if not
        return false;
    }

    public void createConnection() {
        if (useODBCCheckBox.isSelected()) {
            errorConsole.append("Connecting using ODBC\n");
            databaseDat.setConnection(databaseConnector.makeOdbcConnection(databaseField.getText(), usernameField.getText(), String.valueOf(passwordField.getPassword())));
        } else {
            errorConsole.append("Connecting using SQLServer\n");
            databaseDat.setConnection(databaseConnector.makeSqlConnection(serverField.getText(), portField.getText(), databaseField.getText(), usernameField.getText(), String.valueOf(passwordField.getPassword())));
        }
    }

    public void updateStatistics() {
        tableStatistics.setText("Rows: " + resultTable.getRowCount() + " Columns: " + resultTable.getColumnCount());
    }

    public JTextArea getErrorConsole() {
        return errorConsole;
    }

}
