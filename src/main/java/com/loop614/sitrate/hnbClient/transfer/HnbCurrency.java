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
        currency.datum_primjene = parseDate(hnbCurrencyJson.getString("datum_primjene"));
        currency.drzava = hnbCurrencyJson.getString("drzava");
        currency.drzava_iso = hnbCurrencyJson.getString("drzava_iso");
        currency.sifra_valute = hnbCurrencyJson.getString("sifra_valute");
        currency.valuta = hnbCurrencyJson.getString("valuta");
        currency.kupovni_tecaj = Double.valueOf(hnbCurrencyJson.getString("kupovni_tecaj").replace(',', '.'));
        currency.srednji_tecaj = Double.valueOf(hnbCurrencyJson.getString("srednji_tecaj").replace(',', '.'));
        currency.prodajni_tecaj = Double.valueOf(hnbCurrencyJson.getString("prodajni_tecaj").replace(',', '.'));

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
}
