package jaxp;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxDemo {

    @Test
    public void parseXml() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();

        parser.parse("res/in/messages.xml", new DefaultHandler() {

            private boolean bTo;
            private boolean bFrom;
            private boolean bHeading;
            private boolean bBody;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {

                System.out.println("Start element: " + qName);

                if ("note".equals(qName)) {
                    String id = attributes.getValue("id");
                    System.out.println("ID: " + id);
                } else if ("to".equals(qName)) {
                    bTo = true;
                } else if ("from".equals(qName)) {
                    bFrom = true;
                } else if ("heading".equals(qName)) {
                    bHeading = true;
                } else if ("body".equals(qName)) {
                    bBody = true;
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                System.out.println("  End element: " + qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (bTo) {
                    System.out.println("           To: " + new String(ch, start, length));
                    bTo = false;
                } else if (bFrom) {
                    System.out.println("         From: " + new String(ch, start, length));
                    bFrom = false;
                } else if (bHeading) {
                    System.out.println("      Heading: " + new String(ch, start, length));
                    bHeading = false;
                } else if (bBody) {
                    System.out.println("         Body: " + new String(ch, start, length));
                    bBody = false;
                }
            }
        });
    }
}
