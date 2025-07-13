package com.applichat.controllers;


import org.w3c.dom.Element;

import com.applichat.services.UtilisateurService;

import java.util.List;

public class UtilisateurController 
{

    private final UtilisateurService utilisateurService;

    public UtilisateurController() 
    {
        this.utilisateurService = new UtilisateurService();
    }

    public void creerUtilisateur(Element utilisateur) 
    {
        utilisateurService.creerUtilisateur(utilisateur);
    }

    public void modifierUtilisateur(String id, Element utilisateurMisAJour) 
    {
        utilisateurService.modifierUtilisateur(utilisateurMisAJour);
    }

    public void supprimerUtilisateur(String id) 
    {
        utilisateurService.supprimerUtilisateur(id);
    }

    public Element getUtilisateurById(String id) 
    {
        return utilisateurService.getUtilisateurParId(id);
    }

    public List<Element> getTousUtilisateurs() 
    {
        return utilisateurService.listerUtilisateurs();
    }
}
