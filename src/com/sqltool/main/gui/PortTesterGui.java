package com.abttsupport.main.gui;

import com.abttsupport.main.cont.PortTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dlaird on 7/18/14.
 */
public class PortTesterGui {
    private JTextField ip_address;
    private JTextField port;
    private JButton checkPortButton;
    private JPanel rootPanel;
    private JLabel statusLabel;
    private JTextArea console;

    public PortTesterGui() {
        //Button to check the port
        checkPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create array and test the port
                List<String> items = Arrays.asList(port.getText().split("\\s*,\\s*"));
                console.append("Testing: " + ip_address.getText().toString() + " On port: " + port.getText().toString() + "\n");

                //Used to test one value
                if (items.size() == 0) {
                    String port_status = new PortTest().availablePort(ip_address.getText().toString(), Integer.parseInt(items.get(0)));
                    statusLabel.setText(port_status);
                    console.append("Status:" + port_status + "\n");
                } else {
                    //used to test a comma delimited array of values
                    for (int i = 0; i < items.size(); i++) {
                        String port_status = new PortTest().availablePort(ip_address.getText().toString(), Integer.parseInt(items.get(i)));
                        statusLabel.setText(port_status);
                        console.append("Status:" + port_status + "\n");
                    }
                }
            }
        });

    }

    public void loadGui() {

        //Set look and feel to system default

        //Load gui components
        JFrame frame = new JFrame("Desktop Port Tester");
        frame.setContentPane(new PortTesterGui().rootPanel);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 360);
        frame.setVisible(true);


    }

}
