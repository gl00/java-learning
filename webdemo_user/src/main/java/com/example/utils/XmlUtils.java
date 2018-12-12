package com.example.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;

public class XmlUtils {
    private static final String filePath;

    static {
        filePath = XmlUtils.class.getClassLoader().getResource("users.xml").getPath();
    }

    public static Document getDocument() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(filePath);
        return document;
    }

    public static void writeToXml(Document document) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(fileWriter, format);
            writer.write(document);
        }
    }
}
