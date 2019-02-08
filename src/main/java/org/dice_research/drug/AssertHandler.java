package org.dice_research.drug;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.xml.sax.Attributes;

public class AssertHandler extends BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssertHandler.class);

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        super.startElement(uri, localName, qName, attributes);

        if (level == 0) {
            if (!qName.equals("drugbank")) {
                LOGGER.error("Level 0 is not drugbank");
                assert false;
            }
        }

        if (level == 1) {
            if (!qName.equals("drug")) {
                LOGGER.error("Level 1 is not drug");
                assert false;
            }
        }
    }
}
