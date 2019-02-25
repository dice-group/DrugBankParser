package org.dice_research.drug;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.dice_research.drug.vocab.DrugBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

// Not thread safe!
public class ProductHandler extends RDFCreatingHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHandler.class);

    private static final String PRODUCTS_TAG = "products";
    private static final String PRODUCT_TAG = "product";
    private static final String[] VALUE_TAGS = new String[] { "name", "labeller", "ndc-id", "ndc-product-code",
            "dpd-id", "ema-product-code", "ema-ma-number", "dosage-form", "strength", "route", "fda-application-number",
            "country", "source" };
    private static final Property[] VALUE_PROPERTIES = new Property[] { RDFS.label, DrugBank.productLabeller,
            DrugBank.ndcId, DrugBank.ndcProductCode, DrugBank.dpdId, DrugBank.emaProductCode, DrugBank.emaMaNumber,
            DrugBank.dosageFormDescription, DrugBank.strengthDescription, DrugBank.routeDescription,
            DrugBank.fdaApplicationNumber, DrugBank.country, DrugBank.source };
    private static final String[] BOOLEAN_TAGS = new String[] { "generic", "over-the-counter", "approved" };
    private static final Property[] BOOLEAN_PROPERTIES = new Property[] { DrugBank.generic, DrugBank.overTheCounter,
            DrugBank.approved };
    // TODO "started-marketing-on","ended-marketing-on"
    // DrugBank.startedMarketingOn = property("startedMarketingOn");
    // DrugBank.endedMarketingOn = property("endedMarketingOn");

    private Resource product = null;
    private Model productModel = null;
    private Map<String, Property> stringLiteralProps = new HashMap<>();
    private Map<String, Property> booleanLiteralProps = new HashMap<>();
    private boolean sawProducts = false;
    private StringLiteralHandler stringHandler;
    private BooleanLiteralHandler booleanHandler;

    public ProductHandler() {
        for (int i = 0; i < BOOLEAN_PROPERTIES.length; ++i) {
            booleanLiteralProps.put(BOOLEAN_TAGS[i], BOOLEAN_PROPERTIES[i]);
        }
        for (int i = 0; i < VALUE_PROPERTIES.length; ++i) {
            stringLiteralProps.put(VALUE_TAGS[i], VALUE_PROPERTIES[i]);
        }
        stringHandler = new StringLiteralHandler(this);
        booleanHandler = new BooleanLiteralHandler(this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
        case PRODUCTS_TAG: {
            sawProducts = true;
            break;
        }
        case PRODUCT_TAG: {
            if (sawProducts) {
                createProduct();
            }
            break;
        }
        default:
            if (product != null) {
                if (stringLiteralProps.containsKey(qName)) {
                    stringHandler.setProperty(stringLiteralProps.get(qName));
                    captureText(stringHandler);
                } else if (booleanLiteralProps.containsKey(qName)) {
                    booleanHandler.setProperty(booleanLiteralProps.get(qName));
                    captureText(booleanHandler);
                }
            }
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
        case PRODUCTS_TAG: {
            sawProducts = false;
            break;
        }
        case PRODUCT_TAG: {
            finalizeProduct();
            break;
        }
        default:
            break;
        }
        super.endElement(uri, localName, qName);
    }

    protected void createProduct() {
        StringBuilder builder = new StringBuilder();
        builder.append(DrugBank.getURI());
        builder.append("product-");
        builder.append(UUID.randomUUID());
        product = ResourceFactory.createResource(builder.toString());
        productModel = ModelFactory.createDefaultModel();
        productModel.add(product, RDF.type, DrugBank.Product);
    }

    protected void finalizeProduct() {
        // Create product URI
        NodeIterator nIter;
        StringBuilder finalUriBuilder = new StringBuilder();
        finalUriBuilder.append(DrugBank.getURI());
        // delete last char
        finalUriBuilder.deleteCharAt(finalUriBuilder.length() - 1);
        finalUriBuilder.append("/products");
        nIter = productModel.listObjectsOfProperty(product, DrugBank.source);
        if (nIter.hasNext()) {
            // Add the source
            finalUriBuilder.append('/');
            addToUri(nIter.next().toString(), finalUriBuilder);
        }
        finalUriBuilder.append('#');
        // Get the label
        nIter = productModel.listObjectsOfProperty(product, RDFS.label);
        if (nIter.hasNext()) {
            addToUri(nIter.next().toString(), finalUriBuilder);
        } else {
            LOGGER.warn("Couldn't find a label for product! Creating random URI.");
            // the product has no label
            finalUriBuilder.append("unknown-");
            finalUriBuilder.append(UUID.randomUUID());
        }
        Resource finalProduct = ResourceFactory.createResource(finalUriBuilder.toString());
        StmtIterator iterator = productModel.listStatements();
        Statement s;
        while (iterator.hasNext()) {
            s = iterator.next();
            if (s.getObject().isResource()) {
                addTriple(finalProduct, s.getPredicate(), s.getObject().asResource());
            } else if (s.getObject().isLiteral()) {
                addTriple(finalProduct, s.getPredicate(), s.getObject().asLiteral());
            } else {
                LOGGER.error("Couldn't process triple " + s + ". It will be ignored.");
            }
        }
        addTriple(DrugBank.getDrug(currentDrugID), DrugBank.product, finalProduct);
        product = null;
        productModel = null;
    }

    private void addToUri(String string, StringBuilder uriBuilder) {
        char c;
        for (int i = 0; i < string.length(); ++i) {
            c = string.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                uriBuilder.append(c);
            } else {
                switch (c) {
                case '-': // falls through
                case '+':
                case '*': {
                    uriBuilder.append(c);
                    break;
                }
                default: {
                    uriBuilder.append('_');
                }
                }
            }
        }
    }

    public void addTriple(Property property, Literal literal) {
        productModel.add(product, property, literal);
    }

    public static abstract class AbstractValueHandler implements Consumer<String> {

        protected ProductHandler productHandler;
        protected Property property = null;

        public AbstractValueHandler(ProductHandler productHandler) {
            this.productHandler = productHandler;
        }

        public void setProperty(Property property) {
            this.property = property;
        }

        @Override
        public void accept(String value) {
            if (value != null && !value.isEmpty()) {
                productHandler.addTriple(property, createLiteralNode(value));
            }
        }

        protected abstract Literal createLiteralNode(String value);
    }

    public static class StringLiteralHandler extends AbstractValueHandler {

        public StringLiteralHandler(ProductHandler productHandler) {
            super(productHandler);
        }

        @Override
        protected Literal createLiteralNode(String value) {
            return ResourceFactory.createStringLiteral(value);
        }
    }

    public static class BooleanLiteralHandler extends AbstractValueHandler {

        public BooleanLiteralHandler(ProductHandler productHandler) {
            super(productHandler);
        }

        @Override
        protected Literal createLiteralNode(String value) {
            return ResourceFactory.createTypedLiteral(value, XSDDatatype.XSDboolean);
        }
    }
}
