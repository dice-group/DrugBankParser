package org.dice_research.drug.vocab;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class DrugBank {

    protected static final String uri = "http://dice-research.org/ns/drugbank#";

    /**
     * returns the URI for this schema
     *
     * @return the URI for this schema
     */
    public static String getURI() {
        return uri;
    }

    protected static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    protected static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }

    // Resources sorted alphabetically
    public static final Resource Drug = resource("Drug");
    public static final Resource DrugInteraction = resource("DrugInteraction");

    // Properties sorted alphabetically
    public static final Property id = property("id");
    public static final Property indication = property("indication");
    public static final Property indicationDescription = property("indicationDescription");
    public static final Property interactingDrug1 = property("interactingDrug1");
    public static final Property interactingDrug2 = property("interactingDrug2");

    /**
     * returns the drug resource given its ID
     *
     * @param drugID the drug ID
     * @return the resource
     */
    public static Resource getDrug(String drugID) {
        return resource("drug-" + drugID);
    }

}
