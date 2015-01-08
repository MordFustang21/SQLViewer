package com.sqltool.main.misc;

import java.util.ArrayList;

/**
 * Created by dlaird on 12/30/14.
 */
public class QueryAnalyzer {

    /**
     * Checks to see if this is an update command
     *
     * @param query the text used in the SQL query
     * @return whether or not its an update statement
     */
    public static Boolean isUpdate(String query) {
        //Check if its update or select
        query = query.toLowerCase();
        if (query.contains("update") && query.contains("set")) {
            return true;
        } else if (query.contains("delete") && query.contains("from")) {
            return true;
        } else if (query.contains("select") && query.contains("into")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the table name from the query
     *
     * @param query the text used in the SQL query
     * @return the table inside query
     */
    public static String getTableName(String query) {
        query = query.toLowerCase();
        String[] queryContent = query.split(" ");
        for (int i = 0; i < queryContent.length; i++) {
            if (queryContent[i].equals("from")) {
                i++;
                System.err.println("Table: " + queryContent[i]);
                return queryContent[i];
            } else if (queryContent[i].equals("update")) {
                i++;
                System.err.println("Table: " + queryContent[i]);
                return queryContent[i];
            }
        }
        return "Test";
    }

    /**
     * Builds an array of tables from query
     *
     * @param query the text used in the SQL query
     * @return the array of tables in the query
     */
    public static String[] getListOfTables(String query) {

        query = query.toLowerCase();

        //Build array list
        ArrayList<String> tableList = new ArrayList<String>();
        int index = 0;
        String[] queryContent = query.split(" ");
        for (int i = 0; i < queryContent.length; i++) {
            if (queryContent[i].equals("from")) {
                i++;
                System.err.println("Table: " + queryContent[i]);

                //Add table to array and increment index
                tableList.add(index, queryContent[i]);
                index++;
            } else if (queryContent[i].equals("update")) {
                i++;
                System.err.println("Table: " + queryContent[i]);
                //Add table to array and increment index
                tableList.add(index, queryContent[i]);
                index++;
            }
        }

        //Convert arraylist to String array
        int resultSize = tableList.size();
        String[] tableNames = new String[resultSize];
        for (int i = 0; i < resultSize; i++) {
            System.out.println(tableList.get(i));
            tableNames[i] = tableList.get(i);
        }

        return tableNames;
    }
}
