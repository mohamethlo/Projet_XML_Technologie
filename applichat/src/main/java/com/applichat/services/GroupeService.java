package com.applichat.services;


import com.applichat.doa.GroupeDAO;
import com.applichat.doa.UtilisateurDAO;
import com.applichat.exceptions.GroupeException;
import org.w3c.dom.Element;

import java.util.List;

public class GroupeService 
{

    private final GroupeDAO groupeDAO;
    private final UtilisateurDAO utilisateurDAO;

    public GroupeService() 
    {
        this.groupeDAO = new GroupeDAO();
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public void creerGroupe(Element groupe) 
    {
        String id = groupe.getAttribute("id");
        if (id == null || id.isEmpty()) 
        {
            throw new GroupeException("L'attribut 'id' du groupe est requis.");
        }

        Element creePar = (Element) groupe.getElementsByTagName("creePar").item(0);
        String utilisateurId = creePar.getAttribute("utilisateur");
        if (utilisateurDAO.getUtilisateurById(utilisateurId) == null) 
        {
            throw new GroupeException("Le créateur du groupe n'existe pas.");
        }

        if (groupeDAO.getGroupeById(id) != null) 
        {
            throw new GroupeException("Un groupe avec cet ID existe déjà.");
        }

        groupeDAO.ajouterGroupe(groupe);
    }

    public void supprimerGroupe(String groupeId) 
    {
        Element groupe = groupeDAO.getGroupeById(groupeId);
        if (groupe == null) {
            throw new GroupeException("Aucun groupe trouvé avec l'ID : " + groupeId);
        }
        groupeDAO.supprimerGroupe(groupeId);
    }

    public void renommerGroupe(String groupeId, String nouveauNom) 
    {
        Element groupe = groupeDAO.getGroupeById(groupeId);
        if (groupe == null) {
            throw new GroupeException("Groupe introuvable.");
        }

        Element meta = (Element) groupe.getElementsByTagName("meta").item(0);
        Element nom = (Element) meta.getElementsByTagName("nom").item(0);
        nom.setTextContent(nouveauNom);

        groupeDAO.updateGroupe(groupe);
    }

    public void ajouterMembre(String groupeId, Element membre) 
    {
        String utilisateurId = membre.getAttribute("utilisateur");
        if (utilisateurDAO.getUtilisateurById(utilisateurId) == null) 
        {
            throw new GroupeException("Utilisateur à ajouter non trouvé.");
        }

        groupeDAO.ajouterMembreAuGroupe(groupeId, membre);
    }

    public void retirerMembre(String groupeId, String utilisateurId) 
    {
        groupeDAO.supprimerMembreDuGroupe(groupeId, utilisateurId);
    }

    public List<Element> getGroupesParUtilisateur(String utilisateurId) 
    {
        return groupeDAO.getGroupesParUtilisateur(utilisateurId);
    }
}

