package com.applichat.controllers;


import com.applichat.services.ContactService;
import org.w3c.dom.Element;

import java.util.List;

public class ContactController 
{

    private final ContactService contactService;

    public ContactController() 
    {
        this.contactService = new ContactService();
    }

    public void ajouterContact(Element contact) 
    {
        contactService.ajouterContact(contact);
    }

    public void modifierContact(String id, Element contactMisAJour) 
    {
        contactService.modifierContact(id, contactMisAJour);
    }

    public void supprimerContact(String id) 
    {
        contactService.supprimerContact(id);
    }

    public Element getContactById(String id) 
    {
        return contactService.getContactById(id);
    }

    public List<Element> getContactsParUtilisateur(String utilisateurId) 
    {
        return contactService.getContactsParUtilisateur(utilisateurId);
    }
}

