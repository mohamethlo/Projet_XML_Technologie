package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLManager {

    private static final String XML_FILE_PATH = "src/main/resources/applichat.xml";
    private static final String DTD_FILE_PATH = "src/main/resources/applichat.dtd";

    private static Document document;

    static {
        loadXML();
    }

    public static void loadXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true); // Active la validation DTD
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler() {
                public void error(org.xml.sax.SAXParseException e) {
                    throw new RuntimeException("Erreur de validation XML : " + e.getMessage());
                }
            });

            document = builder.parse(new File(XML_FILE_PATH));
        } catch (Exception e) {
            throw new XMLException("Erreur lors du chargement du fichier XML : " + e.getMessage(), e);
        }
    }

    public static Document getDocument() {
        if (document == null) {
            loadXML();
        }
        return document;
    }

    public static void saveXML() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new XMLException("Erreur lors de la sauvegarde du fichier XML : " + e.getMessage(), e);
        }
    }

    // Utilitaires pratiques
    public static Element getRootElement() {
        return document.getDocumentElement();
    }
}
