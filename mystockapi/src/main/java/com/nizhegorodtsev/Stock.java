package com.nizhegorodtsev;

import java.io.Serializable;
import java.lang.reflect.Field;


public class Stock implements Serializable {
    public int getAmountOwned() { return amountOwned; }


    public String getTicker() {
        return ticker;
    }

    public Double getAsk() {
        return ask;
    }

    public Double getAverageDailyVolume() {
        return averageDailyVolume;
    }

    public Double getAskSize() {
        return askSize;
    }

    public Double getBid() {return bid;}

    public Double getAskRealTime() {
        return askRealTime;
    }

    public Double getBidRealTime() {
        return bidRealTime;
    }

    public Double getBookValue() {
        return bookValue;
    }

    public Double getBidSize() {
        return bidSize;
    }

    public String getChangeAndPercentChange() {
        return changeAndPercentChange;
    }

    public Double getChange() {
        return change;
    }

    public Double getComission() {
        return comission;
    }

    public Double getChangeRealTime() {
        return changeRealTime;
    }

    public Double getAfterHoursChange() {
        return afterHoursChange;
    }

    public Double getDividend() {
        return dividend;
    }

    public String getLastTradeDate() {
        return lastTradeDate;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public Double getEarnings() {
        return earnings;
    }

    public String getErrorIndication() {
        return errorIndication;
    }

    public Double getEpsEstimatesCurrentYear() {
        return epsEstimatesCurrentYear;
    }

    public Double getEpsEstimatesNextYear() {
        return epsEstimatesNextYear;
    }

    public Double getEpsEstimatesNextQuater() {
        return epsEstimatesNextQuater;
    }

    public Double getFloatShares() {
        return floatShares;
    }

    public Double getDayLow() {
        return dayLow;
    }

    public Double getDayHigh() {
        return dayHigh;
    }

    public Double get_52weekLow() {
        return _52weekLow;
    }

    public Double get_52weekHigh() {
        return _52weekHigh;
    }

    public Double getHoldingsGainPercent() {
        return holdingsGainPercent;
    }

    public Double getAnnualizedGain() {
        return annualizedGain;
    }

    public Double getHoldingsGain() {
        return holdingsGain;
    }

    public Double getHoldingsGainPercentRealTime() {
        return holdingsGainPercentRealTime;
    }

    public Double getHoldingsGainRealTime() {
        return holdingsGainRealTime;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public Double getOrderBook() {
        return orderBook;
    }

    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    public String getMarketCapRealTime() {
        return marketCapRealTime;
    }

    public String geteBITDA() {
        return eBITDA;
    }

    public Double getChangeFrom52WeekLow() {
        return changeFrom52WeekLow;
    }

    public Double getPercentChangeFrom52WeekLow() {
        return percentChangeFrom52WeekLow;
    }

    public String getLastTradeRealTimeWithTime() {
        return lastTradeRealTimeWithTime;
    }

    public Double getChangePercentRealTime() {
        return changePercentRealTime;
    }

    public Double getLastTradeSize() {
        return lastTradeSize;
    }

    public Double getChangeFrom52WeekHigh() {
        return changeFrom52WeekHigh;
    }

    public Double getPercentChangeFrom52WeekHigh() {
        return percentChangeFrom52WeekHigh;
    }

    public String getLastTradeWithTime() {
        return lastTradeWithTime;
    }

    public Double getLastTrade() {
        return lastTrade;
    }

    public Double getHighLimit() {
        return highLimit;
    }

    public Double getLowLimit() {
        return lowLimit;
    }

    public String getDayRange() {
        return dayRange;
    }

    public String getDayRangeRealTime() {
        return dayRangeRealTime;
    }

    public Double get_50dayMovingAverage() {
        return _50dayMovingAverage;
    }

    public Double get_200dayMovingAverage() {
        return _200dayMovingAverage;
    }

    public Double getChangeFrom200dayMovingAverage() {
        return changeFrom200dayMovingAverage;
    }

    public Double getPercentChangeFrom200dayMovingAverage() {
        return percentChangeFrom200dayMovingAverage;
    }

    public Double getChangeFrom50dayMovingAverage() {
        return changeFrom50dayMovingAverage;
    }

    public Double getPercentChangeFrom50dayMovingAverage() {
        return percentChangeFrom50dayMovingAverage;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Double getOpen() {
        return open;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public Double getPricePaid() {
        return pricePaid;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public Double getPriceSales() {
        return PriceSales;
    }

    public Double getPriceBook() {
        return PriceBook;
    }

    public String getExDividendDate() {
        return exDividendDate;
    }

    public Double getPeRatio() {
        return peRatio;
    }

    public String getDividendPayDate() {
        return dividendPayDate;
    }

    public Double getPeRatioRealTime() {
        return peRatioRealTime;
    }

    public Double getPegRatio() {
        return pegRatio;
    }

    public Double getPriceEPSEstimateCurrentYeat() {
        return priceEPSEstimateCurrentYeat;
    }

    public Double getPriceEPSEstimateNextYeat() {
        return priceEPSEstimateNextYeat;
    }

    public String getSharesOwned() {
        return sharesOwned;
    }

    public Double getShortRatio() {
        return shortRatio;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public String getTradeLinks() {
        return tradeLinks;
    }

    public String getTickerTrend() {
        return tickerTrend;
    }

    public String get_1yearTargetPrice() {
        return _1yearTargetPrice;
    }

    public double getVolume() {
        return volume;
    }

    public double getHoldingValue() {
        return holdingValue;
    }

    public double getHoldingValueRealTime() {
        return holdingValueRealTime;
    }

    public String get_52weekRange() {
        return _52weekRange;
    }

    public Double getDayValueChange() {
        return dayValueChange;
    }

    public Double getDayValueChangeRealTime() {
        return dayValueChangeRealTime;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public int amountOwned;
    private String ticker; //s
    private Double ask;  //a
    private Double averageDailyVolume;   //a2
    private Double askSize;   //a5
    private Double bid;   //b
    private Double askRealTime;   //b2
    private Double bidRealTime;   //b3
    private Double bookValue;   //b4
    private Double bidSize;   //b6
    private String changeAndPercentChange;   //c
    private Double change;   //c1
    private Double comission;   //c3
    private Double changeRealTime;   //c6
    private Double afterHoursChange;   //c8
    private Double dividend;   //d
    private String lastTradeDate;   //d1
    private String tradeDate;   //d2
    private Double earnings;   //e
    private String errorIndication; //e1
    private Double epsEstimatesCurrentYear; //e7
    private Double epsEstimatesNextYear; //e8
    private Double epsEstimatesNextQuater; //e9
    private Double floatShares; //f6
    private Double dayLow; //g
    private Double dayHigh; //h
    private Double _52weekLow; //j
    private Double _52weekHigh; //k
    private Double holdingsGainPercent; //g1
    private Double annualizedGain; //g3
    private Double holdingsGain; //g4
    private Double holdingsGainPercentRealTime; //g5
    private Double holdingsGainRealTime; //g6
    private String moreInfo; //i
    private Double orderBook; //i5
    private String marketCapitalization; //j1
    private String marketCapRealTime; //j3
    private String eBITDA; //j4 - Earnings Before Interest, Taxes, Depreciation and Amortization
    private Double changeFrom52WeekLow; //j5
    private Double percentChangeFrom52WeekLow; //j6
    private String lastTradeRealTimeWithTime; //k1
    private Double changePercentRealTime; //k2
    private Double lastTradeSize; //k3
    private Double changeFrom52WeekHigh; //k4
    private Double percentChangeFrom52WeekHigh; //k5
    private String lastTradeWithTime; //l
    private Double lastTrade; //l1
    private Double highLimit; //l2
    private Double lowLimit; //l3
    private String dayRange; //m
    private String dayRangeRealTime; //m2
    private Double _50dayMovingAverage; //m3
    private Double _200dayMovingAverage; //m4
    private Double changeFrom200dayMovingAverage; //m5
    private Double percentChangeFrom200dayMovingAverage; //m6
    private Double changeFrom50dayMovingAverage; //m7
    private Double percentChangeFrom50dayMovingAverage; //m8
    private String name; //n
    private String notes; //n4
    private Double open; //o
    private Double previousClose; //p
    private Double pricePaid; //p1
    private Double changePercent; //p2
    private Double PriceSales; //p5
    private Double PriceBook; //p6
    private String exDividendDate; //q
    private Double peRatio; //r
    private String dividendPayDate; //r1
    private Double peRatioRealTime; //r2
    private Double pegRatio; //r5
    private Double priceEPSEstimateCurrentYeat; //r6
    private Double priceEPSEstimateNextYeat; //r7
    public String sharesOwned; //s1
    private Double shortRatio; //s7
    private String lastTradeTime; //t1
    private String tradeLinks; //t6
    private String tickerTrend; //t7
    private String _1yearTargetPrice; //t8
    private double volume; //v
    private double holdingValue; //v1
    private double holdingValueRealTime; //v7
    private String _52weekRange; //w
    private Double dayValueChange; //w1
    private Double dayValueChangeRealTime; //w4
    private String stockExchange; //x

    public Stock()
    {
    }
    public Stock(String ticker)
    {
        this.ticker = ticker;
    }

    public void setAmountSharesOwned(int amountOwned) {
        this.amountOwned = amountOwned;
    }

    public void buyShares(int toBuy) {
        this.amountOwned += toBuy;
    }

    public void sellShares(int toSell) {
        this.amountOwned = this.amountOwned - toSell;
    }

    public void setProperty(String propertyName, double propertyValue)    {
        try {
            Field f = this.getClass().getDeclaredField(propertyName);
            Class<?> clazz = f.getType();
            Double d = new Double(propertyValue);
            if (clazz.equals(Double.class)) {
                f.set(this, d);
            }
            else if (clazz.equals(String.class)) {
                f.set(this, d.toString());
            }
        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        }catch (IllegalArgumentException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.ticker;
    }

    public void setProperty(String propertyName, String propertyValue)
    {
        try {

            Field f = this.getClass().getDeclaredField(propertyName);
            if (propertyValue == null)
                return;
            if (propertyValue.equals("N/A") || propertyValue.equals(""))
                return;

            String strValue = propertyValue.trim();
            if (strValue.startsWith("\""))
                strValue = strValue.substring(1);
            if (strValue.endsWith("\""))
                strValue = strValue.substring(0, strValue.length() - 1);

            Class<?> clazz = f.getType();
            if (clazz.equals(Double.class)) {
                Double d = new Double(Double.parseDouble(strValue.replace('%', ' ').trim()));
                f.set(this, d);
            } else if (clazz.equals(String.class)) {
                f.set(this, strValue);
            }

        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        }catch (IllegalArgumentException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }

}
