package com.abttsupport.main.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dlaird on 10/30/14.
 */
public class AtPrefLoader {
    private Properties prefs;
    private String server, port, database;
    private String path = "";
    private File atPrefs = new File("atpPrefs");
    private InputStream prefFileStream;

    public AtPrefLoader(){
       prefs = new Properties();
        loadPref();
    }

    public void loadPref(){
        //Load the variables
        String path;
        String locPath;
        if (new File("atpPrefsCC").exists()) {
            System.out.println("atpPrefsCC Exists");
            locPath = "atpPrefsCC";
        } else {
            System.out.println("Using User Pref");
            String homeDir = System.getProperty("user.home");
            path = homeDir + File.separator + ".abouttime";

            locPath = path + File.separator + atPrefs;
        }

        try {
            System.out.println(locPath);
            prefFileStream = new FileInputStream(locPath);
            prefs.load(prefFileStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServer(){
        return prefs.getProperty("server");
    }

    public String getPort(){
        return prefs.getProperty("port");
    }

    public String getDatabase(){
        return prefs.getProperty("dbName");
    }

    public void closeStream(){
        try {
            prefFileStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
