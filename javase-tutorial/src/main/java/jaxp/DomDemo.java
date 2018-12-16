package jaxp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomDemo {

    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private String xmlFile;

    @BeforeEach
    public void init() throws ParserConfigurationException, IOException, SAXException {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        xmlFile = DomDemo.class.getResource("/messages.xml").getPath();
    }

    @Test
    public void parseXml() throws IOException, SAXException {
        Document doc = db.parse(xmlFile);
        Element rootElem = doc.getDocumentElement();
        System.out.println("Root element: " + rootElem.getNodeName());
        System.out.println("---------------------------------------");

        NodeList nodeList = doc.getElementsByTagName("note");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("\nCurrent element:");

            // xml 会把空白（空格/换行等）当成 text 元素
            // 所以如果 xml 是格式化了的，输出的时候又不希望打印那些空白元素
            // 就要过滤元素类型
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("id: " + elem.getAttribute("id"));
                System.out.println("to: "
                        + elem.getElementsByTagName("to").item(0).getTextContent());
                System.out.println("from: "
                        + elem.getElementsByTagName("from").item(0).getTextContent());
                System.out.println("heading: "
                        + elem.getElementsByTagName("heading").item(0).getTextContent());
                System.out.println("body: "
                        + elem.getElementsByTagName("body").item(0).getTextContent());
            }
        }
    }

    @Test
    public void queryXml() throws IOException, SAXException {
        Document doc = db.parse(xmlFile);
        Element rootElem = doc.getDocumentElement();
        rootElem.normalize();
        System.out.println("Root element: " + rootElem.getNodeName());

        if (doc.hasChildNodes()) {
            printNode(doc.getChildNodes());
        }
    }

    private void printNode(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                System.out.println("\nNode name: " + node.getNodeName() + " [OPEN]");
                System.out.println("Node value: " + node.getTextContent());

                if (node.hasAttributes()) {
                    NamedNodeMap attrs = node.getAttributes();
                    for (int j = 0; j < attrs.getLength(); j++) {
                        Node attr = attrs.item(j);
                        System.out.println("attr name: " + attr.getNodeName());
                        System.out.println("attr value: " + attr.getNodeValue());
                    }
                }
                if (node.hasChildNodes()) {
                    printNode(node.getChildNodes());
                }

                System.out.println("Node name: " + node.getNodeName() + " [CLOSE]");
            }
        }
    }

    /*
    建立一个包含以下内容的 xml 文件。
    <xml version="1.0" encoding="UTF-8" ?>
    <messages>
        <note id="1001">
            <to>Bob</to>
            <from>Tom</from>
            <heading>Notice</heading>
            <body>Meeting 10 am Room 106</body>
        </note>
    </messages>
     */
    @Test
    public void createXml() throws TransformerException {
        // xml 会把空白当成元素
        // 建立 xml 的时候没有插入空白元素，那生成的 xml 文件中就不会有空白元素
        // 也就没有上面那种格式化了的效果。

        Document doc = db.newDocument();

        Element rootElem = doc.createElement("messages");
        doc.appendChild(rootElem);

        Element note = doc.createElement("note");
        rootElem.appendChild(note);

        Attr id = doc.createAttribute("id");
        id.setValue("1001");
        note.setAttributeNode(id);

        Element to = doc.createElement("to");
        to.appendChild(doc.createTextNode("Bob"));
        note.appendChild(to);

        Element from = doc.createElement("from");
        from.appendChild(doc.createTextNode("Tom"));
        note.appendChild(from);

        Element heading = doc.createElement("heading");
        heading.appendChild(doc.createTextNode("Notice"));
        note.appendChild(heading);

        Element body = doc.createElement("body");
        body.appendChild(doc.createTextNode("Metting 10 am Room 106"));
        note.appendChild(body);

        // 写入 xml 文件
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        DOMSource domSource = new DOMSource(doc);
        File targetFile = new File("res/out/dom_messages.xml");
        StreamResult streamResult = new StreamResult(targetFile);

        transformer.transform(domSource, streamResult);
    }

    /*
    修改 dom_messages.xml 中第一个 note 元素：
        把属性 id 的值加 10
        把 to 元素的值该为 John
        删除 heading 元素
     */
    @Test
    public void modifyXml() throws IOException, SAXException, TransformerException {
        String filePath = "res/out/dom_messages.xml";
        Document doc = db.parse(filePath);

        Node note = doc.getElementsByTagName("note").item(0);

        NamedNodeMap attrs = note.getAttributes();
        Node id = attrs.getNamedItem("id");
        id.setNodeValue(id.getNodeValue() + 10);

        NodeList childNodes = note.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if ("to".equals(item.getNodeName())) {
                item.setTextContent("John");
            }
            if ("heading".equals(item.getNodeName())) {
                note.removeChild(item);
            }
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(filePath));
        transformer.transform(domSource, streamResult);
    }

}
