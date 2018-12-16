package com.example.jdbclearning;

import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class WebRowSetSample {

    private Connection con;
    private JdbcUtils settings;

    public WebRowSetSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
    }

    public void testWebRowSet() throws SQLException, IOException {
        WebRowSet priceList = null;
        int[] keyCols = {1};
        FileReader fReader = null;
        FileWriter fWriter = null;
        String priceListFileName = "pricelist.xml";

        try {
            RowSetFactory rsf = RowSetProvider.newFactory();
            priceList = rsf.createWebRowSet();

            priceList.setUrl(settings.getUrlString());
            priceList.setUsername(settings.getUsername());
            priceList.setPassword(settings.getPassword());
            priceList.setCommand("select cof_name, price from coffees");
            priceList.setKeyColumns(keyCols);

            // Populate the WebRowSet
            priceList.execute();
            System.out.println("Size of WebRowSet is: " + priceList.size());

            //Insert a new row
            priceList.moveToInsertRow();
            priceList.updateString("cof_name", "Kona");
            priceList.updateFloat("price", 8.99f);
            priceList.insertRow();
            priceList.moveToCurrentRow();
            System.out.println("New row inserted");
            System.out.println("Size of WebRowSet is: " + priceList.size());

            // Delete the row with "Espresso"
            priceList.beforeFirst();
            while (priceList.next()) {
                if (priceList.getString(1).equals("Espresso")) {
                    System.out.println("Deleting row with Expresso...");
                    priceList.deleteRow();
                    break;
                }
            }

            // Update price of Colombian
            priceList.beforeFirst();
            while (priceList.next()) {
                if (priceList.getString(1).equals("Colombian")) {
                    System.out.println("Updating row with Colombian...");
                    priceList.updateFloat(2, 6.99f);
                    priceList.updateRow();
                    break;
                }
            }

            int size1 = priceList.size();
            fWriter = new FileWriter(priceListFileName);
            priceList.writeXml(fWriter);
            fWriter.flush();
            fWriter.close();


            // Creating the receiving WebRowSet object
            WebRowSet receiver = rsf.createWebRowSet();
            receiver.setUrl(settings.getUrlString());
            receiver.setUsername(settings.getUsername());
            receiver.setPassword(settings.getPassword());

            // Now read the XML file
            fReader = new FileReader(priceListFileName);
            receiver.readXml(fReader);
            int size2 = receiver.size();
            if (size1 == size2) {
                System.out.println("WebRowSet serialized and deserialized properly");
            } else {
                System.out.println("Error...serializing/deserializing the WebRowSet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (fWriter != null) {
                fWriter.close();
            }
            if (fReader != null) {
                fReader.close();
            }
            if (priceList != null) {
                priceList.close();
            }
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = WebRowSetSample.class.getClassLoader()
                    .getResource("properties/mysql-sample-properties.xml")
                    .getPath();
        } else {
            propertiesFileName = args[0];
        }

        try {
            myJdbcUtils = new JdbcUtils(propertiesFileName);
        } catch (IOException e) {
            System.err.println("Problem reading properties file " + propertiesFileName);
            e.printStackTrace();
            return;
        }

        try {
            myConnection = myJdbcUtils.getConnection();

            WebRowSetSample myWebRowSetSample = new WebRowSetSample(myConnection, myJdbcUtils);
            myWebRowSetSample.testWebRowSet();
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }

    }

}
