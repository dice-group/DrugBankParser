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
    public static final Resource Product = resource("Product");

    // Properties sorted alphabetically
    public static final Property approved = property("approved");
    public static final Property casNumber = property("casNumber");
    public static final Property clearanceDescription = property("clearanceDescription");
    public static final Property country = property("country");
    public static final Property dosageFormDescription = property("dosageFormDescription");
    public static final Property dpdId = property("dpdId");
    public static final Property emaMaNumber = property("emaMaNumber");
    public static final Property emaProductCode = property("emaProductCode");
    public static final Property endedMarketingOn = property("endedMarketingOn");
    public static final Property fdaApplicationNumber = property("fdaApplicationNumber");
    public static final Property generic = property("generic");
    public static final Property halfLifeDescription = property("halfLifeDescription");
    public static final Property id = property("id");
    public static final Property indication = property("indication");
    public static final Property indicationDescription = property("indicationDescription");
    public static final Property interactingDrug1 = property("interactingDrug1");
    public static final Property interactingDrug2 = property("interactingDrug2");
    public static final Property mechanismOfActionDescription = property("mechanismOfActionDescription");
    public static final Property ndcId = property("ndcId");
    public static final Property ndcProductCode = property("ndcProductCode");
    public static final Property overTheCounter = property("overTheCounter");
    public static final Property pharmacodynamicsDescription = property("pharmacodynamicsDescription");
    public static final Property product = property("product");
    public static final Property productLabeller = property("productLabeller");
    public static final Property routeDescription = property("routeDescription");
    public static final Property routeOfEliminationDescription = property("routeOfEliminationDescription");
    public static final Property source = property("source");
    public static final Property startedMarketingOn = property("startedMarketingOn");
    public static final Property stateDescription = property("stateDescription");
    public static final Property strengthDescription = property("strengthDescription");
    public static final Property toxicityDescription = property("toxicityDescription");
    public static final Property unii = property("unii");
    public static final Property volumeOfDistributionDescription = property("volumeOfDistributionDescription");
    
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
