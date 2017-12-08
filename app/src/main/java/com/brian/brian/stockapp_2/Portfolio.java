package com.brian.brian.stockapp_2;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nizhegorodtsev.Stock;


public class Portfolio implements Serializable{
    private double netWorth;
    private double cash;
    public List<Stock> securities;
    //private int securitiesLength;
    private double careerPortfolioGrowth;
    private double careerPortfolioGrowthPercent;
    private double dailyPortfolioGrowth;
    private double dailyPortfolioGrowthPercent;

    static final String TABLE_PORTFOLIO = "tablePortfolio";
    static final String TICKER_PORTFOLIO = "_ticker";
    static final String TABLE_WATCHLIST = "tableWatchlist";
    static final String TICKER_WATCHLIST = "_ticker";
    static final String TABLE_CASH = "tableCash";
    static final String CASH_CASH = "cash";


    public Portfolio(List<Stock> securities, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.cash = databaseHelper.getCash();
        this.securities = securities;
        this.netWorth = calcNetWorth(context);

        calcCareerPortfolioGrowthAndPercent(context);
        calcDailyPortfolioGrowthAndPercent(context);
    }

    public Portfolio() {}

    public void setAmountsOwned(List<Integer> amountsOwned) {
        for (int i=0; i<this.securities.size(); i++) {
            this.securities.get(i).setAmountSharesOwned(amountsOwned.get(i));
        }
    }

    // Calculate current net worth
    public double calcNetWorth(Context context) {

        double calcNetWorth = this.cash;
        for (int i=0; i<this.securities.size(); i++) {
            Log.d("CALC NET WORTH:", String.valueOf(securities.get(i).getAmountOwned()));

            calcNetWorth = calcNetWorth + securities.get(i).getAmountOwned() * securities.get(i).getLastTrade();
        }

        return calcNetWorth;
    }


    // calculate career growth in $ and %
    public void calcCareerPortfolioGrowthAndPercent(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.cash = databaseHelper.getCash();

        // Players Start with 10,000
        double calcCareerPortfolioGrowth = this.cash;
        for (int i=0; i<this.securities.size(); i++) {
            calcCareerPortfolioGrowth = calcCareerPortfolioGrowth +
                    securities.get(i).getAmountOwned()*securities.get(i).getLastTrade();
        }

        // Set field values
        // -10,000 becuase that is starting amount of money
        this.careerPortfolioGrowth = calcCareerPortfolioGrowth - 10000.0;
        this.careerPortfolioGrowthPercent = calcCareerPortfolioGrowth / 10000.0 - 1.0;
    }


    // calculate daily portfolio growth in $ and %
    public void calcDailyPortfolioGrowthAndPercent(Context context) {
        double calcDailyPortfolioGrowth = 0.0;
        for (int i=0; i<this.securities.size(); i++) {
            calcDailyPortfolioGrowth = calcDailyPortfolioGrowth +
                    securities.get(i).getAmountOwned()
                            * (1+securities.get(i).getChangePercent());
        }

        // Set field values
        this.dailyPortfolioGrowth = calcDailyPortfolioGrowth;
        this.dailyPortfolioGrowthPercent = ((netWorth) / (netWorth - calcDailyPortfolioGrowth))-1.0;
    }


