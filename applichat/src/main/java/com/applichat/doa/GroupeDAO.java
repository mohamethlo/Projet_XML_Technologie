package com.applichat.doa;


import com.applichat.exceptions.GroupeException;
import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class GroupeDAO extends XMLManager 
{

    private static final String GROUPES_TAG = "groupes";
    private static final String GROUPE_TAG = "groupe";

    private final Document doc;

    public GroupeDAO() 
    {
        this.doc = XMLManager.getDocument();
    }

 
    public List<Element> getAllGroupes() 
    {
        List<Element> groupes = new ArrayList<>();
        NodeList groupeNodes = doc.getElementsByTagName(GROUPE_TAG);
        for (int i = 0; i < groupeNodes.getLength(); i++) 
        {
            Node node = groupeNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                groupes.add((Element) node);
            }
        }
        return groupes;
    }


    public Element getGroupeById(String id) 
    {
        NodeList groupeNodes = doc.getElementsByTagName(GROUPE_TAG);
        for (int i = 0; i < groupeNodes.getLength(); i++) 
        {
            Element groupe = (Element) groupeNodes.item(i);
            if (groupe.getAttribute("id").equals(id)) 
            {
                return groupe;
            }
        }
        return null;
    }

    public void ajouterGroupe(Element groupe) 
    {
        Element racine = XMLManager.getRootElement();
        Node groupesNode = racine.getElementsByTagName(GROUPES_TAG).item(0);
        if (groupesNode == null) 
        {
            groupesNode = doc.createElement(GROUPES_TAG);
            racine.appendChild(groupesNode);
        }
        groupesNode.appendChild(groupe);
        XMLManager.saveXML();
    }


    public void supprimerGroupe(String id) 
    {
        Element groupe = getGroupeById(id);
        if (groupe != null) 
        {
            groupe.getParentNode().removeChild(groupe);
            XMLManager.saveXML();
        } 
        else 
        {
            throw new XMLException("Groupe introuvable avec l'id : " + id);
        }
    }


    public void modifierGroupe(String id, Element groupeMisAJour) 
    {
        Element groupe = getGroupeById(id);
        if (groupe != null) 
        {
            Node parent = groupe.getParentNode();
            parent.replaceChild(groupeMisAJour, groupe);
            XMLManager.saveXML();
        } 
        else 
        {
            throw new XMLException("Impossible de modifier : groupe introuvable avec l'id " + id);
        }
    }

    public void updateGroupe(Element groupeMisAJour) 
    {
        String id = groupeMisAJour.getAttribute("id");
        Element ancien = getGroupeById(id);
        if (ancien != null) 
        {
            Node parent = ancien.getParentNode();
            parent.replaceChild(doc.importNode(groupeMisAJour, true), ancien);
            saveXML();
        } 
        else 
        {
            throw new GroupeException("Impossible de modifier : groupe introuvable avec l'id " + id);
        }
    }

    public void ajouterMembreAuGroupe(String groupeId, Element membre) 
    {
        Element groupe = getGroupeById(groupeId);
        if (groupe == null) 
        {
            throw new GroupeException("Groupe non trouvé.");
        }

        Element membresNode = (Element) groupe.getElementsByTagName("membres").item(0);
        membresNode.appendChild(doc.importNode(membre, true));
        saveXML();
    }

    public void supprimerMembreDuGroupe(String groupeId, String utilisateurId) 
    {
        Element groupe = getGroupeById(groupeId);
        if (groupe == null) 
        {
            throw new GroupeException("Groupe non trouvé.");
        }

        Element membresNode = (Element) groupe.getElementsByTagName("membres").item(0);
        NodeList membres = membresNode.getElementsByTagName("membre");

        for (int i = 0; i < membres.getLength(); i++) 
        {
            Element membre = (Element) membres.item(i);
            if (utilisateurId.equals(membre.getAttribute("utilisateur"))) 
            {
                membresNode.removeChild(membre);
                saveXML();
                return;
            }
        }

        throw new GroupeException("Membre non trouvé dans le groupe.");
    }

    public List<Element> getGroupesParUtilisateur(String utilisateurId) 
    {
        List<Element> resultats = new ArrayList<>();
        NodeList groupes = doc.getElementsByTagName("groupe");

        for (int i = 0; i < groupes.getLength(); i++) 
        {
            Element groupe = (Element) groupes.item(i);
            Element membres = (Element) groupe.getElementsByTagName("membres").item(0);
            NodeList listeMembres = membres.getElementsByTagName("membre");

            for (int j = 0; j < listeMembres.getLength(); j++) 
            {
                Element membre = (Element) listeMembres.item(j);
                if (utilisateurId.equals(membre.getAttribute("utilisateur"))) 
                {
                    resultats.add(groupe);
                    break;
                }
            }
        }

        return resultats;
    }


}
