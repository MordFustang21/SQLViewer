package com.abttsupport.main.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by dlaird on 11/5/14.
 */
public class ProgressBar extends JFrame{


    public ProgressBar() {
        setTitle("Loading...");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container content = getContentPane();
        JProgressBar progressBar = new JProgressBar();
        Border border = BorderFactory.createTitledBorder("Loading...");
        progressBar.setIndeterminate(true);
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void hideProgress(){
        setVisible(false);
    }

    public void showProgress(){
        setVisible(true);
    }
}
