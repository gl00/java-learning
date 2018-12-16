package jaxp;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class Dom4jDemo {

    @Test
    public void queryXml() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read("res/in/messages.xml");

        Element root = doc.getRootElement();
        System.out.println("Root element: " + root.getName());
        System.out.println("--------------------------------");

        for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
            Element element = it.next();
            System.out.println("\nCurrent element: " + element.getName());

            for (Attribute attr : element.attributes()) {
                System.out.println(attr.getName() + ": " + attr.getValue());
            }

            for (Iterator<Element> it2 = element.elementIterator(); it2.hasNext();) {
                Element elem = it2.next();
                System.out.println(elem.getName() + ": " + elem.getText());
            }

        }
    }
}
