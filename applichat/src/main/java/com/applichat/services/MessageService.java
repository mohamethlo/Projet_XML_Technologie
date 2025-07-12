package com.applichat.services;

import com.applichat.doa.MessageDAO;
import org.w3c.dom.Element;

import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO;

    public MessageService() 
    {
        this.messageDAO = new MessageDAO();
    }

    public void envoyerMessage(Element message) 
    {
        messageDAO.ajouterMessage(message);
    }

    public void modifierMessage(Element messageMisAJour) 
    {
        messageDAO.updateMessage(messageMisAJour);
    }


    public void supprimerMessage(String id) 
    {
        messageDAO.supprimerMessage(id);
    }

    public List<Element> getMessagesConversation(String deId, String aId) 
    {
        return messageDAO.getMessagesConversation(deId, aId);
    }

    public List<Element> getMessagesParUtilisateur(String utilisateurId) 
    {
        return messageDAO.getMessagesParUtilisateur(utilisateurId);
    }
}

