package com.cloudacademy.bitcoin;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConverterSvc {

    private final static String BITCOIN_CURRENT_PRICE_URL= "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final HttpGet httpget = new HttpGet(BITCOIN_CURRENT_PRICE_URL);

    private CloseableHttpClient httpclient;

    public ConverterSvc() {
        this.httpclient = HttpClients.createDefault();
    }

    public ConverterSvc(CloseableHttpClient httpClient) {
        this.httpclient = httpClient;
    }

    public enum Currency {
        USD,
        GBP,
        EUR
    }
    
    public double getExchangeRate(Currency currency) {
        double rate = 0;

        try (CloseableHttpResponse response = this.httpclient.execute(httpget)) {
            switch (response.getStatusLine().getStatusCode()) {
                case 200:
                    InputStream inputStream = response.getEntity().getContent();
                    var json = new BufferedReader(new InputStreamReader(inputStream));

                    @SuppressWarnings("deprecation")
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    String n = jsonObject.get("bpi").getAsJsonObject().get(currency.toString()).getAsJsonObject().get("rate").getAsString();
                    NumberFormat nf = NumberFormat.getInstance();
                    rate = nf.parse(n).doubleValue();

                    break;
                default:
                    rate = -1;
            }
        } catch (IOException | ParseException ex) {
            rate = -1;
        }

        return rate;
    }
    public double convertBitcoins(Currency currency, int coins) {
        double dollars = 0;

        if (coins < 0) {
            throw new IllegalArgumentException("Number of coins must not be less than zero");
        }
        var exchangeRate = getExchangeRate(currency);

        if (exchangeRate >= 0) {
            dollars = exchangeRate * coins;
        } else {
            dollars = -1;
        }

        return dollars;
    }
}