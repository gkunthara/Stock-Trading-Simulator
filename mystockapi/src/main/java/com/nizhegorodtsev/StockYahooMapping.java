package com.nizhegorodtsev;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by senizhegorodtsev on 7/6/2015.
 * This class maps the resulting string from Yahoo to a Stock object
 */
public class StockYahooMapping implements IStockMapping {



    private static ArrayList<StringPair> stockFieldMapping;

    //private static Map<String, String> stockFieldMapping;

    private static void initStockFieldMapping()
    {
        stockFieldMapping = new ArrayList<StringPair>();

        stockFieldMapping.add(new StringPair("ask", "a"));
        stockFieldMapping.add(new StringPair("averageDailyVolume", "a2"));
        stockFieldMapping.add(new StringPair("askSize", "a5"));
        stockFieldMapping.add(new StringPair("bid", "b"));
        stockFieldMapping.add(new StringPair("askRealTime", "b2"));
        stockFieldMapping.add(new StringPair("bidRealTime", "b3"));
        stockFieldMapping.add(new StringPair("bookValue", "b4"));
        stockFieldMapping.add(new StringPair("bidSize", "b6"));
        stockFieldMapping.add(new StringPair("changeAndPercentChange", "c"));
        stockFieldMapping.add(new StringPair("change", "c1"));
        stockFieldMapping.add(new StringPair("comission", "c3"));
        stockFieldMapping.add(new StringPair("changeRealTime", "c6"));
        stockFieldMapping.add(new StringPair("afterHoursChange", "c8"));
        stockFieldMapping.add(new StringPair("dividend", "d"));
        stockFieldMapping.add(new StringPair("lastTradeDate", "d1"));
        stockFieldMapping.add(new StringPair("tradeDate", "d2"));
        stockFieldMapping.add(new StringPair("earnings", "e"));
        stockFieldMapping.add(new StringPair("errorIndication", "e1"));
        stockFieldMapping.add(new StringPair("epsEstimatesCurrentYear", "e7"));
        stockFieldMapping.add(new StringPair("epsEstimatesNextYear", "e8"));
        stockFieldMapping.add(new StringPair("epsEstimatesNextQuater", "e9"));
        stockFieldMapping.add(new StringPair("floatShares", "f6"));
        stockFieldMapping.add(new StringPair("dayLow", "g"));
        stockFieldMapping.add(new StringPair("dayHigh", "h"));
        stockFieldMapping.add(new StringPair("_52weekLow", "j"));
        stockFieldMapping.add(new StringPair("_52weekHigh", "k"));
        stockFieldMapping.add(new StringPair("holdingsGainPercent", "g1"));
        stockFieldMapping.add(new StringPair("annualizedGain", "g3"));
        stockFieldMapping.add(new StringPair("holdingsGain", "g4"));
        stockFieldMapping.add(new StringPair("holdingsGainPercentRealTime", "g5"));
        stockFieldMapping.add(new StringPair("holdingsGainRealTime", "g6"));
        stockFieldMapping.add(new StringPair("moreInfo", "i"));
        stockFieldMapping.add(new StringPair("orderBook", "i5"));
        stockFieldMapping.add(new StringPair("marketCapitalization", "j1"));
        stockFieldMapping.add(new StringPair("marketCapRealTime", "j3"));
        stockFieldMapping.add(new StringPair("eBITDA", "j4"));
        stockFieldMapping.add(new StringPair("changeFrom52WeekLow", "j5"));
        stockFieldMapping.add(new StringPair("percentChangeFrom52WeekLow", "j6"));
        stockFieldMapping.add(new StringPair("lastTradeRealTimeWithTime", "k1"));
        stockFieldMapping.add(new StringPair("changePercentRealTime", "k2"));
        stockFieldMapping.add(new StringPair("lastTradeSize", "k3"));
        stockFieldMapping.add(new StringPair("changeFrom52WeekHigh", "k4"));
        stockFieldMapping.add(new StringPair("percentChangeFrom52WeekHigh", "k5"));
        stockFieldMapping.add(new StringPair("lastTradeWithTime", "l"));
        stockFieldMapping.add(new StringPair("lastTrade", "l1"));
        stockFieldMapping.add(new StringPair("highLimit", "l2"));
        stockFieldMapping.add(new StringPair("lowLimit", "l3"));
        stockFieldMapping.add(new StringPair("dayRange", "m"));
        stockFieldMapping.add(new StringPair("dayRangeRealTime", "m2"));
        stockFieldMapping.add(new StringPair("_50dayMovingAverage", "m3"));
        stockFieldMapping.add(new StringPair("_200dayMovingAverage", "m4"));
        stockFieldMapping.add(new StringPair("changeFrom200dayMovingAverage", "m5"));
        stockFieldMapping.add(new StringPair("percentChangeFrom200dayMovingAverage", "m6"));
        stockFieldMapping.add(new StringPair("changeFrom50dayMovingAverage", "m7"));
        stockFieldMapping.add(new StringPair("percentChangeFrom50dayMovingAverage", "m8"));
        stockFieldMapping.add(new StringPair("name", "n"));
        stockFieldMapping.add(new StringPair("notes", "n4"));
        stockFieldMapping.add(new StringPair("open", "o"));
        stockFieldMapping.add(new StringPair("previousClose", "p"));
        stockFieldMapping.add(new StringPair("pricePaid", "p1"));
        stockFieldMapping.add(new StringPair("changePercent", "p2"));
        stockFieldMapping.add(new StringPair("PriceSales", "p5"));
        stockFieldMapping.add(new StringPair("PriceBook", "p6"));
        stockFieldMapping.add(new StringPair("exDividendDate", "q"));
        stockFieldMapping.add(new StringPair("peRatio", "r"));
        stockFieldMapping.add(new StringPair("dividendPayDate", "r1"));
        stockFieldMapping.add(new StringPair("peRatioRealTime", "r2"));
        stockFieldMapping.add(new StringPair("pegRatio", "r5"));
        stockFieldMapping.add(new StringPair("priceEPSEstimateCurrentYeat", "r6"));
        stockFieldMapping.add(new StringPair("priceEPSEstimateNextYeat", "r7"));
        stockFieldMapping.add(new StringPair("ticker", "s"));
        stockFieldMapping.add(new StringPair("sharesOwned", "s1"));
        stockFieldMapping.add(new StringPair("shortRatio", "s7"));
        stockFieldMapping.add(new StringPair("lastTradeTime", "t1"));
        stockFieldMapping.add(new StringPair("tradeLinks", "t6"));
        stockFieldMapping.add(new StringPair("tickerTrend", "t7"));
        stockFieldMapping.add(new StringPair("_1yearTargetPrice", "t8"));
        stockFieldMapping.add(new StringPair("volume", "v"));
        stockFieldMapping.add(new StringPair("holdingValue", "v1"));
        stockFieldMapping.add(new StringPair("holdingValueRealTime", "v7"));
        stockFieldMapping.add(new StringPair("_52weekRange", "w"));
        stockFieldMapping.add(new StringPair("dayValueChange", "w1"));
        stockFieldMapping.add(new StringPair("dayValueChangeRealTime", "w4"));
        stockFieldMapping.add(new StringPair("stockExchange", "x"));
    }

