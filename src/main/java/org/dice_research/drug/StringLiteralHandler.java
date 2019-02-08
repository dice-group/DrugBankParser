package org.dice_research.drug;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.dice_research.drug.vocab.DrugBank;
import org.xml.sax.Attributes;

public class StringLiteralHandler extends RDFCreatingHandler {

    private Property property;
    private String tagName;
    private String literalValue;
    private Integer tagLevel;

    public StringLiteralHandler(Property property, String tagName) {
        this(property, tagName, null);
    }

    public StringLiteralHandler(Property property, String tagName, Integer tagLevel) {
        this.property = property;
        this.tagName = tagName;
        this.tagLevel = tagLevel;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (((tagLevel == null) || (tagLevel == level)) && qName.equals(tagName)) {
            captureText(this::setLiteralValue);
        }
    }

    private void setLiteralValue(String value) {
        this.literalValue = value;
    }
    
    @Override
    public void endDrug() {
        addTriple(DrugBank.getDrug(currentDrugID), property, ResourceFactory.createLangLiteral(literalValue, "en"));
    }
}
