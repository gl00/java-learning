package com.example.jdbclearning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

public class ClobSample {

    private Connection con;
    private JdbcUtils settings;

    public ClobSample(Connection con, JdbcUtils settings) {
        this.con = con;
        this.settings = settings;
    }

    public void addRowToCoffeeDescriptions(String coffeeName, String fileName) {
        Clob myClob = null;
        String sql = "insert into coffee_descriptions values(?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            myClob = con.createClob();
            Writer clobWriter = myClob.setCharacterStream(1);
            String str = readFile(fileName, clobWriter);
            System.out.println("Wrote the following: " + clobWriter.toString());
            if (settings.getDbms().equals("mysql")) {
                System.out.println("MySQL, setting String in Clob object with setString method");
                // MySQL 必须对 Clob 对象 setString，否则其内容为空
                // 其实对于 MySQL，不需要前面的 clobWriter 对象，只要对 myClob setString 即可
                myClob.setString(1, str);
            }
            System.out.println("Length of Clob: " + myClob.length());

            pstmt.setString(1, coffeeName);
            pstmt.setClob(2, myClob);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readExcerpt(String coffeeName, int numChar) {

        String sql = "select cof_desc from coffee_descriptions where cof_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, coffeeName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Clob myClob = rs.getClob("cof_desc");
                System.out.println("Length of retrieved Clob: " + myClob.length());
                return myClob.getSubString(1, numChar);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;
    }

    private String readFile(String fileName, Writer writer) throws IOException {

        String filePath = this.getClass().getClassLoader().getResource(fileName).getPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                System.out.println("Writing: " + nextLine);
                writer.write(nextLine);
                sb.append(nextLine);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        JdbcUtils myJdbcUtils = null;
        Connection myConnection = null;

        String propertiesFileName = null;
        if (args.length == 0 || args[0] == null) {
            propertiesFileName = ClobSample.class.getClassLoader()
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

            ClobSample myClobSample = new ClobSample(myConnection, myJdbcUtils);
            myClobSample.addRowToCoffeeDescriptions("Colombian", "txt/colombian-description.txt");
            String description = myClobSample.readExcerpt("Colombian", 10);
            System.out.println(description);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }

    }
}
