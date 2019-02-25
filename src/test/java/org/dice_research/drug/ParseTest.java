package org.dice_research.drug;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.dice_research.drug.vocab.DrugBank;
import org.junit.Before;
import org.junit.Test;

public class ParseTest {

    private static final String TEST_FILE = "src/test/resources/dataset.xml";

    private String getOutputFile(String name) {
        return "output/" + name;
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void baseHandler() throws Exception {
        Executor.execute(TEST_FILE, new BaseHandler());
    }

    @Test
    public void assertHandler() throws Exception {
        Executor.execute(TEST_FILE, new AssertHandler());
    }

    @Test
    public void drugResourceHandler() throws Exception {
        Executor.execute(TEST_FILE, getOutputFile("drugs.nt"), new DrugResourceHandler());
    }

    @Test
    public void drugLabelHandler() throws Exception {
        Executor.execute(TEST_FILE, getOutputFile("drug-labels.nt"), new DrugLabelHandler());
    }

    @Test
    public void interactionHandler() throws Exception {
        Executor.execute(TEST_FILE, getOutputFile("interactions.nt"), new InteractionHandler());
    }

    @Test
    public void stringLiteralHandler() throws Exception {
        // Executor.execute(TEST_FILE, getOutputFile("indicationDescription.nt"), new
        // StringLiteralHandler(DrugBank.indicationDescription, "indication"));
        // Executor.execute(TEST_FILE, getOutputFile("pharmacodynamicsDescription.nt"),
        // new StringLiteralHandler(DrugBank.pharmacodynamicsDescription,
        // "pharmacodynamics"));
        // Executor.execute(TEST_FILE, getOutputFile("mechanismOfActionDescription.nt"),
        // new StringLiteralHandler(DrugBank.mechanismOfActionDescription,
        // "mechanism-of-action"));
        // Executor.execute(TEST_FILE, getOutputFile("toxicityDescription.nt"), new
        // StringLiteralHandler(DrugBank.toxicityDescription, "toxicity"));
        // Executor.execute(TEST_FILE, getOutputFile("description.nt"), new
        // StringLiteralHandler(RDFS.comment, "description"));
        // Executor.execute(TEST_FILE, getOutputFile("stateDescription.nt"), new
        // StringLiteralHandler(DrugBank.stateDescription, "state"));
        Executor.execute(TEST_FILE, getOutputFile("casNumber.nt"),
                new StringLiteralHandler(DrugBank.casNumber, "cas-number"));
        Executor.execute(TEST_FILE, getOutputFile("clearanceDescription.nt"),
                new StringLiteralHandler(DrugBank.clearanceDescription, "clearance"));
        Executor.execute(TEST_FILE, getOutputFile("halfLifeDescription.nt"),
                new StringLiteralHandler(DrugBank.halfLifeDescription, "half-life"));
        Executor.execute(TEST_FILE, getOutputFile("routeOfEliminationDescription.nt"),
                new StringLiteralHandler(DrugBank.routeOfEliminationDescription, "route-of-elimination"));
        Executor.execute(TEST_FILE, getOutputFile("unii.nt"), new StringLiteralHandler(DrugBank.unii, "unii"));
        Executor.execute(TEST_FILE, getOutputFile("volumeOfDistributionDescription.nt"),
                new StringLiteralHandler(DrugBank.volumeOfDistributionDescription, "volume-of-distribution"));
    }

    @Test
    public void productHandler() throws Exception {
        File temp = File.createTempFile("handler", "test");
        Executor.execute(TEST_FILE, temp.getAbsolutePath(), new ProductHandler());
        Model model = ModelFactory.createDefaultModel();
        try (InputStream is = new BufferedInputStream(new FileInputStream(temp));
                OutputStream os = new BufferedOutputStream(new FileOutputStream(getOutputFile("products.nt")))) {
            model.read(is, "", "NT");
            model.write(os, "NT");
        }
    }

}
