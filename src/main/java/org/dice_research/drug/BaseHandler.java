package org.dice_research.drug;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.xml.sax.SAXException;
import java.util.function.Consumer;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class BaseHandler extends DefaultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);

    public int level = -1;
    public String currentDrugID = null;

    private Consumer<String> captureTextConsumer = null;
    private StringBuilder textCaptureBuilder = new StringBuilder();

    public void endDrug() {}

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        ++level;

        if (level == 1 && qName.equals("drug")) {
        }

        if (level == 2 && qName.equals("drugbank-id")
        && attributes.getIndex("primary") != -1
        && attributes.getValue("primary").equals("true")) {
            captureText(s -> currentDrugID = s);
        }

    }

    public void endElement(String uri, String localName, String qName) {
        if (captureTextConsumer != null) {
            captureTextConsumer.accept(textCaptureBuilder.toString());
            captureTextConsumer = null;
            textCaptureBuilder.delete(0, textCaptureBuilder.length());
        }

        if (level == 1 && qName.equals("drug")) {
            endDrug();
            currentDrugID = null;
        }

        level--;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (captureTextConsumer != null) {
            textCaptureBuilder.append(ch, start, length);
        }
    }

    public void captureText(Consumer<String> handler) {
        captureTextConsumer = handler;
    }

}
