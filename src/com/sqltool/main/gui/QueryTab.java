package com.sqltool.main.gui;

import com.sqltool.main.cont.TabController;
import com.sqltool.main.db.DatabaseConnectorGui;
import com.sqltool.main.misc.QueryAnalyzer;
import com.sqltool.main.db.DatabaseConnectorGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;

/**
 * Tab objects that are added to the main window
 */
public class QueryTab {
    private JButton executeButton;
    private JTextArea queryText;
    private JScrollPane scrollTable;
    public JTable resultTable;
    private JPanel queryPanel;
    private JLabel tableStatistics;
    private JLabel databaseLabel;

    private QueryTab instance = this;
    private TabController tabController = TabController.getInstance();
    private Connection connection = null;

    public JPanel getQueryPanel() {
        return queryPanel;
    }

    public QueryTab() {
        //Execute query button
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabController.executeQuery(getQueryText(), instance, connection);
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
                    tabController.executeQuery(getQueryText(), instance, connection);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //Add popup menu
        final JPopupMenu queryPopup = new JPopupMenu();
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

                //databaseDat.executeUpdate(backupQuery);
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

                //databaseDat.executeUpdate(dropTableQuery + restoreTableQuery);
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
                //queryController.executeUpdate(dropBackupTable);
            }
        });
        queryPopup.add(dropBackupMenu);

        //List Tables
        JMenuItem tableList = new JMenuItem("Get list of Tables");
        tableList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QueryAnalyzer.getListOfTables(queryText.getText());
            }
        });
        queryPopup.add(tableList);

        //New connection
        JMenuItem addTab = new JMenuItem("New Connection");
        addTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show server setting
                new DatabaseConnectorGui(instance);
            }
        });
        queryPopup.add(addTab);

        //Use main connection
        JMenuItem removeConnection = new JMenuItem("Remove Connection");
        removeConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection = null;
                setDatabaseLabel("Main Connection");
            }
        });
        queryPopup.add(removeConnection);
    }

    //Find selected text or all
    public String getQueryText() {
        //Load the query
        String query;
        if (queryText.getSelectedText() == null) {
            query = queryText.getText();
        } else {
            query = queryText.getSelectedText();
        }

        return query;
    }

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

    public void updateStatistics() {
        tableStatistics.setText("Rows: " + resultTable.getRowCount() + " Columns: " + resultTable.getColumnCount());
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setDatabaseLabel(String db) {
        databaseLabel.setText("Connected to: " + db);
    }

}
