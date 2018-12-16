package com.example.jdbclearning;

import java.io.IOException;
import java.sql.*;

public class StoredProcedureMySQLSample {

    private Connection con;
    private String dbms;
    private String dbName;

    public StoredProcedureMySQLSample(Connection con, String dbms, String dbName) {
        this.con = con;
        this.dbms = dbms;
        this.dbName = dbName;
    }

    public void createProcedureShowSuppliers() {
        String queryDrop = "drop procedure if exists show_suppliers";
        try (Statement stmt = con.createStatement()) {
            System.out.println("Calling drop procedure");
            stmt.execute(queryDrop);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        String createProcedure =
                "create procedure show_suppliers() "
                        + "begin "
                        + "select suppliers.sup_name, coffees.cof_name "
                        + "from suppliers, coffees "
                        + "where suppliers.sup_id = coffees.sup_id "
                        + "order by sup_name; "
                        + "end";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createProcedure);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void createProcedureGetSupplierOfCoffee() {
        String queryDrop = "drop procedure if exists get_supplier_of_coffee";
        try (Statement stmt = con.createStatement()) {
            System.out.println("Calling drop procedure");
            stmt.execute(queryDrop);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        String createProcedure =
                "create procedure get_supplier_of_coffee(in coffeeName varchar(32), out supplierName varchar(40)) "
                        + "begin "
                        + "select suppliers.sup_name into supplierName "
                        + "from suppliers, coffees "
                        + "where suppliers.sup_id = coffees.sup_id "
                        + "and coffeeName = coffees.cof_name; "
                        + "end";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createProcedure);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void createProcedureRaisePrice() {
        String queryDrop = "drop procedure if exists raise_price";
        try (Statement stmt = con.createStatement()) {
            System.out.println("Calling drop procedure");
            stmt.execute(queryDrop);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        String createProcedure =
                "create procedure raise_price(in coffeeName varchar(32), in maximumPercentage float, inout newPrice numeric(10, 2)) "
                        + "begin "
                        + "main: begin "
                        + "declare maximumNewPrice numeric(10, 2); "
                        + "declare oldPrice numeric(10, 2); "
                        + "select coffees.price into oldPrice "
                        + "from coffees "
                        + "where coffees.cof_name = coffeeName; "
                        + "set maximumNewPrice = oldPrice * (1 + maximumPercentage); "
                        + "if (newPrice > maximumNewPrice) "
                        + "then set newPrice = maximumNewPrice; "
                        + "end if; "
                        + "if (newPrice <= oldPrice) "
                        + "then set newPrice = oldPrice; "
                        + "leave main; "
                        + "end if;"
                        + "update coffees "
                        + "set coffees.price = newPrice "
                        + "where coffees.cof_name = coffeeName; "
                        + "select newPrice; "
                        + "end main;"
                        + "end";
        System.out.println(createProcedure);
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createProcedure);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void runStoredProcedure(String coffeeName, float maximumPercentage, float newPrice) {
        CallableStatement cs = null;
        try {
            System.out.println("\nCalling the procedure get_supplier_of_coffee");
            cs = con.prepareCall("{call get_supplier_of_coffee(?, ?)}");
            cs.setString(1, coffeeName);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.executeQuery();

            String supplierName = cs.getString(2);
            if (supplierName != null) {
                System.out.println("\nSupplier of the coffee " + coffeeName + ": " + supplierName);
            } else {
                System.out.println("\nUnable to find the coffee " + coffeeName);
            }

            System.out.println("\nCalling the procedure show_suppliers");
            cs = con.prepareCall("{call show_suppliers}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String supplier = rs.getString("sup_name");
                String coffee = rs.getString("cof_name");
                System.out.println(supplier + ": " + coffee);
            }

            System.out.println("\nContents of coffees before calling raise_rice:");
            CoffeesTable.viewTable(con);

            System.out.println("\nCalling the procedure raise_prise");
            cs = con.prepareCall("{call raise_price(?, ?, ?)}");
            cs.setString(1, coffeeName);
            cs.setFloat(2, maximumPercentage);
            cs.setFloat(3, newPrice);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.execute();

            System.out.println("\nValues of newPrice after calling raise_price: " + newPrice);


            System.out.println("\nContents of coffees after calling raise_rice:");
            CoffeesTable.viewTable(con);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = StoredProcedureMySQLSample.class.getClassLoader()
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
            StoredProcedureMySQLSample myStoredProcedureSample =
                    new StoredProcedureMySQLSample(myConnection, myJdbcUtils.getDbms(), myJdbcUtils.getDbName());

            System.out.println("\nCreating show_suppliers stored procedure");
            myStoredProcedureSample.createProcedureShowSuppliers();

            System.out.println("\nCreating get_supplier_of_coffee stored procedure");
            myStoredProcedureSample.createProcedureGetSupplierOfCoffee();

            System.out.println("\nCreating raise_price stored procedure");
            myStoredProcedureSample.createProcedureRaisePrice();

            System.out.println("\nCalling all stored procedure:");
            myStoredProcedureSample.runStoredProcedure("Colombian", 0.10f, 19.99f);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
