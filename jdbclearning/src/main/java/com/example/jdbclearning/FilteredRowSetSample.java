package com.example.jdbclearning;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FilteredRowSetSample {
    private Connection con;
    private JdbcUtils settings;

    public FilteredRowSetSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
    }

    public void testFilteredRowSet() throws SQLException {
        FilteredRowSet frs = null;
        StateFilter myStateFilter = new StateFilter(10000, 10999, 1);
        String[] cityArray = {"SF", "LA"};
        CityFilter myCityFilter = new CityFilter(cityArray, 2);

        try {
            RowSetFactory rsf = RowSetProvider.newFactory();
            frs = rsf.createFilteredRowSet();

            frs.setUrl(settings.getUrlString());
            frs.setUsername(settings.getUsername());
            frs.setPassword(settings.getPassword());
            frs.setCommand("select * from coffee_houses");
            frs.execute();

            System.out.println("\nBefore filter:");
            FilteredRowSetSample.viewTable(con);

            System.out.println("\nSetting state filter:");
            frs.beforeFirst();
            frs.setFilter(myStateFilter);
            viewFilteredRowSet(frs);

            System.out.println("\nSetting city filter:");
            frs.beforeFirst();
            frs.setFilter(myCityFilter);
            viewFilteredRowSet(frs);

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (frs != null) {
                frs.close();
            }
        }
    }

    public void viewFilteredRowSet(FilteredRowSet frs) throws SQLException {
        if (frs == null) {
            return;
        }
        while (frs.next()) {
            System.out.println(frs.getInt("store_id") + ", "
                    + frs.getString("city") + ", "
                    + frs.getInt("coffee") + ", "
                    + frs.getInt("merch") + ", "
                    + frs.getInt("total"));
        }
    }

    public static void viewTable(Connection con) {
        String query = "select * from coffee_houses";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getInt("store_id") + ", "
                        + rs.getString("city") + ", "
                        + rs.getInt("coffee") + ", "
                        + rs.getInt("merch") + ", "
                        + rs.getInt("total"));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = FilteredRowSetSample.class.getClassLoader()
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

            FilteredRowSetSample myFilteredRowSetSample = new FilteredRowSetSample(myConnection, myJdbcUtils);
            myFilteredRowSetSample.testFilteredRowSet();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
