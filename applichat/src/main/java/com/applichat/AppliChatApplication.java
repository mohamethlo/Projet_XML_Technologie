package com.applichat;


import org.w3c.dom.Element;

import com.applichat.doa.XMLManager;

public class AppliChatApplication 
{
    public static void main(String[] args) 
    {
        Element racine = XMLManager.getRootElement();
        System.out.println("Racine XML : " + racine.getNodeName());
    }
}
