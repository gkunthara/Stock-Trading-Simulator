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
import android.widget.TextView;
import android.widget.Toast;
import com.nizhegorodtsev.Stock;
import java.text.DecimalFormat;


public class StockDetailsActivity extends AppCompatActivity{

    final DecimalFormat decimalFormat1 = new DecimalFormat("##0.00");
    final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");
    final DecimalFormat decimalFormat3 = new DecimalFormat("##0.0");
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
            String marketCap = extras.getString("marketCap", "");
            marketCap = marketCap.substring(0,3);
            String ticker = extras.getString("ticker","");
            String name = extras.getString("name","");
            this.stock.setProperty("ticker", ticker);
            this.stock.setProperty("change", decimalFormat1.format(change));
            this.stock.setProperty("changePercent", decimalFormat1.format(changePercent));
            this.stock.setProperty("lastTrade", decimalFormat3.format(lastTrade));


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
            stockMarketCap.setText((marketCap));



            EditText sharesTobuy = (EditText)findViewById(R.id.numSharesToBuy);
            EditText sharesToSell = (EditText)findViewById(R.id.numSharesToSell);

            TextWatcher inputTextWatcherBuy = new TextWatcher() {
                public void afterTextChanged(Editable s) {

                    if(!s.toString().equals("")){
                        double numSharesToBuy = Double.parseDouble(s.toString());
                        String totalSharesToBuy = Double.toString(numSharesToBuy * lastTrade);
                        totalSharesToBuy = decimalFormat1.format(Double.valueOf(totalSharesToBuy));
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
                        totalSharesToBuy = decimalFormat1.format(Double.valueOf(totalSharesToBuy));
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
                        totalSharesToSell = decimalFormat1.format(Double.valueOf(totalSharesToSell));
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
                        totalSharesToSell = decimalFormat1.format(Double.valueOf(totalSharesToSell));
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

        TextView  sharesToBuyView = (TextView) findViewById(R.id.numSharesToBuy);

        int sharesToBuy = 0;
        try {
            sharesToBuy = Integer.valueOf(sharesToBuyView.getText().toString());
        } catch (NumberFormatException e) {
            sharesToBuy = 0;
        }
        this.stock.setAmountSharesOwned(sharesToBuy);

        portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
        if(portfolio.purchase(this.stock, this)){
            Intent intent = new Intent();
            intent.putExtra("portfolio", this.portfolio);
            setResult(Activity.RESULT_OK, intent);
            this.finish();
        }
        else{ //if unable to make purchase
            Toast.makeText(this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
        }
        }




    public void sellShares(View view){

        TextView  sharesToSellView = (TextView) findViewById(R.id.numSharesToSell);

        int sharesToSell = 0;
        try {
            sharesToSell = Integer.valueOf(sharesToSellView.getText().toString());
        } catch (NumberFormatException e) {
                sharesToSell = 0;
        }
        this.stock.setAmountSharesOwned(sharesToSell);

        portfolio = (Portfolio) getIntent().getSerializableExtra("portfolio");
        if(portfolio.sell(this.stock, this)){
            Intent intent = new Intent();
            intent.putExtra("portfolio", this.portfolio);
            setResult(Activity.RESULT_OK, intent);
            this.finish();
        }
        else{ //if unable to sell shares
            Toast.makeText(this, "Unable to sell stock", Toast.LENGTH_SHORT).show();
        }

    }




}

