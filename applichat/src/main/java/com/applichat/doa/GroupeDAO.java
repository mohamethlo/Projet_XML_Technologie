package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class GroupeDAO {

    private static final String GROUPES_TAG = "groupes";
    private static final String GROUPE_TAG = "groupe";

    private final Document doc;

    public GroupeDAO() {
        this.doc = XMLManager.getDocument();
    }

    /**
     * Récupérer tous les groupes
     */
    public List<Element> getAllGroupes() {
        List<Element> groupes = new ArrayList<>();
        NodeList groupeNodes = doc.getElementsByTagName(GROUPE_TAG);
        for (int i = 0; i < groupeNodes.getLength(); i++) {
            Node node = groupeNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                groupes.add((Element) node);
            }
        }
        return groupes;
    }

    /**
     * Récupérer un groupe par son ID
     */
    public Element getGroupeById(String id) {
        NodeList groupeNodes = doc.getElementsByTagName(GROUPE_TAG);
        for (int i = 0; i < groupeNodes.getLength(); i++) {
            Element groupe = (Element) groupeNodes.item(i);
            if (groupe.getAttribute("id").equals(id)) {
                return groupe;
            }
        }
        return null;
    }

    /**
     * Ajouter un groupe
     */
    public void ajouterGroupe(Element groupe) {
        Element racine = XMLManager.getRootElement();
        Node groupesNode = racine.getElementsByTagName(GROUPES_TAG).item(0);
        if (groupesNode == null) {
            groupesNode = doc.createElement(GROUPES_TAG);
            racine.appendChild(groupesNode);
        }
        groupesNode.appendChild(groupe);
        XMLManager.saveXML();
    }

    /**
     * Supprimer un groupe
     */
    public void supprimerGroupe(String id) {
        Element groupe = getGroupeById(id);
        if (groupe != null) {
            groupe.getParentNode().removeChild(groupe);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Groupe introuvable avec l'id : " + id);
        }
    }

    /**
     * Modifier un groupe
     */
    public void modifierGroupe(String id, Element groupeMisAJour) {
        Element groupe = getGroupeById(id);
        if (groupe != null) {
            Node parent = groupe.getParentNode();
            parent.replaceChild(groupeMisAJour, groupe);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Impossible de modifier : groupe introuvable avec l'id " + id);
        }
    }
}
