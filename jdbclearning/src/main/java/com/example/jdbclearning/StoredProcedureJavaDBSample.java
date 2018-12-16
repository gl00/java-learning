package com.example.jdbclearning;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

/**
 * <p>在Java DB中创建和使用存储过程涉及以下步骤：
 *
 * <ol>
 *
 * <li>Create a public static Java method in a Java class: This method performs the required task of the stored procedure.
 * <br>在Java类中创建公共静态Java方法：此方法执行存储过程的所需任务。
 *
 * <li>Create the stored procedure: This stored procedure calls the Java method you created.
 * <br>创建存储过程：此存储过程调用您创建的Java方法。
 *
 * <li>Package the Java class (that contains the public static Java method you created earlier) in a JAR file.
 * <br>将Java类（包含您之前创建的公共静态Java方法）打包到JAR文件中。
 *
 * <li>Call the stored procedure with the CALL SQL statement. See the section Calling Stored Procedures in Java DB and MySQL
 * <br>使用CALL SQL语句调用存储过程。请参阅Java DB和MySQL中的调用存储过程一节
 *
 * </ol>
 */
public class StoredProcedureJavaDBSample {

    private Connection con;
    private String dbms;
    private String dbName;
    private String schema = "APP";

    public StoredProcedureJavaDBSample(Connection con, String dbms, String dbName) {
        this.con = con;
        this.dbms = dbms;
        this.dbName = dbName;
    }

    public static void showSuppliers(ResultSet[] rs) throws SQLException {

        // 从URL jdbc：default：connection中检索Connection对象。这是Java DB中的一种约定，用于指示存储过程将使用当前存在的Connection对象。
        Connection con = DriverManager.getConnection("jdbc:default:connection");
        // 请注意，此方法中未关闭Statement对象。不要关闭存储过程的Java方法中的任何Statement对象;如果这样做，当您调用存储过程时发出CALL语句时，ResultSet对象将不存在。
        Statement stmt = null;
        String query = "select suppliers.sup_name, coffees.cof_name "
                + "from suppliers, coffees "
                + "where suppliers.sup_id = coffees.sup_id order by sup_name";
        stmt = con.createStatement();
        rs[0] = stmt.executeQuery(query);
    }

