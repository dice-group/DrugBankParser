package org.dice_research.drug;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.dice_research.drug.vocab.DrugBank;

public class DrugResourceHandler extends RDFCreatingHandler {
    @Override
    public void endDrug() {
        Resource drug = DrugBank.getDrug(currentDrugID);
        addTriple(drug, RDF.type, DrugBank.Drug);
        addTriple(drug, DrugBank.id, ResourceFactory.createStringLiteral(currentDrugID));
    }
}
