package com.example.jdbclearning;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JoinRowSetSample {

    private Connection con;
    private JdbcUtils settings;

    public JoinRowSetSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
    }

    public static void getCoffeesBoughtBySupplier(String supplierName, Connection con) throws SQLException {
        Statement stmt = null;
        String query = "select coffees.cof_name from coffees, suppliers "
                + "where suppliers.sup_name like '" + supplierName
                + "' and suppliers.sup_id = coffees.sup_id";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Coffees bought from " + supplierName + ": ");
            while (rs.next()) {
                System.out.println("     " + rs.getString(1));
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void testJoinRowSet(String supplierName) throws SQLException {
        CachedRowSet coffees = null;
        CachedRowSet suppliers = null;
        JoinRowSet jrs = null;

        try {
            RowSetFactory rsf = RowSetProvider.newFactory();

            coffees = rsf.createCachedRowSet();
            coffees.setUrl(settings.getUrlString());
            coffees.setUsername(settings.getUsername());
            coffees.setPassword(settings.getPassword());
            coffees.setCommand("select * from coffees");
            coffees.execute();

            suppliers = rsf.createCachedRowSet();
            suppliers.setUrl(settings.getUrlString());
            suppliers.setUsername(settings.getUsername());
            suppliers.setPassword(settings.getPassword());
            suppliers.setCommand("select * from suppliers");
            suppliers.execute();

            jrs = rsf.createJoinRowSet();
            jrs.addRowSet(coffees, "sup_id");
            jrs.addRowSet(suppliers, "sup_id");


            System.out.println("Coffees bought from " + supplierName + ": ");
            while (jrs.next()) {
                if (jrs.getString("sup_name").equals(supplierName)) {
                    System.out.println("     " + jrs.getString(1));
                }
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (jrs != null) {
                jrs.close();
            }
            if (suppliers != null) {
                suppliers.close();
            }
            if (coffees != null) {
                coffees.close();
            }
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = JoinRowSetSample.class.getClassLoader().getResource("properties/mysql-sample-properties.xml").getPath();
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

            System.out.println("\nCoffees bought by each supplier:");
            JoinRowSetSample.getCoffeesBoughtBySupplier("Acme, Inc.", myConnection);

            System.out.println("\nUsing JoinRowSet:");
            JoinRowSetSample myJoinRowSetSample = new JoinRowSetSample(myConnection, myJdbcUtils);
            myJoinRowSetSample.testJoinRowSet("Acme, Inc.");
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
