package com.loop614.sitrate.hnbClient.transfer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class HnbCurrency {
    public int broj_tecajnice;
    public Date datum_primjene;
    public String drzava;
    public String drzava_iso;
    public String sifra_valute;
    public String valuta;
    public double kupovni_tecaj;
    public double srednji_tecaj;
    public double prodajni_tecaj;

    public static HnbCurrency fromJson(JSONObject hnbCurrencyJson) {
        HnbCurrency currency = new HnbCurrency();
        currency.broj_tecajnice = hnbCurrencyJson.getInt("broj_tecajnice");
        currency.datum_primjene = new Date(hnbCurrencyJson.getLong("datum_primjene"));
        currency.drzava = hnbCurrencyJson.getString("drzava");
        currency.drzava_iso = hnbCurrencyJson.getString("drzava_iso");
        currency.sifra_valute = hnbCurrencyJson.getString("sifra_valute");
        currency.valuta = hnbCurrencyJson.getString("valuta");
        currency.kupovni_tecaj = hnbCurrencyJson.getDouble("kupovni_tecaj");
        currency.srednji_tecaj = hnbCurrencyJson.getDouble("srednji_tecaj");
        currency.prodajni_tecaj = hnbCurrencyJson.getDouble("prodajni_tecaj");

        return currency;
    }

    public String getCurrency() {
        return this.valuta;
    }

    public double getCurrencyMiddleValue() {
        return this.srednji_tecaj;
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HnbCurrency fromReceivedObject(HnbCurrencyReceived receivedObject) {
        HnbCurrency currency = new HnbCurrency();
        currency.broj_tecajnice = Integer.valueOf(receivedObject.broj_tecajnice);
        currency.datum_primjene = parseDate(receivedObject.datum_primjene);
        currency.drzava = receivedObject.drzava;
        currency.drzava_iso = receivedObject.drzava_iso;
        currency.sifra_valute = receivedObject.sifra_valute;
        currency.valuta = receivedObject.valuta;
        currency.kupovni_tecaj = Double.valueOf(receivedObject.kupovni_tecaj.replace(',', '.'));
        currency.srednji_tecaj = Double.valueOf(receivedObject.srednji_tecaj.replace(',', '.'));
        currency.prodajni_tecaj = Double.valueOf(receivedObject.prodajni_tecaj.replace(',', '.'));

        return currency;
    }
}