    public static void getSupplierOfCoffee(String coffeeName, String[] supplierName) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:default:connection");
        String query = "select supplier.sup_name from suppliers, coffees "
                + "where suppliers.sup_id = coffees.sup_id and ? = coffees.cof_name";
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            supplierName[0] = rs.getString(1);
        } else {
            supplierName[0] = null;
        }
    }

    public static void raisePrice(String coffeeName, float maximumPercentage, BigDecimal[] newPrice) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement pstmt = null;

        String query = "select price from coffees where cof_name = ?";
        pstmt = con.prepareStatement(query);

        pstmt.setString(1, coffeeName);
        ResultSet rs = pstmt.executeQuery();
        BigDecimal oldPrice;
        if (rs.next()) {
            oldPrice = rs.getBigDecimal(1);
        } else {
            return;
        }

        BigDecimal maximumNewPrice = oldPrice.multiply(new BigDecimal(1 + maximumPercentage));

        // Test if newPrice[0] > maximumNewPrice
        if (newPrice[0].compareTo(maximumNewPrice) > 0) {
            newPrice[0] = maximumNewPrice;
        } else {
            newPrice[0] = oldPrice;
            return;
        }

        String queryUpdatePrice = "update coffees set price = ? where cof_name = ?";
        pstmt = con.prepareStatement(queryUpdatePrice);
        pstmt.setBigDecimal(1, newPrice[0]);
        pstmt.setString(2, coffeeName);
        pstmt.executeUpdate();
    }

    public void createProcedures(Connection connection) {

        String queryDropShowSuppliers = "DROP PROCEDURE SHOW_SUPPLIERS";
        String queryDropGetSupplierOfCoffee = "DROP PROCEDURE GET_SUPPLIER_OF_COFFEE";
        String queryDropRaisePrice = "DROP PROCEDURE RAISE_PRICE";

        System.out.println("Calling drop procedure");
        try (Statement stmtDropShowSuppliers = con.createStatement();
             Statement stmtDropGetSupplierOfCoffee = con.createStatement();
             Statement stmtDropRaisePrice = con.createStatement()) {

            stmtDropShowSuppliers.execute(queryDropShowSuppliers);
            stmtDropGetSupplierOfCoffee.execute(queryDropGetSupplierOfCoffee);
            stmtDropRaisePrice.execute(queryDropRaisePrice);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        String queryCreateShowSuppliers =
                "create procedure show_suppliers() "
                        + "parameter style java "
                        + "language java "
                        + "dynamic result sets 1 "
                        + "external name 'com.example.jdbclearning.StoredProcedureJavaDBSample.showSuppliers'";

        String queryCreateGetSupplierOfCoffee =
                "create procedure get_supplier_of_coffee(in coffeeName varchar(32), out supplierName varchar(40)) "
                        + "parameter style java "
                        + "language java "
                        + " dynamic result sets 0 "
                        + "external name 'com.example.jdbclearning.StoredProcedureJavaDBSample.getSupplierOfCoffee'";

        String queryCreateRaisePrice =
                "create procedure raise_price(in coffeeName varchar(32), in maximumPercentage float, inout newPrice numeric(10,2)) "
                        + "parameter style java "
                        + "language java "
                        + " dynamic result sets 0 "
                        + "external name 'com.example.jdbclearning.StoredProcedureJavaDBSample.raisePrice'";

        System.out.println("Calling create procedure");
        try (Statement stmtCreateShowSuppliers = con.createStatement();
             Statement stmtCreateGetSupplierOfCoffee = con.createStatement();
             Statement stmtCreateRaisePrice = con.createStatement()) {

            stmtCreateShowSuppliers.execute(queryCreateShowSuppliers);
            stmtCreateGetSupplierOfCoffee.execute(queryCreateGetSupplierOfCoffee);
            stmtCreateRaisePrice.execute(queryCreateRaisePrice);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void runStoredProcedures(String coffeeName, float maximumPercentage, float newPrice) throws SQLException {

        try (CallableStatement cs = con.prepareCall("{call get_supplier_of_coffee(?, ?)}")) {

            System.out.println("\nCalling the stored procedure get_supplier_of_coffee");
            cs.setString(1, coffeeName);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();

            String supplierName = cs.getString(2);
            if (supplierName != null) {
                System.out.println("\nSupplier of the coffee " + coffeeName + ": " + supplierName);
            } else {
                System.out.println("\nUnable to find the coffee " + coffeeName);
            }

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        try (CallableStatement cs = con.prepareCall("{call show_suppliers()}")) {

            System.out.println("\nCalling the stored procedure show_suppliers");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                String supplier = rs.getString("sup_name");
                String coffee = rs.getString("cof_name");
                System.out.println(supplier + ": " + coffee);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        try (CallableStatement cs = con.prepareCall("{call raise_price(?,?,?)}")) {

            System.out.println("\nContents of coffees table before calling raise_price:");
            CoffeesTable.viewTable(con);

            System.out.println("\nCalling stored procedure raise_price");
            cs.setString(1, coffeeName);
            cs.setDouble(2, maximumPercentage);
            cs.setDouble(3, newPrice);
            cs.registerOutParameter(3, Types.DOUBLE);
            cs.execute();

            System.out.println("\nalues of newPrice after calling raise_price: " + cs.getFloat(3));

            System.out.println("\nContents of coffees table after calling raise_price:");
            CoffeesTable.viewTable(con);
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        System.out.println(StoredProcedureJavaDBSample.class.getClassLoader().getResource(""));
        System.out.println(StoredProcedureJavaDBSample.class.getClassLoader().getResource("/"));

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = StoredProcedureJavaDBSample.class.getClassLoader()
                    .getResource("properties/javadb-sample-properties.xml").getPath();
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

            StoredProcedureJavaDBSample mySP =
                    new StoredProcedureJavaDBSample(myConnection, myJdbcUtils.getDbms(), myJdbcUtils.getDbName());

            System.out.println("\nCreating stored procedure:");
            mySP.createProcedures(myConnection);

            System.out.println("\nRunning all stored procedures:");
            mySP.runStoredProcedures("Colombian", 0.10f, 19.99f);

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
