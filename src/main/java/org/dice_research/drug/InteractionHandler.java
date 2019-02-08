package org.dice_research.drug;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.dice_research.drug.vocab.DrugBank;
import org.xml.sax.Attributes;

public class InteractionHandler extends RDFCreatingHandler {

    private static final String DRUG_INTERACTIONS_TAG = "drug-interactions";
    private static final String DRUG_INTERACTION_TAG = "drug-interaction";
    private static final String DRUGBANK_TAG = "drugbank-id";
    private static final String DESCRIPTION_TAG = "description";

    private String interactingDrugId;
    private String interactionDescription;
    private boolean sawDrugInteractions = false;
    private boolean sawDrugInteraction = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
        case DRUG_INTERACTIONS_TAG: {
            sawDrugInteractions = true;
            break;
        }
        case DRUG_INTERACTION_TAG: {
            if (sawDrugInteractions) {
                sawDrugInteraction = true;
            }
            break;
        }
        case DRUGBANK_TAG: {
            if (sawDrugInteraction) {
                captureText(this::setInteractingDrugId);
            }
            break;
        }
        case DESCRIPTION_TAG: {
            if (sawDrugInteraction) {
                captureText(this::setInteractionDescription);
            }
            break;
        }
        default:
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
        case DRUG_INTERACTIONS_TAG: {
            sawDrugInteractions = false;
            break;
        }
        case DRUG_INTERACTION_TAG: {
            sawDrugInteraction = false;
            if (interactingDrugId != null) {
                storeDrugInteraction();
            }
            break;
        }
        default:
            break;
        }
        super.endElement(uri, localName, qName);
    }

    private void storeDrugInteraction() {
        StringBuilder builder = new StringBuilder();
        builder.append(DrugBank.getURI());
        builder.append("interaction-");
        builder.append(this.currentDrugID);
        builder.append('-');
        builder.append(interactingDrugId);
        Resource interaction = ResourceFactory.createResource(builder.toString());
        addTriple(interaction, RDF.type, DrugBank.DrugInteraction);
        if (interactionDescription != null) {
            addTriple(interaction, RDFS.comment, ResourceFactory.createLangLiteral(interactionDescription, "en"));
        }
        addTriple(interaction, DrugBank.interactingDrug1, DrugBank.getDrug(this.currentDrugID));
        addTriple(interaction, DrugBank.interactingDrug2, DrugBank.getDrug(interactingDrugId));
        interactingDrugId = null;
        interactionDescription = null;
    }

    private void setInteractingDrugId(String interactingDrugId) {
        this.interactingDrugId = interactingDrugId;
    }

    private void setInteractionDescription(String interactionDescription) {
        this.interactionDescription = interactionDescription;
    }
}
