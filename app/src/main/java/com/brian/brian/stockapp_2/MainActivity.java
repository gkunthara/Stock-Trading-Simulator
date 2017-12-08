package com.brian.brian.stockapp_2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nizhegorodtsev.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements Serializable {

    static final String TABLE_PORTFOLIO = "tablePortfolio";
    static final String TICKER_PORTFOLIO = "_ticker";
    static final String TABLE_WATCHLIST = "tableWatchlist";
    static final String TICKER_WATCHLIST = "_ticker";
    static final String TABLE_CASH = "tableCash";
    static final String CASH_CASH = "cash";
    static final int SEARCH_STOCK_CODE = 1;
    static final String STOCK = "stock";
    Stock stock = new Stock();
    Portfolio portfolio;
    public ArrayAdapter<Stock> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");

        displayPortfolio(this.portfolio);
        portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
        System.out.println(portfolio.getCash());
        System.out.println(portfolio.toString());


        ListView listView = (ListView) findViewById(R.id.stock_list_view);
        arrayAdapter = new ArrayAdapter<Stock>(
                this,
                R.layout.portfolio_list_view,
                R.id.ticker,
                portfolio.securities
        )

        {
            // this s a definition for an anonymous subclass of ArrayAdapter
            // override the getView() method
            // getView() is called for each item in the data source
            // creates and returns the view for this item
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, ViewGroup parent) {
                // position is the index of the data source

//                for(int i = 0; i < portfolio.getSecuritiesLength(); i++){
//                    Stock stock = portfolio.securities.get(i);
//                    System.out.println(stock.getTicker());
//                    System.out.println(stock.getAmountOwned());
//                    System.out.println(stock.getLastTrade());
//                }

                Stock currStock = portfolio.securities.get(position);

                View view = super.getView(position, convertView, parent);
                // add some customization code to view
                // task: get a reference to the Stock at index position

//                System.out.println(currStock.getTicker());
//                System.out.println(currStock.getAmountOwned());
//                System.out.println(currStock.getLastTrade());


                // next, get a reference to the TextView with id text1
                TextView textView1 = (TextView) view.findViewById(R.id.ticker);
                // task: set the text for textview1
                textView1.setText(currStock.getTicker());
                // task: set the text for textview2 to be the phonenumer
                TextView textView2 = (TextView) view.findViewById(R.id.numSharesOwned);
                // task: set the text for textview1
                textView2.setText("(" + currStock.getAmountOwned() + ")");

                TextView textView3 = (TextView) view.findViewById(R.id.percentChange);
                // task: set the text for textview1
                textView3.setText(String.valueOf(currStock.getChangePercent()));
                TextView textView4 = (TextView) view.findViewById(R.id.sharePrice);
                // task: set the text for textview1
                textView4.setText(String.valueOf(currStock.getLastTrade()));


                return view;

            }
        };


        listView.setAdapter(arrayAdapter);

    }


    public void displayPortfolio(Portfolio portfolio) {
        List<Stock> portSecurities = portfolio.securities;
        Log.d("DISPLAY PORTFOLIO", "--START--");
        Log.d("      Cash", String.valueOf(portfolio.getCash()));
        for (int i=0; i<portSecurities.size(); i++) {
            Log.d("      " + portSecurities.get(i).getTicker(), "(" + String.valueOf(portSecurities.get(i).getAmountOwned()) + ")");
        }
        Log.d("DISPLAY PORTFOLIO", "---END---");
    }

    public void addAction(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        Log.d("TAG", "ADD ACTION");
        EditText editText =  (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();
        String urlRequest = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol="
                + text + "&callback=myFunction";
        APIRequestAsyncTask asyncTask = new APIRequestAsyncTask();
        asyncTask.execute(urlRequest);

        //databaseHelper.insertStock(text, TABLE_PORTFOLIO);
//        List<String> list = databaseHelper.getSelectAllTickers(TABLE_PORTFOLIO);
//
//        for (int i=0; i<list.size(); i++) {
//            Log.d("Table Contents:", list.get(i));
//        }


    }

    public void goToPortfolio(View view){

        double netWorth = portfolio.getNetWorth();
        double cashHoldings = portfolio.getCash();
        double stockHoldings = portfolio.getNetWorth() - portfolio.getCash();
        double dailyGrowth = portfolio.getDailyPortfolioGrowth();
        double careerGrowth = portfolio.getCareerPortfolioGrowth();

        Intent intent = new Intent(MainActivity.this, PortfolioSummaryActivity.class);
        intent.putExtra("netWorth", netWorth);
        intent.putExtra("cashHoldings", cashHoldings);
        intent.putExtra("stockHoldings", stockHoldings);
        intent.putExtra("dailyGrowth", dailyGrowth);
        intent.putExtra("careerGrowth", careerGrowth);
        startActivity(intent);

    }


    public void deleteAction(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        EditText editText =  (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();

        databaseHelper.deleteStock(text, TABLE_PORTFOLIO);

        List<String> list = databaseHelper.getSelectAllTickers(TABLE_PORTFOLIO);

        for (int i=0; i<list.size(); i++) {
//            Log.d("Table Contents:", list.get(i));
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEARCH_STOCK_CODE && resultCode == Activity.RESULT_OK){
            this.portfolio = (Portfolio)  data.getSerializableExtra("portfolio");
           // stock = (Stock) data.getSerializableExtra("stock");
            //portfolio.securities.add(stock);
            arrayAdapter.clear();

            displayPortfolio(this.portfolio);
            displayDB();
            Log.d("securities length", String.valueOf(this.portfolio.getSecurities().size()));
            for (int i=0; i<this.portfolio.getSecurities().size(); i++) {
                Log.d("Add to Adapter", portfolio.getSecurities().get(i).getTicker());
                arrayAdapter.add(this.portfolio.getSecurities().get(i));
            }
            arrayAdapter.notifyDataSetChanged();
        }

    }

    public void displayDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> tickers = databaseHelper.getSelectAllTickers("tablePortfolio");
        List<Integer> amounts = databaseHelper.getAmountOwned();
        Double cash = databaseHelper.getCash();

        Log.d("DISPLAY DB", "--START--");
        Log.d("      Cash", String.valueOf(cash));
        for (int i=0; i<tickers.size(); i++) {
            Log.d("      " + tickers.get(i), "(" + String.valueOf(amounts.get(i)) + ")");
        }
        Log.d("DISPLAY DB", "---END---");
    }



    public Stock xmlParser(String jsonInfo) {
        Stock stock = new Stock();

        try {
            String token = jsonInfo.substring(11, jsonInfo.length()-1);

//            Log.d("Check string", token);
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


    private class APIRequestAsyncTask extends AsyncTask<String, Void, Stock> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // runs on the UI thread
//            Log.d("TAG", "onPreExecute: ");
        }

        // must override doInBackground()
        @Override
        protected Stock doInBackground(String... strings) {
            Stock newStock = null;

            // THIS CODE RUNS ON A BACKGROUND THREAD
            // varargs is the ...
            // variable number of arguments
            // treat like an array
//            Log.d("TAG", "doInBackground: HELLO FROM BACKGROUND THREAD");
//            Log.d("TAG", "doInBackground: " + strings[0]);

            // there are three things we need to do
            // 1. open the request URL
            // 2. download the JSON response
            // 3. extract the meters value from the response
            // come back to this!

            String toReturn = "";

            // step 1
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();

                // 2) get the JSON response
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String result = "";
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }


//                Log.d("TAGLIERE", result);
                //String token = result.substring(11, result.length()-1);
                //Log.d("TAGLIERE", token);
                newStock = xmlParser(result);
//                Log.d("TAGLIERE", newStock.getName());



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //catch (JSONException e) {
            //  e.printStackTrace();
            //}

            // always assume success!!!

            return newStock;
        }

        // background threads cannot update the UI
        // we need to so in a method call onPostExecute(), which
        // runs on the main UI event thread

        @Override
        protected void onPostExecute(Stock resultStock) {
            stock = resultStock;
            Intent intent = new Intent(MainActivity.this, StockDetailsActivity.class);
//            intent.putExtra(STOCK, stock);
            intent.putExtra("ticker", stock.getTicker());
            intent.putExtra("lastTrade", stock.getLastTrade());
            intent.putExtra("name", stock.getName());
            intent.putExtra("change", stock.getChange());
            intent.putExtra("percentChange", stock.getChangePercent());
            intent.putExtra("marketCap", stock.getMarketCapitalization());
            portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
            intent.putExtra("portfolio", portfolio);


            startActivityForResult(intent, SEARCH_STOCK_CODE);
            super.onPostExecute(resultStock);
            // run on the UI thread
//            Log.d("TAG", "onPostExecute: " + String.valueOf(resultStock));
//            Log.d("END GAME", resultStock.getName())
        }
    }
}