package com.sqltool.main.gui;

import com.sqltool.main.cont.TabController;
import com.sqltool.main.db.DatabaseConnectorGui;
import com.sqltool.main.gui.tabcomponents.TabPopup;
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

        queryText.setComponentPopupMenu(new TabPopup(instance));
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
