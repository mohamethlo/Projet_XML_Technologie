package com.applichat.services;


import com.applichat.doa.ContactDAO;
import org.w3c.dom.Element;

import java.util.List;

public class ContactService 
{

    private final ContactDAO contactDAO;

    public ContactService() 
    {
        this.contactDAO = new ContactDAO();
    }

    public void ajouterContact(Element contact) 
    {
        contactDAO.ajouterContact(contact);
    }

    public void supprimerContact(String id) 
    {
        contactDAO.supprimerContact(id);
    }

    public List<Element> getContactsParUtilisateur(String utilisateurId) 
    {
        return contactDAO.getContactsParUtilisateur(utilisateurId);
    }

    public void modifierContact(String id, Element contactMisAJour) 
    {
        contactDAO.modifierContact(id, contactMisAJour);
    }

    public Element getContactById(String id) 
    {
        return contactDAO.getContactById(id);
    }
}
