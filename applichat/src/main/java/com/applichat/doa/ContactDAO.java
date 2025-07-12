package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class ContactDAO 
{

    private static final String CONTACTS_TAG = "contacts";
    private static final String CONTACT_TAG = "contact";

    private Document doc;

    public ContactDAO() 
    {
        try {
            this.doc = XMLManager.getDocument();
        } catch (XMLException e) {
            e.printStackTrace();
        }
    }

    public List<Element> getAllContacts() 
    {
        List<Element> contacts = new ArrayList<>();
        NodeList contactNodes = doc.getElementsByTagName("contact");
        for (int i = 0; i < contactNodes.getLength(); i++) 
        {
            Element el = (Element) contactNodes.item(i);
            contacts.add(el);
        }
        return contacts;
    }

 
    public Element getContactById(String id) 
    {
        NodeList contactNodes = doc.getElementsByTagName(CONTACT_TAG);
        for (int i = 0; i < contactNodes.getLength(); i++) 
        {
            Element contact = (Element) contactNodes.item(i);
            if (contact.getAttribute("id").equals(id)) 
            {
                return contact;
            }
        }
        return null;
    }


    public void ajouterContact(Element contact) 
    {
        Element racine = XMLManager.getRootElement();
        Node contactsNode = racine.getElementsByTagName(CONTACTS_TAG).item(0);
        if (contactsNode == null) 
        {
            contactsNode = doc.createElement(CONTACTS_TAG);
            racine.appendChild(contactsNode);
        }
        contactsNode.appendChild(contact);
        XMLManager.saveXML();
    }


    public void supprimerContact(String id) 
    {
        Element contact = getContactById(id);
        if (contact != null) 
        {
            contact.getParentNode().removeChild(contact);
            XMLManager.saveXML();
        } 
        else 
        {
            throw new XMLException("Contact introuvable avec l'id : " + id);
        }
    }

    public void modifierContact(String id, Element contactMisAJour) 
    {
        Element contact = getContactById(id);
        if (contact != null) 
        {
            Node parent = contact.getParentNode();
            parent.replaceChild(contactMisAJour, contact);
            XMLManager.saveXML();
        } 
        else 
        {
            throw new XMLException("Impossible de modifier : contact introuvable avec l'id " + id);
        }
    }

    public List<Element> getContactsParUtilisateur(String utilisateurId) 
    {
        List<Element> contactsTrouves = new ArrayList<>();
        NodeList contactList = doc.getElementsByTagName("contact");

        for (int i = 0; i < contactList.getLength(); i++) 
        {
            Element contact = (Element) contactList.item(i);
            if (utilisateurId.equals(contact.getAttribute("utilisateur"))) 
            {
                contactsTrouves.add(contact);
            }
        }

        return contactsTrouves;
    }


}

