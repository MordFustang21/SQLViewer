package com.abttsupport.main.cont;

import com.abttsupport.main.gui.ProgressBar;
import com.abttsupport.main.gui.QueryTab;
import com.abttsupport.main.db.DatabaseDat;
import com.abttsupport.main.misc.QueryAnalyzer;

import javax.swing.*;
import java.sql.Connection;


/**
 * Used to pass data to and from the tabs
 */

public class TabController {
    public static TabController instance = null;

    DatabaseDat databaseDat = DatabaseDat.getInstance();

    public static TabController getInstance() {
        if (instance == null) {
            instance = new TabController();
        }
        return instance;
    }

    public void executeQuery(final String query, final QueryTab tab, final Connection connection) {
        final ProgressBar progressBar = new ProgressBar();
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Used for the connection of the main window
                if (connection == null) {
                    //If is update execute update
                    if (QueryAnalyzer.isUpdate(query)) {
                        //execute update
                        if (databaseDat.executeUpdate(query) == DatabaseDat.TRANSACTION_COMITTED) {
                            //Reload the table to see the changes
                            tab.resultTable.setModel(databaseDat.getTableModel("select * from " + QueryAnalyzer.getTableName(query)));
                        }
                    } else {
                        //Set table model
                        tab.resultTable.setModel(databaseDat.getTableModel(query));
                        tab.resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                        tab.updateStatistics();
                    }
                    progressBar.dispose();
                } else {
                    //Used if using tabs own connection
                    if (QueryAnalyzer.isUpdate(query)) {
                        //execute update
                        if (databaseDat.executeUpdate(query, connection) == DatabaseDat.TRANSACTION_COMITTED) {
                            //Reload the table to see the changes
                            tab.resultTable.setModel(databaseDat.getTableModel("select * from " + QueryAnalyzer.getTableName(query), connection));
                        }
                    } else {
                        //Set table model
                        tab.resultTable.setModel(databaseDat.getTableModel(query, connection));
                        tab.resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                        tab.updateStatistics();
                    }
                    progressBar.dispose();
                }
            }
        }).start();

    }

}
