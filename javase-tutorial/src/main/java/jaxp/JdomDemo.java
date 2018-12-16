package jaxp;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JdomDemo {

    @Test
    public void parseXml() throws Exception {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new File("res/in/messages.xml"));

        Element rootElem = doc.getRootElement();
        System.out.println("Root element: " + rootElem.getName());
        System.out.println("-----------------------------------");

        List<Content> content = rootElem.getContent();

        List<Element> children = rootElem.getChildren();
        for (Element elem : children) {
            System.out.println("\nCurrent element: " + elem.getName());

            Attribute id = elem.getAttribute("id");
            System.out.println("id: " + id.getValue());

            System.out.println("to: " + elem.getChild("to").getValue());
            System.out.println("from: " + elem.getChild("to").getText());
            System.out.println("heading: " + elem.getChildText("heading"));
            System.out.println("body: " + elem.getChildText("body"));
        }
    }

    @Test
    public void createXml() throws IOException {
        Document doc = new Document();
        Element messages = new Element("messages");
        doc.setRootElement(messages);

        Element note = new Element("note");
        messages.addContent(note);

        note.setAttribute("id", "999");
        note.addContent(new Element("to").setText("All employees"));
        note.addContent(new Element("from").setText("Frank"));
        note.addContent(new Element("heading").setText("Invite"));
        note.addContent(new Element("bod").setText("Company's annual dinner"));

        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getPrettyFormat());
        FileWriter fileWriter = new FileWriter("res/out/jdom_messages.xml");
        out.output(doc, fileWriter);
    }

    @Test
    public void modifyXml() throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build("res/out/jdom_messages.xml");

        Element rootElem = doc.getRootElement();

        Element note = rootElem.getChild("note");
        // 更新属性
        note.getAttribute("id").setValue("123");
        // 更新元素
        note.getChild("from").setText("Boss");
        // 添加元素
        Element newNote = new Element("note");
        newNote.setAttribute("id", "567");
        newNote.addContent(new Element("to").setText("David"));
        newNote.addContent(new Element("from").setText("Matt"));
        newNote.addContent(new Element("heading").setText("Reminder"));
        newNote.addContent(new Element("body").setText("Don't forget to submit the report"));
        rootElem.addContent(newNote);

        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getPrettyFormat());
        out.output(doc, System.out);
    }
}