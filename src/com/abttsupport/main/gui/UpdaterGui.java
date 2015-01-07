package com.abttsupport.main.gui;

import com.abttsupport.main.cont.DesktopUpdater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdaterGui {
    DesktopUpdater desktopUpdater;
    private JPanel panel1;
    private JButton aboutTime20Button;
    private JButton DBUpgraderButton;
    private JButton reportsButton;
    private JButton allButton;
    private JButton restoreBackupButton;
    private JButton formsXpressButton;
    private JButton emailManagerButton;
    private JButton backupAllButton;
    private JButton restoreReportsButton;
    private JTextPane console;
    private JButton launchSqlTool;
    private JButton serviceCenterButton;
    private JButton checkErrorLogButton;
    private JTextField fileNameField;

    public UpdaterGui() {
        desktopUpdater = new DesktopUpdater(console);
        aboutTime20Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.getAbouttime20();
            }
        });

        DBUpgraderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.getDBUpgrader();
            }
        });

        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.getReports();
            }
        });

        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.updateAll();
            }
        });
        restoreBackupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.restoreAll();
            }
        });
        formsXpressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.getFormsxpress();
            }
        });
        emailManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.getEmailManager();
            }
        });
        backupAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.backupAll();
            }
        });
        restoreReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopUpdater.restoreReports();
            }
        });
        launchSqlTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        SqlViewer.getInstance();
                    }
                }.start();

            }
        });
        checkErrorLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        desktopUpdater.readFile(fileNameField.getText());
                    }
                }).start();
            }
            //End enclosure

        });
    }


    public void loadUpdaterUI() {
        JFrame frame = new JFrame("Update Utility");
        frame.setContentPane(new UpdaterGui().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

}
