package com.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;


public class EcbConnector {
    public static Document getXML() throws ParserConfigurationException, IOException, SAXException {
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());

        Element root = doc.getDocumentElement();
        NodeList nList = root.getElementsByTagName("Cube");

        Element elem = (Element) nList.item(5);

        return doc;
    }

    public static HashMap getFX_HashMap(Document doc) throws IOException, SAXException, ParserConfigurationException, ParseException {
        Date dataDate = null;
        HashMap<String, String> FX_Rates = new HashMap<>();
        Element root = doc.getDocumentElement();
        NodeList nList = root.getElementsByTagName("Cube");
        for (int i = 0; i < nList.getLength(); i++) {
            Element elem = (Element) nList.item(i);
            if (!elem.getAttribute("time").isEmpty()) {
                FX_Rates.put("DATA_DATE", elem.getAttribute("time"));
            }
            if (!elem.getAttribute("currency").isEmpty()) {
                FX_Rates.put(elem.getAttribute("currency"), elem.getAttribute("rate"));
            }
        }
        return FX_Rates;
    }
}




