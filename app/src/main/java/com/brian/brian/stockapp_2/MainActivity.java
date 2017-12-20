package com.brian.brian.stockapp_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import java.text.DecimalFormat;
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

    static final int SEARCH_STOCK_CODE = 1;
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

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, ViewGroup parent) {

                Stock currStock = portfolio.securities.get(position);

                View view = super.getView(position, convertView, parent);


                // next, get a reference to the TextView with id text1
                TextView textView1 = (TextView) view.findViewById(R.id.ticker);
                // task: set the text for textview1
                textView1.setText(currStock.getTicker());
                // task: set the text for textview2 to be the phonenumer
                TextView textView2 = (TextView) view.findViewById(R.id.numSharesOwned);
                // task: set the text for textview1
                textView2.setText("(" + currStock.getAmountOwned() + ")");

                TextView textView3 = (TextView) view.findViewById(R.id.percentChange);
                if(currStock.getChangePercent() < 0){
                    textView3.setTextColor(Color.RED);

                }
                else{
                    textView3.setTextColor(Color.parseColor("#008000"));
                }


                textView3.setText(String.valueOf(currStock.getChangePercent()));
                TextView textView4 = (TextView) view.findViewById(R.id.sharePrice);
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
        EditText editText =  (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();
        String urlRequest = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol="
                + text + "&callback=myFunction";
        APIRequestAsyncTask asyncTask = new APIRequestAsyncTask();
        asyncTask.execute(urlRequest); //grab real time data of whatever stock was searched


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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEARCH_STOCK_CODE && resultCode == Activity.RESULT_OK){
            this.portfolio = (Portfolio)  data.getSerializableExtra("portfolio");
            arrayAdapter.clear();

            displayPortfolio(this.portfolio);
            for (int i=0; i<this.portfolio.getSecurities().size(); i++) {
                arrayAdapter.add(this.portfolio.getSecurities().get(i));
            }
            arrayAdapter.notifyDataSetChanged();
        }

    }


    public Stock xmlParser(String jsonInfo) {
        Stock stock = new Stock();

        try {
            String token = jsonInfo.substring(11, jsonInfo.length()-1);

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

                newStock = xmlParser(result);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //catch (JSONException e) {

            return newStock;
        }


        @Override
        protected void onPostExecute(Stock resultStock) {
            stock = resultStock;


            Intent intent = new Intent(MainActivity.this, StockDetailsActivity.class);
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
        }
    }
}