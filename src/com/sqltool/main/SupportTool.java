package com.abttsupport.main;

import com.abttsupport.main.cont.DesktopUpdater;
import com.abttsupport.main.gui.PortTesterGui;
import com.abttsupport.main.gui.SqlViewer;
import com.abttsupport.main.gui.UpdaterGui;
import com.abttsupport.main.misc.SSLTest;

import javax.swing.*;

/**
 * Created by dlaird on 12/30/14.
 */
public class SupportTool {

    public static void main(String[] args) {
        int length = args.length;

        //If no arguments load port tester
        if (length == 0) {
            boolean sqlOnly = true;
            if (sqlOnly) {
                setUiManager();
                SqlViewer.getInstance();
            } else {
                new PortTesterGui().loadGui();
            }

            //If arguments load updater
        } else {
            DesktopUpdater desktopUpdater = new DesktopUpdater(null);
            if (args[0].equals("-update")) {
                if (args.length > 1) {
                    if (args[1].equals("abouttime20.jar")) {
                        desktopUpdater.getAbouttime20();
                    } else if (args[1].equals("dbupgrader.jar")) {
                        desktopUpdater.getDBUpgrader();
                    } else if (args[1].equals("formsxpress.jar")) {
                        desktopUpdater.getFormsxpress();
                    } else if (args[1].equals("emailmanager.jar")) {
                        desktopUpdater.getEmailManager();
                    } else if (args[1].equals("reports")) {
                        desktopUpdater.getReports();
                    }
                } else {
                    desktopUpdater.updateAll();
                }
            } else if (args[0].equals("-backup")) {
                desktopUpdater.backupAll();
            } else if (args[0].equals("-restore")) {
                desktopUpdater.restoreAll();
            } else if (args[0].equals("-ssl")) {
                try {
                    SSLTest.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (args[0].equals("-gui")) {
                setUiManager();
                new UpdaterGui().loadUpdaterUI();
            } else if (args[0].equals("-sql")) {
                setUiManager();
                SqlViewer.getInstance();
            } else if (args[0].equals("-error")) {
                desktopUpdater.readFile(args[1]);
            } else {
                System.out.println("Please use a valid argument");
            }
        }
    }

    public static void setUiManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
