package com.applichat.controllers;

import com.applichat.services.NotificationService;
import org.w3c.dom.Element;

import java.util.List;

public class NotificationController 
{

    private final NotificationService notificationService;

    public NotificationController() 
    {
        this.notificationService = new NotificationService();
    }

    public void ajouterNotification(Element notification) {
        notificationService.ajouterNotification(notification);
    }

    public void supprimerNotification(String id) {
        notificationService.supprimerNotification(id);
    }

    public Element getNotificationById(String id) {
        return notificationService.getNotificationById(id);
    }

    public List<Element> getNotificationsPourUtilisateur(String utilisateurId) {
        return notificationService.getNotificationsPourUtilisateur(utilisateurId);
    }
}
