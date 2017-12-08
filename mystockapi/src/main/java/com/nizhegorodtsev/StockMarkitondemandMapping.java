package com.nizhegorodtsev;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by senizhegorodtsev on 7/9/2015.
 * The class is intended to parse MarkitOnDemand stock quotes
 * http://dev.markitondemand.com/Api/v2/Quote/jsonp?symbol=AAPL&callback=myFunction
 */
public class StockMarkitondemandMapping implements IStockMapping {
    @Override
    public Stock MapStock(String resultString) {
        Stock stock = new Stock();

        try {
            String token[] = resultString.split("MapStock");
            token[1] = token[1].substring(1, token[1].length() - 1);    //Remove first and last character. '(' and ')'

            JSONObject jsonObject = new JSONObject(token[1]);
            //JSONArray jsonArray = new JSONArray(token[1]);
            //JSONObject jsonObject = jsonArray.getJSONObject(0);

            stock.setProperty("ticker", jsonObject.getString("Symbol"));
            stock.setProperty("name", jsonObject.getString("Name"));
            stock.setProperty("lastTrade", jsonObject.getDouble("LastPrice"));
            stock.setProperty("change", jsonObject.getDouble("Change"));
            stock.setProperty("changePercent", jsonObject.getDouble("ChangePercent"));
            stock.setProperty("lastTradeTime", jsonObject.getString("Timestamp"));
            stock.setProperty("marketCapitalization", jsonObject.getDouble("MarketCap"));
            stock.setProperty("volume", jsonObject.getDouble("Volume"));
            stock.setProperty("dayHigh", jsonObject.getDouble("High"));
            stock.setProperty("dayLow", jsonObject.getDouble("Low"));
            stock.setProperty("open", jsonObject.getDouble("Open"));
        } catch (JSONException e) {
            Logger log = Logger.getLogger(StockGoogleMapping.class.getName());
            log.log(Level.WARNING, e.toString(), e);
        }

        return stock;
    }

    @Override
    public String BuildFetchUrl(String ticker) {
        return String.format("http://dev.markitondemand.com/Api/v2/Quote/jsonp?symbol=%s&callback=MapStock", ticker);
    }
}
