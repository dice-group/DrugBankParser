package org.dice_research.drug;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFWriter;
import org.xml.sax.helpers.DefaultHandler;

public class Executor {

    public void execute(String inputFile, String outputFile, RDFCreatingHandler handler) {
        try (FileOutputStream fout = new FileOutputStream(outputFile)) {
            StreamRDF outputStream = StreamRDFWriter.getWriterStream(fout, Lang.NTRIPLES);
            outputStream.start();
            handler.setOutputStream(outputStream);
            execute(inputFile, handler);
            outputStream.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(String inputFile, DefaultHandler handler) {
        try (InputStream input = new FileInputStream("src/test/resources/example.xml")) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(input, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
