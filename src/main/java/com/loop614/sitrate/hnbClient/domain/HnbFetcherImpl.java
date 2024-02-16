package com.loop614.sitrate.hnbClient.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.loop614.sitrate.hnbClient.HnbClientConfig;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrencyReceived;

@Service
public class HnbFetcherImpl implements HnbFetcher {
    private static HashMap<String, List<HnbCurrency>> cacheHnbCurrency = new HashMap<String, List<HnbCurrency>>();;

    public List<HnbCurrency> currencyEur() throws HnbClientException {
        LocalDate localDate = LocalDate.now();
        String todayString = localDate.toString();
        List<HnbCurrency> cachedList = HnbFetcherImpl.cacheHnbCurrency.get(todayString);
        if(cachedList != null) {
            return cachedList;
        }

        List<HnbCurrency> hnbCurrencies = this.getHnbCurrency();
        HnbFetcherImpl.cacheHnbCurrency = new HashMap<String, List<HnbCurrency>>();
        HnbFetcherImpl.cacheHnbCurrency.put(todayString, hnbCurrencies);

        return hnbCurrencies;
    }

    private List<HnbCurrency> getHnbCurrency() {
        WebClient webClient = WebClient.create(HnbClientConfig.BASEURL);
        List<HnbCurrencyReceived> jsonObjects = webClient.get()
            .uri(HnbClientConfig.URL)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<HnbCurrencyReceived>>() {})
            .block();

        List<HnbCurrency> hnbCurrencies = new ArrayList<HnbCurrency>();
        for (HnbCurrencyReceived jsonObject : jsonObjects) {
            hnbCurrencies.add(HnbCurrency.fromReceivedObject(jsonObject));
        }

        return hnbCurrencies;
    }
}
