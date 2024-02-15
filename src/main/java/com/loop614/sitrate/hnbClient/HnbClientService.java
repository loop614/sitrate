package com.loop614.sitrate.hnbClient;

import java.util.List;

import com.loop614.sitrate.hnbClient.exception.HnbClientException;
import com.loop614.sitrate.hnbClient.transfer.HnbCurrency;

public interface HnbClientService {
    public List<HnbCurrency> currencyEur() throws HnbClientException;
}
