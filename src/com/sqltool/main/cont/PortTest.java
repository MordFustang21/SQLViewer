package com.abttsupport.main.cont;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by dlaird on 4/29/14.
 */
public class PortTest{
    String status;
    int Timeout = 1000;

        public String availablePort(String ip_addr, int add_port){

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip_addr, add_port), Timeout);
                socket.close();
                status = add_port + " is Open";
            } catch (ConnectException e) {

                status = "Can't Connect to " + add_port;
            } catch (Exception ex) {

                status = "Can't Connect to " + add_port;
            }

            return status;
    }
}
