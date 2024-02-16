package com.loop614.sitrate.hnbClient.domain;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loop614.sitrate.hnbClient.HnbClientConfig;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrencyReceived;

@Service
public class HnbFetcherImpl implements HnbFetcher {
    public List<HnbCurrency> currencyEur() throws HnbClientException {
        LocalDate localDate = LocalDate.now();
        String todayString = localDate.toString();
        String cacheDirectoryPath = System.getProperty("user.dir") + HnbClientConfig.CACHEDIRECTORY;
        File cacheDirectory = new File(cacheDirectoryPath);
        if (!cacheDirectory.isDirectory()) {
            cacheDirectory.mkdirs();
        }
        String cachePath = cacheDirectoryPath + HnbClientConfig.URL.replace("/", "") + "-" + todayString + ".json";
        File f = new File(cachePath);
        if(f.exists() && !f.isDirectory()) {
            return getHnbCurrencyFromCache(cachePath);
        }
        List<HnbCurrency> hnbCurrencies = this.getHnbCurrency();
        this.cleanCacheDirectory(cacheDirectory);
        this.saveHnbCurrencyCache(hnbCurrencies, cachePath);

        return hnbCurrencies;
    }

    private void saveHnbCurrencyCache(List<HnbCurrency> hnbCurrencies, String cachePath) {
        try {
            new ObjectMapper().writeValue(new File(cachePath), hnbCurrencies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanCacheDirectory(File cacheDirectory) {
        for(File file: cacheDirectory.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    private List<HnbCurrency> getHnbCurrencyFromCache(String cachePath) {
        try (FileReader reader = new FileReader(cachePath)) {
            JSONArray jsonArray = new JSONArray(new JSONTokener(reader));
            ArrayList<HnbCurrency> currencyList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCurrency = jsonArray.getJSONObject(i);
                HnbCurrency currency = HnbCurrency.fromJson(jsonCurrency);
                currencyList.add(currency);
            }
            return currencyList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
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
