package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Currency;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyService {

    public String convert(String code, boolean toUZS, Double amount) {

        Currency currency = getCurrency(code);

        String result = String.format("%.2f", amount * currency.getRate());
        String result1 = String.format("%.2f", amount / currency.getRate());
        if (toUZS) {
            return result + " Rate : " + currency.getRate();

        }
        return result1 + " Rate : " + currency.getRate();
    }

    public Currency getCurrency(String code) {
        try {
            URL url = new URL("https://cbu.uz/ru/arkhiv-kursov-valyut/json/" + code + "/");

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(url, new TypeReference<ArrayList<Currency>>() {
            }).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
