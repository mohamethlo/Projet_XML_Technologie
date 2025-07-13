package com.applichat.controllers;


import com.applichat.services.MessageService;
import org.w3c.dom.Element;

import java.util.List;

public class MessageController 
{

    private final MessageService messageService;

    public MessageController() 
    {
        this.messageService = new MessageService();
    }

    public void envoyerMessage(Element message) 
    {
        messageService.envoyerMessage(message);
    }

    public void modifierMessage(String id, Element messageMisAJour) 
    {
        messageService.modifierMessage(messageMisAJour);
    }

    public void supprimerMessage(String id) 
    {
        messageService.supprimerMessage(id);
    }

    public Element getMessageById(String id) 
    {
        return messageService.getMessageById(id);
    }

    public List<Element> getMessagesParUtilisateur(String utilisateurId) 
    {
        return messageService.getMessagesParUtilisateur(utilisateurId);
    }


}
