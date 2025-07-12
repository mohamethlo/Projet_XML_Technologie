package com.applichat.services;


import org.w3c.dom.Element;

import com.applichat.doa.UtilisateurDAO;
import com.applichat.exceptions.UtilisateurException;

import java.util.List;

public class UtilisateurService 
{

    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService() 
    {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public void creerUtilisateur(Element utilisateur) 
    {
        String id = utilisateur.getAttribute("id");
        if (id == null || id.isEmpty()) 
        {
            throw new UtilisateurException("ID de l'utilisateur requis.");
        }

        if (utilisateurDAO.getUtilisateurById(id) != null) 
        {
            throw new UtilisateurException("Un utilisateur avec cet ID existe déjà.");
        }

        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    public Element getUtilisateurParId(String id) 
    {
        Element utilisateur = utilisateurDAO.getUtilisateurById(id);
        if (utilisateur == null) 
        {
            throw new UtilisateurException("Utilisateur introuvable avec l’ID : " + id);
        }
        return utilisateur;
    }

    public List<Element> listerUtilisateurs() 
    {
        return utilisateurDAO.getAllUtilisateurs();
    }

    public void modifierUtilisateur(Element utilisateur) 
    {
        String id = utilisateur.getAttribute("id");
        if (utilisateurDAO.getUtilisateurById(id) == null) 
        {
            throw new UtilisateurException("Impossible de modifier : utilisateur inexistant.");
        }
    }


    public void supprimerUtilisateur(String id) 
    {
        if (utilisateurDAO.getUtilisateurById(id) == null) 
        {
            throw new UtilisateurException("Suppression impossible : utilisateur non trouvé.");
        }
        utilisateurDAO.supprimerUtilisateur(id);
    }
}
