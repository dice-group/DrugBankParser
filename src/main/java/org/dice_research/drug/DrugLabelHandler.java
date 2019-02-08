package org.dice_research.drug;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.dice_research.drug.vocab.DrugBank;
import org.xml.sax.Attributes;

public class DrugLabelHandler extends RDFCreatingHandler {
    private String name = null;

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        super.startElement(uri, localName, qName, attributes);

        if (currentDrugID != null && level == 2 && qName.equals("name")) {
            captureText(s -> name = s);
        }
    }

    public void endDrug() {
        Resource drug = DrugBank.getDrug(currentDrugID);
        addTriple(drug, RDF.type, DrugBank.Drug);
        addTriple(drug, DrugBank.id, ResourceFactory.createStringLiteral(currentDrugID));
        addTriple(drug, RDFS.label, ResourceFactory.createLangLiteral(name, "en"));
    }
}
