package org.dice_research.drug;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.system.StreamRDF;

public class RDFCreatingHandler extends BaseHandler {

    private StreamRDF outputStream;

    public RDFCreatingHandler() {
        this.outputStream = null;
    }

    public RDFCreatingHandler(StreamRDF outputStream) {
        this.outputStream = outputStream;
    }

    public void setOutputStream(StreamRDF outputStream) {
        this.outputStream = outputStream;
    }

    public void addTriple(Triple triple) {
        outputStream.triple(triple);
    }

    public void addTriple(Resource s, Property p, Resource o) {
        addTriple(Triple.create(s.asNode(), p.asNode(), o.asNode()));
    }

    public void addTriple(Resource s, Property p, Literal o) {
        addTriple(Triple.create(s.asNode(), p.asNode(), o.asNode()));
    }
}
