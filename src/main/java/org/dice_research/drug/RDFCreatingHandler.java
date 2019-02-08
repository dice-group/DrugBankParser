package org.dice_research.drug;

import org.apache.jena.graph.Triple;
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
}
