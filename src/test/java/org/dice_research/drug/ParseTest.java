package org.dice_research.drug;

import org.junit.Before;
import org.junit.Test;

public class ParseTest {

    private static final String TEST_FILE = "src/test/resources/example.xml";

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
    public void drugLabelHandler() throws Exception {
        Executor.execute(TEST_FILE, getOutputFile("labels.nt"), new DrugLabelHandler());
    }
}