    // Stock.numOwned should contain an unsigned number about how many shares to sell
    public boolean sell(Stock stock, Context context) {
        Log.d("Selling:" + stock.getTicker(), "");
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.cash = databaseHelper.getCash();

        int numToSell = stock.getAmountOwned();
        int index = 0;
        int numSharesOwned = databaseHelper.getAmountOwned(stock.getTicker());
        double valueOfSale = stock.getLastTrade() * numToSell;

        boolean canExecuteSale = false;

        // Find stock in portfolio
        for (int i=0; i<this.securities.size(); i++) {
            if (securities.get(i).getTicker().equals(stock.getTicker())) {
                index = i;
                //       numSharesOwned = securities.get(i).getAmountOwned();
                break;
            }
        }

        Log.d("Shares Owned: " + numSharesOwned, "To Sell: " + numToSell);

        // Sell complete Stake
        if (numSharesOwned == numToSell) {
            Log.d("They", "were equal");
            this.securities.remove(index);

            this.cash = this.cash + valueOfSale;
            //this.securitiesLength--;


            // TODO --> Update Cash Table        -DONE
            // TODO --> Update Portfolio Table   -DONE
            setDBCash(this.cash, context);
            removeDBEntry(stock.getTicker(), context);
            return true;
        }
        // Trim Stake
        else if (numSharesOwned > numToSell) {
            //this.securities.get(index).sellShares(numToSell);
            this.cash = this.cash + valueOfSale;
            int sharesLeft = numSharesOwned - numToSell;
            this.securities.get(index).setAmountSharesOwned(sharesLeft);

            // TODO --> Update Cash Table        -DONE
            // TODO --> Update Portfolio Table   -DONE
            setDBCash(this.cash, context);
            setDBAmountOwned(stock.getTicker(), sharesLeft, context);
            return true;
        }
        // No Sale Due to Lack of Shares
        else {
            String totalShares = String.valueOf(numSharesOwned);
            String desiredSale = String.valueOf(numToSell);

            String msg = "You would like to sell "
                    + desiredSale
                    + " shares, but only have "
                    + totalShares
                    + " shares...";

            return false;
        }
    }

    // Number of share being purchased should already be encapsulated in stock.numOwned
    public boolean purchase(Stock stock, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.cash = databaseHelper.getCash();

        boolean alreadyOwned = false;	// flag for whether stock is already owned
        int alreadyOwnedIndex = -999;	// -999 will give out of bounds error

        int num = stock.getAmountOwned();
        double costOfPurchase = stock.getLastTrade() * num;

        if (costOfPurchase < this.cash){
            // Loop to find if stock is already in portfolio
            System.out.println("mbvmnfgng");
            for (int i=0; i<this.securities.size(); i++) {
                if (this.securities.get(i).getTicker().equals(stock.getTicker())) {
                    alreadyOwned = true;
                    alreadyOwnedIndex = i;
                    break;
                }
            }

            // Testing
            Log.d("ALREADY OWNED---", String.valueOf(alreadyOwned));
            if (alreadyOwned == true) {
                Log.d(this.securities.get(alreadyOwnedIndex).getTicker(), "vs " + stock.getTicker());
            }

            if (alreadyOwned) {
                this.securities.get(alreadyOwnedIndex).buyShares(num);
                this.cash = this.cash - costOfPurchase;

                // TODO --> Update Cash Table        --Done
                // TODO --> Update Portfolio Table   --Done
                setDBCash(this.cash, context);
                setDBAmountOwned(stock.getTicker(),
                        this.securities.get(alreadyOwnedIndex).getAmountOwned(), context);

            }
            else {
//                this.securities.add(stock);
                this.securities.add(stock);
                this.cash = this.cash - costOfPurchase;
                //this.securitiesLength++;

                // TODO --> Update Cash        --Done
                // TODO --> Update DB          --Done
                setDBCash(this.cash, context);
                addDBEntry(stock.getTicker(), stock.getAmountOwned(), context);

            }
            return true;
        }
        else {
            return false;
        }
    }



    public void addDBEntry(String ticker, int amount, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.insertStock(ticker, amount, TABLE_PORTFOLIO);
    }

    public void removeDBEntry(String ticker, Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.deleteStock(ticker, TABLE_PORTFOLIO);
    }

    public void setDBAmountOwned(String ticker, int amount, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.setAmount(ticker, amount);
    }

    public void setDBCash(double amount, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.updateCash(amount);
    }




    // Getters for private fields
    public double getNetWorth() { return this.netWorth; }
    public double getCash() { return this.cash; }
    public double getCareerPortfolioGrowth() { return careerPortfolioGrowth; }
    public double getCareerPortfolioGrowthPercent() { return careerPortfolioGrowthPercent; }
    public double getDailyPortfolioGrowth() { return dailyPortfolioGrowth; }
    public double getDailyPortfolioGrowthPercent() { return dailyPortfolioGrowthPercent; }

    public List<Stock> getSecurities() { return this.securities; }
    //public int getSecuritiesLength() { return this.securitiesLength; }


    @Override
    public String toString() {
        return this.securities.toString();
    }
}
