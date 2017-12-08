package com.brian.brian.stockapp_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.util.Log;

import com.nizhegorodtsev.Stock;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkunthara on 12/4/17.
 */

public class StockDetailsActivity extends AppCompatActivity{

    final DecimalFormat decimalFormat1 = new DecimalFormat("$##0.00");
    final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");
    final DecimalFormat decimalFormat3 = new DecimalFormat("##0.00");
    Portfolio portfolio;
    Stock stock = new Stock();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            final double lastTrade = extras.getDouble("lastTrade", 1);
            double change = extras.getDouble("change", 1);
            double changePercent = extras.getDouble("percentChange", 1);
            double marketCap = extras.getDouble("marketCap", 1);
            String ticker = extras.getString("ticker","");
            String name = extras.getString("name","");
//            this.stock = new Stock();
            this.stock.setProperty("ticker", ticker);
            this.stock.setProperty("change", decimalFormat1.format(change));
            this.stock.setProperty("changePercent", decimalFormat3.format(changePercent));
            this.stock.setProperty("lastTrade", lastTrade);


            this.portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");

            TextView stockLastTrade = (TextView) findViewById(R.id.lastTrade);
            TextView stockTicker = (TextView) findViewById(R.id.ticker);
            TextView stockName = (TextView) findViewById(R.id.name);
            TextView stockChange = (TextView) findViewById(R.id.dayChange);
            if(change > 0){
                stockChange.setTextColor(Color.parseColor("#008000"));
            }
            else{
                stockChange.setTextColor(Color.RED);
            }
            TextView stockPercentChange = (TextView) findViewById(R.id.percent);
            if(changePercent > 0){
                stockPercentChange.setTextColor(Color.parseColor("#008000"));
            }
            else{
                stockPercentChange.setTextColor(Color.RED);
            }
            TextView stockMarketCap = (TextView) findViewById(R.id.cap);


            stockLastTrade.setText(String.valueOf(decimalFormat1.format(lastTrade)));
            stockTicker.setText(ticker);
            stockName.setText(name);
            stockChange.setText(String.valueOf(decimalFormat1.format(change)));
            stockPercentChange.setText(String.valueOf(decimalFormat2.format(changePercent)) + "%");
            stockMarketCap.setText(String.valueOf(decimalFormat1.format(marketCap)));


            EditText sharesTobuy = (EditText)findViewById(R.id.numSharesToBuy);
            EditText sharesToSell = (EditText)findViewById(R.id.numSharesToSell);

//            double numSharesToBuy = Double.parseDouble(sharesTobuy.getText().toString());
//            String totalSharesToBuy = Double.toString(numSharesToBuy * lastTrade);
//            TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
//            totalPrice.setText(totalSharesToBuy);

            TextWatcher inputTextWatcherBuy = new TextWatcher() {
                public void afterTextChanged(Editable s) {

                    if(!s.toString().equals("")){
                        double numSharesToBuy = Double.parseDouble(s.toString());
                        String totalSharesToBuy = Double.toString(numSharesToBuy * lastTrade);
                        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
                        totalPrice.setTextColor(Color.RED);
                        totalPrice.setText(totalSharesToBuy);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after){
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(!s.toString().equals("")){
                        double numSharesToBuy = Double.parseDouble(s.toString());
                        String totalSharesToBuy = Double.toString(numSharesToBuy * lastTrade);
                        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
                        totalPrice.setTextColor(Color.RED);
                        totalPrice.setText(totalSharesToBuy);
                    }

                }
            };


            TextWatcher inputTextWatcherSell = new TextWatcher() {
                public void afterTextChanged(Editable s) {


                    if(!s.toString().trim().equals("")){
                        double numSharesToSell = Double.parseDouble(s.toString());
                        String totalSharesToSell = Double.toString(numSharesToSell * lastTrade);
                        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
                        totalPrice.setTextColor(Color.parseColor("#008000"));
                        totalPrice.setText(totalSharesToSell);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after){
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().equals("")){
                        double numSharesToSell = Double.parseDouble(s.toString());
                        String totalSharesToSell = Double.toString(numSharesToSell * lastTrade);
                        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
                        totalPrice.setTextColor(Color.parseColor("#008000"));
                        totalPrice.setText(totalSharesToSell);
                    }
                }
            };

            sharesTobuy.addTextChangedListener(inputTextWatcherBuy);
            sharesToSell.addTextChangedListener(inputTextWatcherSell);



        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                StockDetailsActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void buyShares(View view){

        TextView priceView = (TextView) findViewById(R.id.totalPrice);
        TextView  sharesToBuyView = (TextView) findViewById(R.id.numSharesToBuy);

        double price = Double.valueOf(priceView.getText().toString());
        int sharesToBuy = Integer.valueOf(sharesToBuyView.getText().toString());
        this.stock.setAmountSharesOwned(sharesToBuy);

        portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
        portfolio.purchase(this.stock, this);

        Intent intent = new Intent();
        intent.putExtra("portfolio", this.portfolio);
        //intent.putExtra("stock", stock);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }


    public void sellShares(View view){

        TextView priceView = (TextView) findViewById(R.id.totalPrice);
        TextView  sharesToSellView = (TextView) findViewById(R.id.numSharesToSell);

        double price = Double.valueOf(priceView.getText().toString());
        int sharesToSell = Integer.valueOf(sharesToSellView.getText().toString());
        this.stock.setAmountSharesOwned(sharesToSell);

        portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
        portfolio.sell(this.stock, this);
        Log.d("ON SALE", this.stock.getTicker() + String.valueOf(this.stock.getAmountOwned()));
        System.out.println("You sold some stock!!!");
        Intent intent = new Intent();
        intent.putExtra("portfolio", this.portfolio);
        //intent.putExtra("stock", stock);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }




}

