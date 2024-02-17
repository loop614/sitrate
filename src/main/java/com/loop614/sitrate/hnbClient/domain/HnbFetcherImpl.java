package com.loop614.sitrate.hnbClient.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.loop614.sitrate.hnbClient.HnbClientConfig;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrencyReceived;

import reactor.core.publisher.Mono;

@Service
public class HnbFetcherImpl implements HnbFetcher {
    private final WebClient webClient;

    private static HashMap<String, List<HnbCurrency>> cacheHnbCurrency = new HashMap<String, List<HnbCurrency>>();;

	public HnbFetcherImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(HnbClientConfig.BASEURL).build();
	}

    public List<HnbCurrency> currencyEur() throws HnbClientException {
        String todayString = LocalDate.now().toString();
        List<HnbCurrency> cachedList = HnbFetcherImpl.cacheHnbCurrency.get(todayString);
        if(cachedList != null) {
            return cachedList;
        }

        List<HnbCurrency> hnbCurrencies = this.getHnbCurrency();
        this.updateCache(todayString, hnbCurrencies);

        return hnbCurrencies;
    }

    private List<HnbCurrency> getHnbCurrency() throws HnbClientException {
        List<HnbCurrencyReceived> jsonObjects = this.webClient.get()
            .uri(HnbClientConfig.URL)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(HnbClientException::new))
            .bodyToMono(new ParameterizedTypeReference<List<HnbCurrencyReceived>>() {})
            .block();

        List<HnbCurrency> hnbCurrencies = new ArrayList<HnbCurrency>();
        for (HnbCurrencyReceived jsonObject : jsonObjects) {
            hnbCurrencies.add(HnbCurrency.fromReceivedObject(jsonObject));
        }

        return hnbCurrencies;
    }

    private void updateCache(String todayString, List<HnbCurrency> hnbCurrencies) {
        HnbFetcherImpl.cacheHnbCurrency = new HashMap<String, List<HnbCurrency>>();
        HnbFetcherImpl.cacheHnbCurrency.put(todayString, hnbCurrencies);
    }
}
