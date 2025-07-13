package com.applichat.controllers;

import com.applichat.services.GroupeService;
import org.w3c.dom.Element;

import java.util.List;

public class GroupeController 
{

    private final GroupeService groupeService;

    public GroupeController() 
    {
        this.groupeService = new GroupeService();
    }

    public void ajouterGroupe(Element groupe) 
    {
        groupeService.ajouterGroupe(groupe);
    }

    public void modifierGroupe(String id, Element groupeMisAJour) 
    {
        groupeService.modifierGroupe(id, groupeMisAJour);
    }

    public void supprimerGroupe(String id) 
    {
        groupeService.supprimerGroupe(id);
    }

    public Element getGroupeById(String id) 
    {
        return groupeService.getGroupeById(id);
    }

    public List<Element> getTousGroupes() {
        return groupeService.getTousGroupes();
    }
}
