package com.applichat;

import org.w3c.dom.*;

import com.applichat.doa.UtilisateurDAO;
import com.applichat.doa.XMLManager;

public class AppliChatApplication {
    public static void main(String[] args) {
        UtilisateurDAO dao = new UtilisateurDAO();
        Document doc = XMLManager.getDocument();

        // Création d’un élément utilisateur complet minimal
        Element utilisateur = doc.createElement("utilisateur");
        utilisateur.setAttribute("id", "u999");

        // identifiants
        Element identifiants = doc.createElement("identifiants");
        Element login = doc.createElement("login");
        login.setTextContent("mohameth");
        Element motDePasse = doc.createElement("motDePasse");
        motDePasse.setTextContent("1234");
        motDePasse.setAttribute("algoHash", "plain");

        identifiants.appendChild(login);
        identifiants.appendChild(motDePasse);
        utilisateur.appendChild(identifiants);

        // profil
        Element profil = doc.createElement("profil");
        Element nom = doc.createElement("nom");
        nom.setTextContent("Mohameth LO");
        profil.appendChild(nom);

        // ajout d’éléments obligatoires minimal pour ne pas casser le DTD
        profil.appendChild(doc.createElement("photo"));
        profil.appendChild(doc.createElement("languePreferee"));
        profil.appendChild(doc.createElement("languesDisponibles"));
        profil.appendChild(doc.createElement("fuseauHoraire"));

        Element statut = doc.createElement("statut");
        statut.setAttribute("derniereConnexion", "2025-07-12T07:00:00");
        statut.setTextContent("En ligne");

        profil.appendChild(statut);
        utilisateur.appendChild(profil);

        // préférences (vide mais structurelle)
        Element preferences = doc.createElement("preferences");
        utilisateur.appendChild(preferences);

        dao.ajouterUtilisateur(utilisateur);

        System.out.println("Utilisateur ajouté avec succès !");
    }
}

