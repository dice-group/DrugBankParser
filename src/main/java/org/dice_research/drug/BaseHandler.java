package org.dice_research.drug;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class BaseHandler extends DefaultHandler {
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        System.out.println("startElement: " + qName);
    }
}
