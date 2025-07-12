package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    private static final String UTILISATEURS_TAG = "utilisateurs";
    private static final String UTILISATEUR_TAG = "utilisateur";

    private Document doc;

    public UtilisateurDAO() {
        this.doc = XMLManager.getDocument();
    }

    /**
     * Retourne tous les éléments utilisateur
     */
    public List<Element> getAllUtilisateurs() {
        List<Element> utilisateurs = new ArrayList<>();
        Element racine = XMLManager.getRootElement();
        NodeList utilisateursNode = racine.getElementsByTagName(UTILISATEURS_TAG);

        if (utilisateursNode.getLength() > 0) {
            Node utilisateursParent = utilisateursNode.item(0);
            NodeList liste = utilisateursParent.getChildNodes();

            for (int i = 0; i < liste.getLength(); i++) {
                Node node = liste.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(UTILISATEUR_TAG)) {
                    utilisateurs.add((Element) node);
                }
            }
        }
        return utilisateurs;
    }

    /**
     * Recherche un utilisateur par ID
     */
    public Element getUtilisateurById(String id) {
        NodeList utilisateurs = doc.getElementsByTagName(UTILISATEUR_TAG);
        for (int i = 0; i < utilisateurs.getLength(); i++) {
            Element u = (Element) utilisateurs.item(i);
            if (u.getAttribute("id").equals(id)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Ajoute un nouvel utilisateur (élément déjà préparé)
     */
    public void ajouterUtilisateur(Element utilisateur) {
        Element racine = XMLManager.getRootElement();
        Node utilisateurs = racine.getElementsByTagName(UTILISATEURS_TAG).item(0);
        utilisateurs.appendChild(utilisateur);
        XMLManager.saveXML();
    }

    /**
     * Supprime un utilisateur par ID
     */
    public void supprimerUtilisateur(String id) {
        Element utilisateur = getUtilisateurById(id);
        if (utilisateur != null) {
            utilisateur.getParentNode().removeChild(utilisateur);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Utilisateur introuvable avec l'id : " + id);
        }
    }

    /**
     * Met à jour un utilisateur existant (remplace le nœud)
     */
    public void modifierUtilisateur(String id, Element utilisateurMisAJour) {
        Element ancien = getUtilisateurById(id);
        if (ancien != null) {
            Node parent = ancien.getParentNode();
            parent.replaceChild(utilisateurMisAJour, ancien);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Impossible de modifier : utilisateur introuvable avec l'id " + id);
        }
    }
}

