package com.applichat.services;

import org.w3c.dom.Element;

import com.applichat.doa.NotificationDAO;

import java.util.List;

import javax.swing.text.Document;

public class NotificationService 
{

    private final NotificationDAO notificationDAO;
    protected Document document;

    public NotificationService() 
    {
        this.notificationDAO = new NotificationDAO();
    }

    public void ajouterNotification(Element notification) 
    {
        notificationDAO.ajouterNotification(notification);
    }

    public void modifierNotification(Element notificationMisAJour) 
    {
        notificationDAO.updateNotification(notificationMisAJour);
    }

    public void supprimerNotification(String id) 
    {
        notificationDAO.supprimerNotification(id);
    }

    public Element getNotificationById(String id) 
    {
        return notificationDAO.getNotificationById(id);
    }

    public List<Element> getNotificationsPourUtilisateur(String utilisateurId) 
    {
        return notificationDAO.getNotificationsPourUtilisateur(utilisateurId);
    }
}
