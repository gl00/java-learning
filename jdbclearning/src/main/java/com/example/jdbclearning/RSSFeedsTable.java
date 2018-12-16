package com.example.jdbclearning;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;

public class RSSFeedsTable {

    private Connection con;
    private String dbms;
    private String dbName;

    public RSSFeedsTable(Connection con, String dbms, String dbName) {
        this.con = con;
        this.dbms = dbms;
        this.dbName = dbName;
    }

    public void addRSSFeed(String fileName)
            throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException, SQLException {

        // Parse the document and retrieve the name of the RSS feed
        String titleString = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(fileName);

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();

        Node titleElement = (Node) xPath.evaluate("/rss/channel/title[1]", doc, XPathConstants.NODE);

        if (titleElement == null) {
            System.out.println("Unable to retrieve title element");
            return;
        } else {
            titleString = titleElement.getTextContent().trim().toLowerCase().replaceAll("\\s+", "_");
            System.out.println("title element: [" + titleString + "]");
        }

        System.out.println(JdbcUtils.convertDocumentToString(doc));

        PreparedStatement insertRow = null;
        SQLXML rssData = null;

        System.out.println("Current DBMS: " + dbms);

        try {
            if (dbms.equals("mysql")) {
                // For databases that support the SQLXML data type, this creates a
                // SQLXML object from org.w3c.dom.Document.

                System.out.println("Adding xml file: " + fileName);
                String insertRowQuery = "insert into rss_feeds (rss_name, rss_feed_xml) values (?, ?)";
                insertRow = con.prepareStatement(insertRowQuery);
                insertRow.setString(1, titleString);

                System.out.println("Creating SQLXML object with MySQL");
                rssData = con.createSQLXML();
                System.out.println("Creating DOMResult object");
                DOMResult dom = rssData.setResult(DOMResult.class);
                dom.setNode(doc);

                insertRow.setSQLXML(2, rssData);
                System.out.println("Running executeUpdate()");
                insertRow.executeUpdate();

            } else if (dbms.equals("derby")) {
                System.out.println("Adding xml file: " + fileName);
                String insertRowQuery = "insert into rss_feeds (rss_name, rss_feed_xml) values"
                        + "(?, xmlparse(document case (? as clob) preserve whitespace))";
                insertRow = con.prepareStatement(insertRowQuery);
                insertRow.setString(1, titleString);
                String convertedDoc = JdbcUtils.convertDocumentToString(doc);
                insertRow.setClob(2, new StringReader(convertedDoc));

                System.out.println("Running executeUpdate()");
                insertRow.executeUpdate();
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        } finally {
            if (insertRow != null) {
                insertRow.close();
            }
        }
    }

    public void viewTable(Connection con)
            throws ParserConfigurationException, TransformerException, IOException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        try (Statement stmt = con.createStatement()) {
            if (dbms.equals("mysql")) {
                String query = "select rss_name, rss_feed_xml from rss_feeds";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String rssName = rs.getString(1);
                    SQLXML rssFeedXML = rs.getSQLXML(2);
                    Document doc = db.parse(rssFeedXML.getBinaryStream());
                    System.out.println("RSS identifier: " + rssName);
                    System.out.println(JdbcUtils.convertDocumentToString(doc));
                }
            } else if (dbms.equals("derby")) {
                String query = "select rss_name, xmlserialize (rss_feed_xml as clob) from rss_feeds";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String rssName = rs.getString(1);
                    String rssFeedXML = rs.getString(2);
                    Document doc = db.parse(new InputSource(new StringReader(rssFeedXML)));
                    System.out.println("RSS identifier: " + rssName);
                    System.out.println(JdbcUtils.convertDocumentToString(doc));
                }
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
            propertiesFileName = RSSFeedsTable.class.getClassLoader()
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

            RSSFeedsTable myRSSFeedsTable =
                    new RSSFeedsTable(myConnection, myJdbcUtils.getDbms(), myJdbcUtils.getDbName());


            String fileName1 = RSSFeedsTable.class.getClassLoader()
                    .getResource("xml/rss-coffee-industry-news.xml")
                    .getPath();
            String fileName2 = RSSFeedsTable.class.getClassLoader()
                    .getResource("xml/rss-the-coffee-break-blog.xml")
                    .getPath();
            myRSSFeedsTable.addRSSFeed(fileName1);
            myRSSFeedsTable.addRSSFeed(fileName2);
            myRSSFeedsTable.viewTable(myConnection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(myConnection);
        }
    }
}
