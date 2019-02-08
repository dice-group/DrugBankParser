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

    // Properties sorted alphabetically
    public static final Property indication = property("indication");
    public static final Property indicationDescription = property("indicationDescription");

}
