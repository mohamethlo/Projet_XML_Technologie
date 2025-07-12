package com.applichat.doa;


import com.applichat.exceptions.XMLException;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final String NOTIFICATIONS_TAG = "notificationsRacine";
    private static final String NOTIFICATION_TAG = "notification";

    private final Document doc;

    public NotificationDAO() {
        this.doc = XMLManager.getDocument();
    }

    /**
     * Récupère toutes les notifications
     */
    public List<Element> getAllNotifications() {
        List<Element> notifications = new ArrayList<>();
        NodeList notifNodes = doc.getElementsByTagName(NOTIFICATION_TAG);
        for (int i = 0; i < notifNodes.getLength(); i++) {
            Node node = notifNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                notifications.add((Element) node);
            }
        }
        return notifications;
    }

    /**
     * Recherche une notification par ID
     */
    public Element getNotificationById(String id) {
        NodeList notifNodes = doc.getElementsByTagName(NOTIFICATION_TAG);
        for (int i = 0; i < notifNodes.getLength(); i++) {
            Element notif = (Element) notifNodes.item(i);
            if (notif.getAttribute("id").equals(id)) {
                return notif;
            }
        }
        return null;
    }

    /**
     * Ajoute une nouvelle notification
     */
    public void ajouterNotification(Element notification) {
        Element racine = XMLManager.getRootElement();
        Node notifNode = racine.getElementsByTagName(NOTIFICATIONS_TAG).item(0);

        if (notifNode == null) {
            notifNode = doc.createElement(NOTIFICATIONS_TAG);
            racine.appendChild(notifNode);
        }

        notifNode.appendChild(notification);
        XMLManager.saveXML();
    }

    /**
     * Supprime une notification
     */
    public void supprimerNotification(String id) {
        Element notif = getNotificationById(id);
        if (notif != null) {
            notif.getParentNode().removeChild(notif);
            XMLManager.saveXML();
        } else {
            throw new XMLException("Notification introuvable avec l'id : " + id);
        }
    }

    /**
     * Marque une notification comme lue
     */
    public void marquerCommeLue(String id) {
        Element notif = getNotificationById(id);
        if (notif != null) {
            notif.setAttribute("lue", "true");
            XMLManager.saveXML();
        } else {
            throw new XMLException("Impossible de marquer comme lue : notification non trouvée.");
        }
    }
}
