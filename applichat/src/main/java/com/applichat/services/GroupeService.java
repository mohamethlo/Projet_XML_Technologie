package com.applichat.services;


import com.applichat.doa.GroupeDAO;
import org.w3c.dom.Element;

import java.util.List;

public class GroupeService 
{

    private final GroupeDAO groupeDAO;

    public GroupeService() 
    {
        this.groupeDAO = new GroupeDAO();
    }

    public void ajouterGroupe(Element groupe) 
    {
        groupeDAO.ajouterGroupe(groupe);
    }

    public void modifierGroupe(String id, Element groupeMisAJour) 
    {
        groupeDAO.modifierGroupe(id, groupeMisAJour);
    }

    public void supprimerGroupe(String id) 
    {
        groupeDAO.supprimerGroupe(id);
    }

    public Element getGroupeById(String id) 
    {
        return groupeDAO.getGroupeById(id);
    }

    public List<Element> getTousGroupes() 
    {
        return groupeDAO.getAllGroupes();
    }
}
