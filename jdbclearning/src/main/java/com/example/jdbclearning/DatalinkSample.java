package com.example.jdbclearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

public class DatalinkSample {

    private Connection con;
    private JdbcUtils settings;

    public DatalinkSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
    }

    public static void viewTable(Connection con, Proxy proxy) {
        try (Statement stmt = con.createStatement()) {
            String query = "select document_name, url from data_repository";

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String documentName = rs.getString(1);
                System.out.println("Document name: " + documentName);

                URL url = rs.getURL(2);
                if (url != null) {
                    URLConnection urlConnection = url.openConnection(proxy);
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String pageContent;
                    while ((pageContent = reader.readLine()) != null) {
                        System.out.println(pageContent);
                    }
                } else {
                    System.out.println("URL is null");
                }
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addURLRow(String description, String url) {
        String sql = "insert into data_repository (document_name, url) values (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setURL(2, new URL(url));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = DatalinkSample.class.getClassLoader()
                    .getResource("properties/mysql-sample-properties.xml").getPath();
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

            DatalinkSample myDatalinkSample = new DatalinkSample(myConnection, myJdbcUtils);
            myDatalinkSample.addURLRow("Oracle", "https://www.oracle.com/index.html");

            DatalinkSample.viewTable(myConnection, Proxy.NO_PROXY);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }

}
