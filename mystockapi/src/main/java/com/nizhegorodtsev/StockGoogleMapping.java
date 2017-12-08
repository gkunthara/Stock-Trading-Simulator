package com.nizhegorodtsev;

/**
 * Created by senizhegorodtsev on 7/8/2015.
 * Builds URL to fetch stock from google finance
 * Maps the resulting JSON into the Stock object
 * http://www.google.com/finance?output=json&q=google
 * http://finance.google.com/finance/info?client=ig&q=aapl
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;


public class StockGoogleMapping implements IStockMapping {
    @Override
    public Stock MapStock(String resultString) {
        Stock stock = new Stock();

        try {
            String token[] = resultString.split("//");


            JSONArray jsonArray = new JSONArray(token[1]);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            stock.setProperty("ticker", jsonObject.getString("t"));
            stock.setProperty("stockExchange", jsonObject.getString("e"));
            stock.setProperty("lastTrade", jsonObject.getString("l_cur"));
            stock.setProperty("lastTradeTime", jsonObject.getString("lt"));
            stock.setProperty("change", jsonObject.getString("c"));
            stock.setProperty("changePercent", jsonObject.getString("cp"));

            if (jsonObject.has("div"))
                stock.setProperty("dividend", jsonObject.getString("div"));
        } catch (JSONException e) {
            Logger log = Logger.getLogger(StockGoogleMapping.class.getName());
            log.log(Level.WARNING, e.toString(), e);
        }

        return stock;
    }

    @Override
    public String BuildFetchUrl(String ticker) {
        return String.format("http://finance.google.com/finance/info?client=ig&q=%s", ticker);
    }
}
