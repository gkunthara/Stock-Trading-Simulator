package com.brian.brian.stockapp_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.nizhegorodtsev.Stock;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import android.database.sqlite.SQLiteOpenHelper;




public class WelcomeActivity extends AppCompatActivity {
    static final String TABLE_PORTFOLIO = "tablePortfolio";
    static final String TICKER_PORTFOLIO = "_ticker";
    static final String TABLE_WATCHLIST = "tableWatchlist";
    static final String TICKER_WATCHLIST = "_ticker";
    static final String TABLE_CASH = "tableCash";
    static final String CASH_CASH = "cash";


    ArrayList<Stock> securities = new ArrayList<>();
    double cash;
    List<Integer> amountsOwned;
    Portfolio portfolio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.portfolio = new Portfolio(securities, 10000.0);
        //accessDatabase();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.rebuildDatabase();

//        Stock stock1 = new Stock();
//        stock1.setProperty("ticker", "TSLA");
//        stock1.setAmountSharesOwned(3);
//        stock1.setProperty("changePercent", -0.16);
//        stock1.setProperty("lastTrade", 169.64);
//        portfolio.getSecurities().add(stock1);


        if (true) {
            Stock AAPL = new Stock();
            AAPL.setProperty("ticker", "AAPL");
            AAPL.setProperty("lastTrade", 105.0);
            AAPL.setProperty("change", 5.0);
            AAPL.setProperty("changePercent", 5.0);
            AAPL.setAmountSharesOwned(5);

            Stock GE = new Stock();
            GE.setProperty("ticker", "GE");
            GE.setProperty("lastTrade", 1005.0);
            GE.setProperty("change", 5.0);
            GE.setProperty("changePercent", .5);
            GE.setAmountSharesOwned(3);

            portfolio.purchase(AAPL, this);
            portfolio.purchase(GE, this);
            displayPortfolio(this.portfolio);
            displayDB();

        }


    }

    public void goToMainActivity(View view){

        Portfolio portfolio = new Portfolio(this.securities, 10000.0);
        displayPortfolio(portfolio);

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.putExtra("portfolio", portfolio);
        //put any intents necessary
        startActivity(intent);
    }


    public void displayPortfolio(Portfolio portfolio) {
        List<Stock> portSecurities = portfolio.getSecurities();
        Log.d("DISPLAY PORTFOLIO", "--START--");
        Log.d("      Cash", String.valueOf(portfolio.getCash()));
        for (int i=0; i<portSecurities.size(); i++) {
            Log.d("      " + portSecurities.get(i).getTicker(), "(" + String.valueOf(portSecurities.get(i).getAmountOwned()) + ")");
        }
        Log.d("DISPLAY PORTFOLIO", "---END---");
    }

    public void displayDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> tickers = databaseHelper.getSelectAllTickers(TABLE_PORTFOLIO);
        List<Integer> amounts = databaseHelper.getAmountOwned();
        Double cash = databaseHelper.getCash();

        Log.d("DISPLAY DB", "--START--");
        Log.d("      Cash", String.valueOf(cash));
        for (int i=0; i<tickers.size(); i++) {
            Log.d("      " + tickers.get(i), "(" + String.valueOf(amounts.get(i)) + ")");
        }
        Log.d("DISPLAY DB", "---END---");
    }

    /*
    public void loadPortfolio(View view) {
        this.portfolio = new Portfolio(securities, cash);
        this.portfolio.setAmountsOwned(this.amountsOwned);
    }
    */

    public void accessDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> tickers = databaseHelper.getSelectAllTickers(TABLE_PORTFOLIO);
        this.cash = databaseHelper.getCash();
        this.amountsOwned = databaseHelper.getAmountOwned();

        for (int i=0; i<tickers.size(); i++) {
            Log.d("TICKER SIZE", "+" + tickers.get(i));
        }


        for (int i=0; i<tickers.size(); i++){
            APIRequestAsyncTask asyncTask = new APIRequestAsyncTask();
            String ticker = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol=" +
                    tickers.get(i) + "&callback=myFunction";

            asyncTask.execute(ticker);
        }

    }

    private class APIRequestAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // runs on the UI thread
            Log.d("TAG", "onPreExecute: ");
        }

        // must override doInBackground()
        @Override
        protected String doInBackground(String... strings) {

            // THIS CODE RUNS ON A BACKGROUND THREAD
            // varargs is the ...
            // variable number of arguments
            // treat like an array
            Log.d("TAG", "doInBackground: HELLO FROM BACKGROUND THREAD");
            Log.d("TAG", "doInBackground: " + strings[0]);

            // there are three things we need to do
            // 1. open the request URL
            // 2. download the JSON response
            // 3. extract the meters value from the response
            // come back to this!

            String result = "";

            // step 1
            try {
                URL url = new URL(strings[0]);
                // added

                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();

                // 2) get the JSON response
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }


                Log.d("TAGLIERE", result);
                //String token = result.substring(11, result.length()-1);
                //Log.d("TAGLIERE", token);
                //newStock = xmlParser(result);
                //Log.d("TAGLIERE", newStock.getName());
                //MainActivity.this.mainStock = newStock;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("soInBackGrount", "Malform excetio");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("soInBackGrount", "IOexceptio");
            } //catch (JSONException e) {
            //  e.printStackTrace();
            //}

            // always assume success!!!

            return result;
        }

        // background threads cannot update the UI
        // we need to so in a method call onPostExecute(), which
        // runs on the main UI event thread

        @Override
        protected void onPostExecute(String resultStock) {
            super.onPostExecute(resultStock);
            // run on the UI thread
            //resultStock.setAmountSharesOwned(1);
            //mainStock = resultStock;
            //securities.add(resultStock);
            //MainActivity.this.brianFlag = true;


            securities.add(xmlParser(resultStock));

            Log.d("TAG", "onPostExecute: " + String.valueOf(resultStock));

            Log.d("END GAME", resultStock);
        }
    }

    public Stock xmlParser(String jsonInfo) {
        Stock stock = new Stock();

        try {
            String token = jsonInfo.substring(11, jsonInfo.length()-1);

            Log.d("Check string", token);
            JSONObject jsonObject = new JSONObject(token);

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

        }

        return stock;
    }
}
