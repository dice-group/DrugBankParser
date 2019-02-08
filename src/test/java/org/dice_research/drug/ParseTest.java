package org.dice_research.drug;

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
}
