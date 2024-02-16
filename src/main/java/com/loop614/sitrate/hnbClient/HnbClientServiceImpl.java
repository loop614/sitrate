package com.loop614.sitrate.hnbClient;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.hnbClient.domain.HnbFetcher;
import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;

@Service
public class HnbClientServiceImpl implements HnbClientService {
    private final HnbFetcher hnbFetcher;

    public HnbClientServiceImpl(HnbFetcher hnbFetcher) {
        this.hnbFetcher = hnbFetcher;
    }

    public List<HnbCurrency> currencyEur() throws HnbClientException {
        return this.hnbFetcher.currencyEur();
    }
}
