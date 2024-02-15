package com.loop614.sitrate.hnbClient;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;

@Service
public class HnbClientServiceImpl implements HnbClientService {
    public List<HnbCurrency> currencyEur() throws HnbClientException {
        String cachePath = System.getProperty("user.dir") + "/src/main/java/com/loop614/sitrate/hnbClient/example/tecajn-eur.json";
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


        /*WebClient webClient = WebClient.create(HnbClientConfig.URL);
        List<HnbCurrency> response = webClient.get()
            .uri("/api/endpoint")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<HnbCurrency>>() {})
            .block();

        return response;*/
    }
}
