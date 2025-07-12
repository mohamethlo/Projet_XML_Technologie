package com.applichat.services;



import org.w3c.dom.Element;

import com.applichat.doa.ContactDAO;
import com.applichat.doa.UtilisateurDAO;
import com.applichat.exceptions.ContactException;

import java.util.List;

public class ContactService 
{

    private final ContactDAO contactDAO;
    private final UtilisateurDAO utilisateurDAO;

    public ContactService() 
    {
        this.contactDAO = new ContactDAO();
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public void ajouterContact(Element contact) 
    {
        String utilisateurId = contact.getAttribute("utilisateur");
        String avecId = contact.getAttribute("avec");

        if (utilisateurId.isEmpty() || avecId.isEmpty()) 
        {
            throw new ContactException("Les attributs 'utilisateur' et 'avec' sont obligatoires.");
        }

        if (utilisateurDAO.getUtilisateurById(utilisateurId) == null) 
        {
            throw new ContactException("Utilisateur source introuvable : " + utilisateurId);
        }

        if (utilisateurDAO.getUtilisateurById(avecId) == null) 
        {
            throw new ContactException("Utilisateur cible introuvable : " + avecId);
        }

        if (contactDAO.getContactById(contact.getAttribute("id")) != null) 
        {
            throw new ContactException("Un contact avec cet ID existe déjà.");
        }

        contactDAO.ajouterContact(contact);
    }

    public Element rechercherContactParId(String id) 
    {
        Element contact = contactDAO.getContactById(id);
        if (contact == null) {
            throw new ContactException("Contact introuvable avec l'ID : " + id);
        }
        return contact;
    }

    public List<Element> getContactsParUtilisateur(String utilisateurId) 
    {
        return contactDAO.getContactsParUtilisateur(utilisateurId);
    }

    public void supprimerContact(String id) 
    {
        Element contact = contactDAO.getContactById(id);
        if (contact == null) {
            throw new ContactException("Contact introuvable pour suppression.");
        }
        contactDAO.supprimerContact(id);
    }
}

