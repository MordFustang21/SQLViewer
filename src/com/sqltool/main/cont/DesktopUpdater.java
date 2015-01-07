package com.abttsupport.main.cont;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Derrick Laird on 8/8/14.
 */
public class DesktopUpdater extends Thread {
    public static final int BUFFER = 2048;

    //File Names
    String emailmanager = "emailmanager.jar";
    String formsxpress = "formsxpress.jar";
    String abouttime20 = "abouttime20.jar";
    String dbupgrader = "dbupgrader.jar";
    JTextPane console;

    public DesktopUpdater(JTextPane inConsole) {
        this.console = inConsole;
    }


    public void backupReports(File folder, int type) {

        File reportsfoldbak = new File("reports_bak");
        if (reportsfoldbak.exists()) {
            printConsole("Deleting old reports folder");
            reportsfoldbak.delete();
        }
        if (type == 1) {
            folder.renameTo(reportsfoldbak);
        } else {
            try {
                FileUtils.copyDirectory(new File("reports"), new File("reports_bak"));
            } catch (IOException i) {
                printConsole("" + i);
            }
        }
    }

    public void createBackup(String filename, int backuptype) {
        if (backuptype == 1) {
            File oldFile = new File(filename);
            File backupFile = new File(filename + ".bak");

            printConsole("Creating Backup of " + filename);
            if (backupFile.exists()) {
                printConsole("Backup file exists deleting");
                backupFile.delete();
            }
            oldFile.renameTo(backupFile);
            printConsole(filename + " Backup Successful");
        } else {
            File oldFile = new File(filename);
            File backupFile = new File(filename + ".bak");
            try {
                if (backupFile.exists()) {
                    printConsole("Backup file exists deleting");
                    backupFile.delete();
                }
                FileUtils.copyFile(oldFile, backupFile);
            } catch (IOException i) {
                printConsole("" + i);
            }
        }
    }

    public void backupAll() {
        createBackup(abouttime20, 2);
        createBackup(emailmanager, 2);
        createBackup(dbupgrader, 2);
        createBackup(formsxpress, 2);
        backupReports(new File("reports"), 2);
    }

    public void restoreAll() {
        restoreAbouttime20();
        restoreDbupgrader();
        restoreEmailManager();
        restoreFormsxpress();
        restoreReports();
    }

    public void updateAll() {
        getAbouttime20();
        getDBUpgrader();
        getEmailManager();
        getFormsxpress();
        getReports();

    }

    public void restoreBackup(String filename) {
        File backupFile = new File(filename + ".bak");
        File oldFile = new File(filename);

        printConsole("Deleting old " + filename);
        oldFile.delete();

        printConsole("Restoring Backup of " + filename);
        backupFile.renameTo(oldFile);
        printConsole("Restore Successful");

    }

    public void getAbouttime20() {
        createBackup("abouttime20.jar", 1);
        File abtt20 = new File("abouttime20.jar");
        URL abtt20url = null;
        try {
            printConsole("assigning url");
            abtt20url = new URL("http://www.abttdrop.com/qa/source/jar/abouttime20.jar");
            printConsole("Downloading abouttime20");
            FileUtils.copyURLToFile(abtt20url, abtt20);
            printConsole("File downloaded \n");
        } catch (IOException i) {
            printConsole("" + i);
        }

    }

    public void getDBUpgrader() {
        createBackup("dbupgrader.jar", 1);
        File abtt20 = new File("dbupgrader.jar");
        URL abtt20url = null;
        try {
            printConsole("assigning url");
            abtt20url = new URL("http://www.abttdrop.com/qa/source/jar/dbupgrader.jar");
            printConsole("Downloading dbupgrader");
            FileUtils.copyURLToFile(abtt20url, abtt20);
            printConsole("File downloaded");
        } catch (IOException i) {
            printConsole("" + i);
        }
    }

    public void getFormsxpress() {
        createBackup("formsxpress.jar", 1);
        File formsfile = new File("formsxpress.jar");
        URL formsurl = null;
        try {
            printConsole("assigning url");
            formsurl = new URL("http://www.abttdrop.com/qa/source/jar/formsxpress.jar");
            printConsole("Downloading dbupgrader");
            FileUtils.copyURLToFile(formsurl, formsfile);
            printConsole("File downloaded");
        } catch (IOException i) {
            printConsole("" + i);
        }
    }

