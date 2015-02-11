package com.sqltool.main.gui.tabcomponents;

import com.sqltool.main.db.DatabaseConnectorGui;
import com.sqltool.main.gui.QueryTab;
import com.sqltool.main.misc.QueryAnalyzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dlaird on 2/10/15.
 */
public class TabPopup extends JPopupMenu {
    private QueryTab queryTab;

    public TabPopup(QueryTab queryTab){
        this.queryTab = queryTab;
        setPopComponents();
    }

    public void setPopComponents(){
        //List Tables
        JMenuItem tableList = new JMenuItem("Get list of Tables");
        tableList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QueryAnalyzer.getListOfTables(queryTab.getQueryText());
            }
        });
        this.add(tableList);

        //New connection
        JMenuItem addTab = new JMenuItem("New Connection");
        addTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show server setting
                new DatabaseConnectorGui(queryTab);
            }
        });
        this.add(addTab);

        //Use main connection
        JMenuItem removeConnection = new JMenuItem("Remove Connection");
        removeConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryTab.setConnection(null);
                queryTab.setDatabaseLabel("Main Connection");
            }
        });
        this.add(removeConnection);
    }
}
