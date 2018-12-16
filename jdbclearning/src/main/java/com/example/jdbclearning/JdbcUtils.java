package com.example.jdbclearning;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {

    private String dbms;
    private String jarFile;
    private String dbName;
    private String username;
    private String password;
    private String urlString;

    private String driver;
    private String serverName;
    private int portNumber;
    private Properties prop;

    public JdbcUtils(String propertiesFileName) throws IOException {
        super();
        this.setProperties(propertiesFileName);
    }

    public static String convertDocumentToString(Document doc) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }

    public String getDbms() {
        return dbms;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrlString() {
        return urlString;
    }

    private static void createDatabase(Connection connArg, String dbNameArg, String dbmsArg) {
        if (dbmsArg.equals("mysql")) {
            try {
                Statement s = connArg.createStatement();
                String newDatabaseString = "CREATE DATABASE IF NOT EXISTS " + dbNameArg;
                s.executeUpdate(newDatabaseString);

                System.out.println("Created database " + dbNameArg);
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    public static void closeConnection(Connection conn) {
        System.out.println("Releasing all open resources ...");
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    public static void cursorHoldabilitySupport(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();

        System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " + ResultSet.HOLD_CURSORS_OVER_COMMIT);
        System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " + ResultSet.CLOSE_CURSORS_AT_COMMIT);
        System.out.println("metaData.getResultSetHoldability(): " + metaData.getResultSetHoldability());
        System.out.println("HOLD_CURSORS_OVER_COMMIT? "
                + metaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
        System.out.println("CLOSE_CURSORS_AT_COMMIT? "
                + metaData.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));
    }

    public static void rowIdLifetime(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        RowIdLifetime lifetime = metaData.getRowIdLifetime();
        switch (lifetime) {
            case ROWID_UNSUPPORTED:
                System.out.println("ROWID type not supported");
                break;
            case ROWID_VALID_FOREVER:
                System.out.println("ROWID type has unlimited lifetime");
                break;
            case ROWID_VALID_OTHER:
                System.out.println("ROWID type has indeterminate lifetime");
                break;
            case ROWID_VALID_SESSION:
                System.out.println("ROWID type has lifetime that is valid for at least the containing session");
                break;
            case ROWID_VALID_TRANSACTION:
                System.out.println("ROWID type has lifetime that is valid for at least the containing transaction");
                break;
        }
    }

    public static boolean ignoreException(String sqlState) {
        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;
        // 42Y55: Table already exists in schema
        return sqlState.equalsIgnoreCase("42Y55");
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (!ignoreException(((SQLException) e).getSQLState())) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static void printBatchUpdateException(BatchUpdateException b) {
        System.err.println("----BatchUpdateException----");
        System.err.println("SQLState:  " + b.getSQLState());
        System.err.println("Message:  " + b.getMessage());
        System.err.println("Vendor:  " + b.getErrorCode());
        System.err.print("Update counts:  ");
        int[] updateCounts = b.getUpdateCounts();

        for (int i = 0; i < updateCounts.length; i++) {
            System.err.print(updateCounts[i] + "   ");
        }
    }

    public void setProperties(String fileName) throws IOException {
        this.prop = new Properties();
        FileInputStream in = new FileInputStream(fileName);
        prop.loadFromXML(in);

        this.dbms = prop.getProperty("dbms");
        this.jarFile = prop.getProperty("jar_file");
        this.driver = prop.getProperty("driver");
        this.dbName = prop.getProperty("database_name");
        this.username = prop.getProperty("user_name");
        this.password = prop.getProperty("password");
        this.serverName = prop.getProperty("server_name");
        this.portNumber = Integer.parseInt(prop.getProperty("port_number"));

        System.out.println("Set the following properties:");
        System.out.println("dbms: " + dbms);
        System.out.println("driver: " + driver);
        System.out.println("dbName: " + dbName);
        System.out.println("userName: " + username);
        System.out.println("serverName: " + serverName);
        System.out.println("portNumber: " + portNumber);
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.username);
        connectionProps.put("password", this.password);

        String currentUrlString = null;

        if (this.dbms.equals("mysql")) {
            currentUrlString = "jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/";
            // 使用的 MySQL 版本是 8.0.13，没有加上 serverTimezone 参数会抛异常：
            // java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
            // 根据提示：要么配置 MySQL 服务器时区，要么在 URL 上通过参数 serverTimezone 设置
//            conn = DriverManager.getConnection(currentUrlString + "?serverTimezone=UTC", connectionProps);
            conn = DriverManager.getConnection(currentUrlString, connectionProps);

//            this.urlString = currentUrlString + this.dbName + "?serverTimezone=UTC";
            this.urlString = currentUrlString + this.dbName;
            conn.setCatalog(this.dbName);
        } else if (this.dbms.equals("derby")) {
            this.urlString = "jdbc:" + this.dbms + ":" + this.dbName;
            conn = DriverManager.getConnection(this.urlString + ";create=true", connectionProps);
        }
        System.out.println("Connected tot database");
        return conn;
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args[0] == null) {
            propertiesFileName = JdbcUtils.class.getClassLoader()
                    .getResource("properties/mysql-sample-properties.xml").getPath();
        } else {
            propertiesFileName = args[0];
        }

        try {
            System.out.println("Reading properties file " + propertiesFileName);
            myJdbcUtils = new JdbcUtils(propertiesFileName);
        } catch (IOException e) {
            System.err.println("Problem reading properties file " + propertiesFileName);
            e.printStackTrace();
            return;
        }

        try {
            myConnection = myJdbcUtils.getConnection();
            JdbcUtils.createDatabase(myConnection, myJdbcUtils.getDbName(), myJdbcUtils.getDbms());
            JdbcUtils.cursorHoldabilitySupport(myConnection);
            JdbcUtils.rowIdLifetime(myConnection);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
