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
        this.portfolio = new Portfolio(securities, this);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.rebuildDatabase();


    }

    public void goToMainActivity(View view){

        Portfolio portfolio = new Portfolio(this.securities, this);
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

//
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

            Log.d("TAG", "doInBackground: HELLO FROM BACKGROUND THREAD");
            Log.d("TAG", "doInBackground: " + strings[0]);



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

        @Override
        protected void onPostExecute(String resultStock) {
            super.onPostExecute(resultStock);

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
