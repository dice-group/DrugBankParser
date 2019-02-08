package org.dice_research.drug;

import org.apache.jena.vocabulary.RDFS;

public class DrugLabelHandler extends StringLiteralHandler {
    public DrugLabelHandler() {
        super(RDFS.label, "name", 2);
    }
}
