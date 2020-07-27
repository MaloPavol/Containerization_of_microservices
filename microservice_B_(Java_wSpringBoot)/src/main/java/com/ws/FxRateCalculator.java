package com.ws;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;


public class FxRateCalculator {
    public double calc(String fromCCY, String toCCY) throws IOException, SAXException, ParserConfigurationException, ParseException {
        double exchangeRate = 0.0001;
        EcbConnector dataRetriever = new EcbConnector();

        Document doc = dataRetriever.getXML();
        HashMap FX_Data = dataRetriever.getFX_HashMap(doc);

        if(fromCCY.equals("EUR") && !toCCY.equals("EUR")){
            exchangeRate = calcFromEUR(toCCY, FX_Data);
        } else if (!fromCCY.equals("EUR") && toCCY.equals("EUR")){

            exchangeRate = calcToEUR(fromCCY, FX_Data);
        } else if (!fromCCY.equals("EUR") && !toCCY.equals("EUR")) {
            exchangeRate = calcCrossRate(fromCCY, toCCY, FX_Data);
        }

        return exchangeRate;
    }

    public double calcFromEUR(String toCCY, HashMap FX_Data){
        String rate = FX_Data.get(toCCY).toString();
        double rateDb = Double.parseDouble(rate);
        double rateResult = rateDb;

        return rateResult;
    }

    public double calcToEUR(String fromCCY, HashMap FX_Data){
        String rate = FX_Data.get(fromCCY).toString();
        double rateDb = Double.parseDouble(rate);
        double rateResult = 1/rateDb;

        return rateResult;
    }

    public double calcCrossRate(String fromCCY, String toCCY, HashMap FX_Data){
        String rateFromCCY = FX_Data.get(fromCCY).toString();
        String rateToCCY = FX_Data.get(toCCY).toString();
        double rateDbFromCCY = Double.parseDouble(rateFromCCY);
        double rateDbToCCY = Double.parseDouble(rateToCCY);
        double rateResult = (1/rateDbFromCCY)*(rateDbToCCY);

        return rateResult;
    }
}