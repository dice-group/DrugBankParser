package org.dice_research.drug;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.junit.Test;
import org.xml.sax.helpers.DefaultHandler;

public class ParseTest {
    @Test
    public void parse() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        InputStream input = new FileInputStream("src/test/resources/example.xml");
        SAXParser parser = factory.newSAXParser();
        DefaultHandler handler = new BaseHandler();
        parser.parse(input, handler);
    }
}