    public void getEmailManager() {
        createBackup(emailmanager, 1);
        File emailman = new File(emailmanager);
        URL emailmanurl = null;
        try {
            printConsole("assigning url");
            emailmanurl = new URL("http://www.abttdrop.com/qa/source/jar/emailmanager.jar");
            printConsole("Downloading dbupgrader");
            FileUtils.copyURLToFile(emailmanurl, emailman);
            printConsole("File downloaded");
        } catch (IOException i) {
            printConsole("" + i);
        }
    }

    public void getReports() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                File reports = new File("reports.zip");
                try {
                    //Download
                    printConsole("Generating URL for Reports");
                    URL abttReports = new URL("http://www.abttdrop.com/qa/source/reports/reports.zip");
                    printConsole("Downloading File");
                    FileUtils.copyURLToFile(abttReports, reports);
                    printConsole("File downloaded");

                    printConsole("Deleting old reports");
                    backupReports(new File("reports"), 1);

                    String zipFile = "reports.zip";
                    String outFolder = "reports";

                    BufferedOutputStream dest = null;
                    BufferedInputStream is = null;
                    ZipEntry zipEntry;
                    ZipFile unZipper = new ZipFile(zipFile);
                    Enumeration e = unZipper.entries();

                    printConsole("Making reports directory");
                    File reportDir = new File("reports");
                    reportDir.mkdir();
                    while (e.hasMoreElements()) {
                        zipEntry = (ZipEntry) e.nextElement();

                        printConsole("Extracting: " + zipEntry);
                        is = new BufferedInputStream(unZipper.getInputStream(zipEntry));
                        int count;
                        byte data[] = new byte[BUFFER];
                        FileOutputStream fos = new FileOutputStream(zipEntry.getName());
                        dest = new BufferedOutputStream(fos, BUFFER);
                        while ((count = is.read(data, 0, BUFFER)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                        is.close();
                    }
                    printConsole("Reports Done");
                } catch (IOException i) {
                    printConsole("" + i);
                }
            }
        }).start();
    }

    public void restoreReports() {
        printConsole("Restoring old");
        File reportsBak = new File("reports_bak");
        File reportsOld = new File("reports");

        printConsole("Deleting old reports");
        try {
            FileUtils.deleteDirectory(reportsOld);
            printConsole("Renaming report backup");

            printConsole("Renaming old reports Folder");
            reportsBak.renameTo(reportsOld);
        } catch (IOException i) {
            printConsole("" + i);
        }
    }

    public void restoreAbouttime20() {
        restoreBackup("abouttime20.jar");
    }

    public void restoreDbupgrader() {
        restoreBackup("dbupgrader.jar");
    }

    public void restoreEmailManager() {
        restoreBackup("emailmanager.jar");
    }

    public void restoreFormsxpress() {
        restoreBackup("formsxpress.jar");
    }

    public void readFile(String filename) {
        printConsole("<------------------ Reading " + filename + " ------------------>");

        try {
            BufferedReader errorReader = new BufferedReader(new FileReader(filename));

            String currentLine;
            while ((currentLine = errorReader.readLine()) != null) {
                printConsole(currentLine);
            }
        } catch (IOException i) {
            printConsole("" + i);
            i.printStackTrace();
        }
        printConsole("<------------------ End of " + filename + " ------------------>");
    }

    public void printConsole(String in) {

        if (console != null) {
            StyledDocument styledDocument = console.getStyledDocument();
            StyleContext styleContext = StyleContext.getDefaultStyleContext();
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            AttributeSet redText = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.red);
            try {
                if (in.contains("sql")) {
                    styledDocument.insertString(styledDocument.getLength(), in + "\n", redText);
                } else {
                    styledDocument.insertString(styledDocument.getLength(), in + "\n", null);
                }
            } catch (BadLocationException b) {
                b.printStackTrace();
            }
        } else {
            System.out.println(in);
        }
    }
}
