package com.example.jdbclearning;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRowSetSample {

    private String dbms;
    private String dbName;
    private Connection con;
    private JdbcUtils settings;

    public JdbcRowSetSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
        this.dbms = settings.getDbms();
        this.dbName = settings.getDbName();
    }

    public void testJdbcRowSet() throws SQLException {

        RowSetFactory rsf = RowSetProvider.newFactory();
        try (JdbcRowSet jdbcRs = rsf.createJdbcRowSet()) {
            jdbcRs.setUrl(settings.getUrlString());
            jdbcRs.setUsername(settings.getUsername());
            jdbcRs.setPassword(settings.getPassword());

            // 查询数据
            jdbcRs.setCommand("select * from coffees");
            jdbcRs.execute();
            while (jdbcRs.next()) {
                String coffeeName = jdbcRs.getString(1);
                int supplierID = jdbcRs.getInt(2);
                float price = jdbcRs.getFloat(3);
                int sales = jdbcRs.getInt(4);
                int total = jdbcRs.getInt(5);
                System.out.printf("%32s\t%d\t%10.2f\t%d\t%d%n",
                        coffeeName, supplierID, price, sales, total);
            }
            // setConcurrency 方法
            // 将此RowSet对象的并发性设置为给定的并发级别。此方法用于更改行集的并发级别，默认情况下为ResultSet.CONCUR_READ_ONLY
            jdbcRs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
            jdbcRs.setType(ResultSet.TYPE_SCROLL_SENSITIVE);
            // 更新数据
            jdbcRs.absolute(3);
            jdbcRs.updateFloat("price", 10.99f);
            jdbcRs.updateRow();

            System.out.println("\nAfter updating the third row:");
            CoffeesTable.viewTable(con);

            // 插入数据
            jdbcRs.moveToInsertRow();
            jdbcRs.updateString("COF_NAME", "HouseBlend");
            jdbcRs.updateInt("SUP_ID", 49);
            jdbcRs.updateFloat("PRICE", 7.99f);
            jdbcRs.updateInt("SALES", 0);
            jdbcRs.updateInt("TOTAL", 0);
            jdbcRs.insertRow();

            jdbcRs.moveToInsertRow();
            jdbcRs.updateString("COF_NAME", "HouseDecaf");
            jdbcRs.updateInt("SUP_ID", 49);
            jdbcRs.updateFloat("PRICE", 8.99f);
            jdbcRs.updateInt("SALES", 0);
            jdbcRs.updateInt("TOTAL", 0);
            jdbcRs.insertRow();

            System.out.println("\nAfter inserting two rows:");
            CoffeesTable.viewTable(con);

            jdbcRs.last();
            jdbcRs.deleteRow();

            System.out.println("\nAfter deleting last row:");
            CoffeesTable.viewTable(con);
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = JdbcRowSetSample.class.getClassLoader()
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

            JdbcRowSetSample jdbcRowSetSample = new JdbcRowSetSample(myConnection, myJdbcUtils);
            jdbcRowSetSample.testJdbcRowSet();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }

}
