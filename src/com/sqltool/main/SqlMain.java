package com.sqltool.main;


import com.sqltool.main.gui.SqlViewer;

import javax.swing.*;

/**
 * Created by dlaird on 12/30/14.
 */
public class SqlMain {

    public static void main(String[] args) {
        setUiManager();
        SqlViewer.getInstance();

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
