package com.loop614.sitrate.hnbClient.domain;

import java.util.List;

import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;

public interface HnbFetcher {
    public List<HnbCurrency> currencyEur() throws HnbClientException;
}