    public StockYahooMapping() {
        if (stockFieldMapping == null) {
            initStockFieldMapping();
        }
    }

    @Override
    public Stock MapStock(String yahooResultString)
    {
        Stock stock = new Stock();

        ArrayList<String> elements = splitLineWithQuotes(yahooResultString);
        int currentIndex = 0;

        Map<String, String> downloadedValues = new HashMap<String, String>();

        for(StringPair pair : stockFieldMapping) {
            stock.setProperty(pair.getPropertyName(), elements.get(currentIndex++));
        }

        return stock;
    }

    public String BuildFetchUrl(String ticker)
    {
        StringBuffer sb = new StringBuffer();

        for (StringPair pair : stockFieldMapping) {
            sb.append(pair.getPropertyValue());
        }
        return String.format("http://finance.yahoo.com/d/quotes.csv?s=%s&f=%s", ticker, sb.toString());

    }

    private ArrayList<String> splitLineWithQuotes(String s)
    {
        ArrayList<String> words = new ArrayList<String>();
        boolean notInsideComma = true;
        int start =0, end=0;
        for(int i=0; i<s.length()-1; i++)
        {
            if(s.charAt(i)==',' && notInsideComma)
            {
                words.add(s.substring(start,i));
                start = i+1;
            }
            else if(s.charAt(i)=='"')
                notInsideComma=!notInsideComma;
        }
        words.add(s.substring(start));
        return words;
    }

}
class StringPair {
    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    private String propertyName;
    private String propertyValue;

    public StringPair(String propertyName, String propertyValue)
    {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }
}