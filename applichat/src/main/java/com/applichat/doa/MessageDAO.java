package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    private static final String MESSAGES_TAG = "messages";
    private static final String MESSAGE_TAG = "message";

    private final Document doc;

    public MessageDAO() {
        this.doc = XMLManager.getDocument();
    }

    /**
     * Récupérer tous les messages
     */
    public List<Element> getAllMessages() {
        List<Element> messages = new ArrayList<>();
        NodeList msgNodes = doc.getElementsByTagName(MESSAGE_TAG);
        for (int i = 0; i < msgNodes.getLength(); i++) {
            Node node = msgNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                messages.add((Element) node);
            }
        }
        return messages;
    }

    /**
     * Rechercher un message par ID
     */
    public Element getMessageById(String id) {
        NodeList msgNodes = doc.getElementsByTagName(MESSAGE_TAG);
        for (int i = 0; i < msgNodes.getLength(); i++) {
            Element msg = (Element) msgNodes.item(i);
            if (msg.getAttribute("id").equals(id)) {
                return msg;
            }
        }
        return null;
    }

    /**
     * Ajouter un message
     */
    public void ajouterMessage(Element message) {
        Element racine = XMLManager.getRootElement();
        Node messagesNode = racine.getElementsByTagName(MESSAGES_TAG).item(0);

        if (messagesNode == null) {
            messagesNode = doc.createElement(MESSAGES_TAG);
            racine.appendChild(messagesNode);
        }

        messagesNode.appendChild(message);
        XMLManager.saveXML();
    }

    /**
     * Supprimer un message
     */
    public void supprimerMessage(String id) {
        Element message = getMessageById(id);
        if (message != null) {
            message.getParentNode().removeChild(message);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Message introuvable avec l'id : " + id);
        }
    }

    /**
     * Modifier un message existant
     */
    public void modifierMessage(String id, Element nouveauMessage) {
        Element ancien = getMessageById(id);
        if (ancien != null) {
            Node parent = ancien.getParentNode();
            parent.replaceChild(nouveauMessage, ancien);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Impossible de modifier : message introuvable avec l'id " + id);
        }
    }
}
