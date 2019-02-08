package org.dice_research.drug;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.helpers.DefaultHandler;

public class ParseTest {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    InputStream input;
    SAXParser parser;

    @Before
    public void setUp() throws Exception {
        input = new FileInputStream("src/test/resources/example.xml");
        parser = factory.newSAXParser();
    }

    @Test
    public void baseHandler() throws Exception {
        DefaultHandler handler = new BaseHandler();
        parser.parse(input, handler);
    }

    @Test
    public void assertHandler() throws Exception {
        DefaultHandler handler = new AssertHandler();
        parser.parse(input, handler);
    }
}
