package com.applichat.doa;


import com.applichat.exceptions.MessageException;
import org.w3c.dom.*;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends XMLManager 
{

    protected Document document;
    private static final String ELEMENT_MESSAGES = "messages";
    private static final String ELEMENT_MESSAGE = "message";

    public MessageDAO() 
    {
        super();
    }

    public void ajouterMessage(Element nouveauMessage) 
    {
        Element racine = document.getDocumentElement();
        NodeList messagesList = racine.getElementsByTagName(ELEMENT_MESSAGES);

        if (messagesList.getLength() == 0) 
        {
            Element messages = document.createElement(ELEMENT_MESSAGES);
            racine.appendChild(messages);
            messages.appendChild(nouveauMessage);
        } 
        else 
        {
            messagesList.item(0).appendChild(nouveauMessage);
        }
        saveXML();
    }

    public void updateMessage(Element messageMisAJour) 
    {
        String id = messageMisAJour.getAttribute("id");
        Element ancienMessage = getMessageById(id);

        if (ancienMessage != null) 
        {
            Node parent = ancienMessage.getParentNode();
            parent.replaceChild(messageMisAJour, ancienMessage);
            saveXML();
        } 
        else 
        {
            throw new MessageException("Impossible de modifier : message introuvable avec l'id " + id);
        }
    }

    public void supprimerMessage(String id) 
    {
        Element message = getMessageById(id);
        if (message != null) 
        {
            Node parent = message.getParentNode();
            parent.removeChild(message);
            saveXML();
        } else 
        {
            throw new MessageException("Impossible de supprimer : message introuvable avec l'id " + id);
        }
    }

    public Element getMessageById(String id) 
    {
        try 
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "//message[@id='" + id + "']";
            Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) 
            {
                return (Element) node;
            }
        } 
        catch (XPathExpressionException e) 
        {
            throw new MessageException("Erreur XPath lors de la recherche du message avec id " + id, e);
        }
        return null;
    }

    public List<Element> getMessagesConversation(String deId, String aId) 
    {
        List<Element> result = new ArrayList<>();
        try 
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expr = "//message[@de='" + deId + "' and @a='" + aId + "'] | //message[@de='" + aId + "' and @a='" + deId + "']";
            NodeList nodes = (NodeList) xpath.evaluate(expr, document, XPathConstants.NODESET);

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
            throw new MessageException("Erreur XPath lors de la récupération des messages de conversation", e);
        }
        return result;
    }

    public List<Element> getMessagesParUtilisateur(String utilisateurId) 
    {
        List<Element> result = new ArrayList<>();
        try 
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expr = "//message[@de='" + utilisateurId + "']";
            NodeList nodes = (NodeList) xpath.evaluate(expr, document, XPathConstants.NODESET);

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
            throw new MessageException("Erreur XPath lors de la récupération des messages par utilisateur", e);
        }
        return result;
    }
}
