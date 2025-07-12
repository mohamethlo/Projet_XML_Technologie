package com.applichat.doa;

import com.applichat.exceptions.NotificationException;
import org.w3c.dom.*;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO extends XMLManager 
{

    private static final String ELEMENT_NOTIFICATIONS = "notificationsRacine";
    private static final String ELEMENT_NOTIFICATION = "notification";
    protected Document document;

    public NotificationDAO() 
    {
        super();
    }

    public void ajouterNotification(Element notification) 
    {
        Element racine = document.getDocumentElement();
        NodeList listeNotif = racine.getElementsByTagName(ELEMENT_NOTIFICATIONS);

        if (listeNotif.getLength() == 0) 
        {
            Element notifications = document.createElement(ELEMENT_NOTIFICATIONS);
            notifications.appendChild(notification);
            racine.appendChild(notifications);
        } 
        else 
        {
            listeNotif.item(0).appendChild(notification);
        }
        saveXML();
    }

    public void updateNotification(Element notificationMisAJour) 
    {
        String id = notificationMisAJour.getAttribute("id");
        Element ancienne = getNotificationById(id);

        if (ancienne != null) 
        {
            Node parent = ancienne.getParentNode();
            parent.replaceChild(notificationMisAJour, ancienne);
            saveXML();
        } 
        else 
        {
            throw new NotificationException("Notification introuvable avec l'id : " + id);
        }
    }

    public void supprimerNotification(String id) 
    {
        Element notif = getNotificationById(id);
        if (notif != null) {
            Node parent = notif.getParentNode();
            parent.removeChild(notif);
            saveXML();
        } 
        else 
        {
            throw new NotificationException("Impossible de supprimer : notification introuvable avec l'id " + id);
        }
    }

    public Element getNotificationById(String id) 
    {
        try 
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "//notification[@id='" + id + "']";
            Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) 
            {
                return (Element) node;
            }
        } 
        catch (XPathExpressionException e) 
        {
            throw new NotificationException("Erreur XPath pour getNotificationById", e);
        }
        return null;
    }

    public List<Element> getNotificationsPourUtilisateur(String utilisateurId) 
    {
        List<Element> result = new ArrayList<>();
        try 
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "//notification[@pour='" + utilisateurId + "']";
            NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) 
            {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) 
                {
                    result.add((Element) node);
                }
            }
        } 
        catch (XPathExpressionException e) 
        {
            throw new NotificationException("Erreur XPath pour getNotificationsPourUtilisateur", e);
        }
        return result;
    }
}
