package com.example.jdbclearning;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.spi.SyncProviderException;
import javax.sql.rowset.spi.SyncResolver;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CachedRowSetSample {

    private String dbms;
    private String dbName;
    private Connection con;
    private JdbcUtils settings;

    public CachedRowSetSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
        this.dbms = settings.getDbms();
        this.dbName = settings.getDbName();
    }

    public void testPaging() throws SQLException {

        CachedRowSet crs = null;
        try {
            RowSetFactory rsf = RowSetProvider.newFactory();
            crs = rsf.createCachedRowSet();
            crs.setUsername(settings.getUsername());
            crs.setPassword(settings.getPassword());

            if (dbms.equals("mysql")) {
                crs.setUrl(settings.getUrlString() + "?relaxAutoCommit=true&useSSL=false");
            } else {
                crs.setUrl(settings.getUrlString());
            }

            crs.setCommand("select * from merch_inventory");

            // 设置分页大小，即每次从数据库查询出多少条记录
            crs.setPageSize(4);

            // 获取第一组值（第一页）
            crs.execute();

            // 添加监听器
            crs.addRowSetListener(new ExampleRowSetListener());

            // 继续查询
            int i = 1;
            do {
                System.out.println("Page number: " + i);
                while (crs.next()) {
                    System.out.println("Found item "
                            + crs.getInt("item_id") + ": "
                            + crs.getString("item_name"));
                    // 更新记录
                    if (crs.getInt("item_id") == 1235) {
                        int currentQuantity = crs.getInt("quan") + 1;
                        System.out.println("Updating quantity to " + currentQuantity);
                        crs.updateInt("quan", currentQuantity);
                        crs.updateRow();
                        // 写入数据库中
                        crs.acceptChanges();
                    }
                }
                i++;
            } while (crs.nextPage());

            // 插入记录
            // Inserting a new row
            // Doing a previous page to come back to the last page
            // as we ll be after the last page.
            int newItemId = 123456;
            if (doesItemIdExist(newItemId)) {
                System.out.println("Item ID " + newItemId + " already exists");
            } else {
                crs.previousPage();
                crs.moveToInsertRow();
                crs.updateInt("item_id", newItemId);
                crs.updateString("item_name", "TableCloth");
                crs.updateInt("sup_id", 927);
                crs.updateInt("quan", 14);
                Calendar timeStamp = new GregorianCalendar();
                timeStamp.set(2006, 4, 1);
                crs.updateTimestamp("date_val", new Timestamp(timeStamp.getTimeInMillis()));
                crs.insertRow();
                crs.moveToCurrentRow();

                System.out.println("About to add a new row...");
                crs.acceptChanges();
                System.out.println("Added a row...");
                viewTable(con);
            }
        }
        // 解决 CachedRowSet 和数据库中的数据的冲突
        catch (SyncProviderException spe) {

            SyncResolver resolver = spe.getSyncResolver();

            Object crsValue; // value in the RowSet object
            Object resolverValue; // value in the SyncResolver object
            Object resolvedValue; // value to be persisted

            while (resolver.nextConflict()) {
                if (resolver.getStatus() == SyncResolver.INSERT_ROW_CONFLICT) {
                    int row = resolver.getRow();
                    crs.absolute(row);

                    int colCount = crs.getMetaData().getColumnCount();
                    for (int i = 1; i < colCount; i++) {
                        // 只有有冲突的列值才不会是 null
                        if (resolver.getConflictValue(i) != null) {
                            crsValue = crs.getObject(i);
                            resolverValue = resolver.getConflictValue(i);

                            // Compare crsValue and resolverValue to determine
                            // which should be the resolved value (the value to persist)
                            //
                            // This example choses the value in the RowSet object,
                            // crsValue, to persist.,

                            resolvedValue = crsValue;

                            resolver.setResolvedValue(i, resolvedValue);
                        }

                    }
                }
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (crs != null) {
                crs.close();
            }
            con.setAutoCommit(true);
        }
    }

    public boolean doesItemIdExist(int id) {
        String query = "select item_id from merch_inventory where item_id =" + id;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return false;
    }

    public void viewTable(Connection con) {
        String query = "select * from merch_inventory";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Found item "
                        + rs.getInt("item_id") + ": "
                        + rs.getString("item_name") + " ("
                        + rs.getInt("quan") + ")");
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
            propertiesFileName = CachedRowSetSample.class.getClassLoader()
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

            CachedRowSetSample cachedRowSetSample = new CachedRowSetSample(myConnection, myJdbcUtils);
            cachedRowSetSample.viewTable(myConnection);
            cachedRowSetSample.testPaging();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
