package com.example.jdbclearning;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CoffeesTable {
    private Connection con;
    private String dbms;

    public CoffeesTable(Connection con, String dbms) {
        this.con = con;
        this.dbms = dbms;
    }


    public void modifyPricesByPercentage(String coffeeName, float priceModifier, float maximumPrice) throws SQLException {
        con.setAutoCommit(false);

        Statement getPrice = null;
        Statement updatePrice = null;
        String query = "select cof_name, price from coffees where cof_name ='" + coffeeName + "'";

        try {
            Savepoint save1 = con.setSavepoint();
            getPrice = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            updatePrice = con.createStatement();

            if (!getPrice.execute(query)) {
                System.out.println("Could not find entry for coffee named " + coffeeName);
            } else {
                ResultSet rs = getPrice.getResultSet();
                rs.first();
                float oldPrice = rs.getFloat("price");
                float newPrice = oldPrice * (1 + priceModifier);
                System.out.println("Old price of " + coffeeName + " is " + oldPrice);
                System.out.println("New price of " + coffeeName + " is " + newPrice);
                System.out.println("Performing updating...");
                updatePrice.executeUpdate("update coffees set price = " + newPrice
                        + " where cof_name ='" + coffeeName + "'");
                System.out.println("\ncoffees table after update:");
                CoffeesTable.viewTable(con);

                if (newPrice > maximumPrice) {
                    System.out.println("\nThe new price, " + newPrice
                            + ", is greater than the maximum price, " + maximumPrice
                            + ". Rolling back the transaction...");
                    con.rollback(save1); // 回滚到指定 savepoint
                    System.out.println("\ncoffees table after rollback:");
                    CoffeesTable.viewTable(con);
                }
                con.commit();
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (getPrice != null) {
                getPrice.close();
            }
            if (updatePrice != null) {
                updatePrice.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
        }
    }

    public void modifyPrices(float percentage) {
        /*
        您无法更新默认的ResultSet对象，并且只能向前移动其光标。但是，您可以创建可以滚动的ResultSet对象（光标可以向后移动或移动到绝对位置）并更新。

        ResultSet.TYPE_SCROLL_SENSITIVE字段创建一个ResultSet对象，其光标可以相对于当前位置向前和向后移动到绝对位置。
        ResultSet.CONCUR_UPDATABLE字段创建一个可以更新的ResultSet对象。
         */
        try (Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            ResultSet uprs = stmt.executeQuery("select * from coffees");
            while (uprs.next()) {
                float f = uprs.getFloat("price");
                uprs.updateFloat("price", f * percentage);
                uprs.updateRow();
                /*
                ResultSet.updateFloat方法使用指定的float值更新光标所在行中指定的列（在本例中为PRICE）。
                ResultSet包含各种更新程序方法，使您可以更新各种数据类型的列值。
                但是，这些更新程序方法都不会修改数据库;您必须调用方法ResultSet.updateRow来更新数据库。
                 */
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void insertRow(String coffeeName, int supplierID, float price, int sales, int total) throws SQLException {

        /*
        注意：并非所有JDBC驱动程序都支持使用ResultSet接口插入新行。
        如果尝试插入新行且JDBC驱动程序数据库不支持此功能，则会引发SQLFeatureNotSupportedException异常。
         */
        try (Statement stmt =
                     con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery("select * from coffees");

            /*
            ResultSet.moveToInsertRow方法将光标移动到插入行。
            插入行是与可更新结果集关联的特殊行。它本质上是一个缓冲区，可以通过在将行插入结果集之前调用updater方法来构造新行。
            例如，此方法调用ResultSet.updateString方法将插入行的COF_NAME列更新为Kona。
             */
            uprs.moveToInsertRow();

            uprs.updateString("cof_name", coffeeName);
            uprs.updateInt("sup_id", supplierID);
            uprs.updateFloat("price", price);
            uprs.updateInt("sales", sales);
            uprs.updateInt("total", total);

            // ResultSet.insertRow方法将插入行的内容插入到ResultSet对象和数据库中。
            uprs.insertRow();
            /*
            注意：使用ResultSet.insertRow插入行后，应将光标移动到插入行以外的行。
            例如，此示例使用方法ResultSet.beforeFirst将其移动到结果集中的第一行之前。
            如果应用程序的另一部分使用相同的结果集并且光标仍指向插入行，则可能会出现意外结果。
             */
            uprs.beforeFirst();
        }
    }

    public void batchUpdate() throws SQLException {
        Statement stmt = null;
        try {
            // 以下行禁用Connection对象con的自动提交模式，以便在调用方法executeBatch时不会自动提交或回滚事务。
            // 要允许正确的错误处理，应始终在开始批量更新之前禁用自动提交模式。
            con.setAutoCommit(false);
            stmt = con.createStatement();

            stmt.addBatch("insert into coffees values('Amaretto', 49, 9.99, 0, 0)");
            stmt.addBatch("insert into coffees values('Hazelnut', 49, 9.99, 0, 0)");
            stmt.addBatch("insert into coffees values('Amaretto_decaf', 49, 10.99, 0, 0)");
            stmt.addBatch("insert into coffees values('Hazelnut_decaf', 49, 10.99, 0, 0)");

            // 以下行将添加到其命令列表中的四个SQL命令发送到要作为批处理执行的数据库：
            int[] updateCounts = stmt.executeBatch();
            /*
            请注意，stmt使用executeBatch方法发送一批插入，而不是方法executeUpdate(它只发送一个命令并返回单个更新计数)。
            DBMS按照将命令添加到命令列表的顺序执行命令

            如果批处理中的所有四个命令都成功执行，则updateCounts将包含四个值，所有这些值都是1，因为插入会影响一行。
            与stmt关联的命令列表现在将为空，因为先前添加的四个命令在stmt调用方法executeBatch时被发送到数据库。
            您可以随时使用clearBatch方法显式清空此命令列表。
             */

            /*
            Connection.commit方法使COFFEES表的批量更新成为永久性。需要显式调用此方法，因为此连接的自动提交模式先前已禁用。
             */
            con.commit();
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            con.setAutoCommit(true);
        }

    }

    public void updateCoffeeSales(Map<String, Integer> salesForWeek) throws SQLException {
        PreparedStatement updateSales = null;
        PreparedStatement updateTotal = null;

        String updateString = "update coffees set sales = ? where cof_name = ?";
        String updateStatement = "update coffees set total = total + ? where cof_name = ?";

        try {
            con.setAutoCommit(false);
            updateSales = con.prepareStatement(updateString);
            updateTotal = con.prepareStatement(updateStatement);

            for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
                updateSales.setInt(1, e.getValue().intValue());
                updateSales.setString(2, e.getKey());
                updateSales.executeUpdate();

                updateTotal.setInt(1, e.getValue().intValue());
                updateTotal.setString(2, e.getKey());
                updateTotal.executeUpdate();
                con.commit();
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            if (con != null) {
                try {
                    System.err.println("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException ex) {
                    JdbcUtils.printSQLException(ex);
                }
            }
        } finally {
            if (updateSales != null) {
                updateSales.close();
            }
            if (updateTotal != null) {
                updateTotal.close();
            }
            if (con != null) {
                con.setAutoCommit(true);
            }
        }
    }

    public static void viewTable(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String query = "select cof_name, sup_id, price, sales, total from coffees";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("cof_name");
                int supplierID = rs.getInt("sup_id");
                float price = rs.getFloat("price");
                int sales = rs.getInt("sales");
                int total = rs.getInt("total");
                System.out.printf("%32s\t%d\t%10.2f\t%d\t%d%n", coffeeName, supplierID, price, sales, total);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public static void alternativeViewTable(Connection con) {
        String query = "select cof_name, sup_id, price, sales, total from coffees";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                /*
                ResultSet接口声明getter方法（例如，getBoolean和getLong），用于从当前行检索列值。
                您可以使用列的索引号或列的别名或名称来检索值。列索引通常更有效。列从1开始编号。
                为了获得最大的可移植性，每行中的结果集列应按从左到右的顺序读取，每列应只读一次。
                 */
                String coffeeName = rs.getString(1);
                int supplierID = rs.getInt(2);
                float price = rs.getFloat(3);
                int sales = rs.getInt(4);
                int total = rs.getInt(5);
                System.out.printf("%32s\t%d\t%10.2f\t%d\t%d%n", coffeeName, supplierID, price, sales, total);
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
            propertiesFileName = CoffeesTable.class.getClassLoader()
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

            CoffeesTable myCoffeeTable = new CoffeesTable(myConnection, myJdbcUtils.getDbms());

            System.out.println("\nContents of coffees table:");
            CoffeesTable.viewTable(myConnection);

            System.out.println("\nRaising coffee prices by 25%");
            myCoffeeTable.modifyPrices(1.25f);

            System.out.println("\nInserting a new row:");
            myCoffeeTable.insertRow("Kona", 150, 10.99f, 0, 0);
            CoffeesTable.viewTable(myConnection);

            System.out.println("\nUpdating sales of coffee per week:");
            Map<String, Integer> salesCoffeeWeek = new HashMap<>();
            salesCoffeeWeek.put("Colombian", 175);
            salesCoffeeWeek.put("French_Roast", 150);
            salesCoffeeWeek.put("Espresso", 60);
            salesCoffeeWeek.put("Colombian_Decaf", 155);
            salesCoffeeWeek.put("French_Roast_Decaf", 90);
            myCoffeeTable.updateCoffeeSales(salesCoffeeWeek);
            CoffeesTable.viewTable(myConnection);

            System.out.println("\nModifying prices by percentage");
            myCoffeeTable.modifyPricesByPercentage("Colombian", 0.01f, 9.00f);
            CoffeesTable.viewTable(myConnection);

            System.out.println("\nPerforming batch update; adding new coffees");
            myCoffeeTable.batchUpdate();
            CoffeesTable.viewTable(myConnection);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
