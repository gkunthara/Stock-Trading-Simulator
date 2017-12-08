package com.brian.brian.stockapp_2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PortfolioSummaryActivity extends AppCompatActivity {

    final DecimalFormat decimalFormat1 = new DecimalFormat("$##0.00");
    final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();

        if(extras != null){

            double netWorth = extras.getDouble("netWorth", 1);
            double cashHoldings = extras.getDouble("cashHoldings", 1);
            double stockHoldings = extras.getDouble("stockHoldings", 1);
            double dailyGrowth = extras.getDouble("dailyGrowth", 1);
            double careerGrowth = extras.getDouble("careerGrowth", 1);

            TextView dailyGrowthAmount = (TextView) findViewById(R.id.dailyGrowthAmount);
            TextView netWorthAmount = (TextView) findViewById(R.id.netWorthAmount);
            TextView cashHoldingsAmount = (TextView) findViewById(R.id.cashHoldingsAmount);
            TextView careerGrowthAmount = (TextView) findViewById(R.id.careerGrowthAmount);
            TextView stockHoldingsAmount = (TextView) findViewById(R.id.stockHoldingsAmount);

            if(dailyGrowth > 0){
                dailyGrowthAmount.setTextColor(Color.parseColor("#008000"));
            }
            else{
                dailyGrowthAmount.setTextColor(Color.RED);
            }

            if(careerGrowth > 0){
                careerGrowthAmount.setTextColor(Color.parseColor("#008000"));
            }
            else{
                careerGrowthAmount.setTextColor(Color.RED);
            }


            dailyGrowthAmount.setText(String.valueOf(decimalFormat2.format(dailyGrowth)) + "%");
            netWorthAmount.setText(String.valueOf(decimalFormat1.format(netWorth)));
            cashHoldingsAmount.setText(String.valueOf(decimalFormat1.format(cashHoldings)));
            stockHoldingsAmount.setText(String.valueOf(decimalFormat1.format(stockHoldings)));
            careerGrowthAmount.setText(String.valueOf(decimalFormat2.format(careerGrowth)) + "%");

        }




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                PortfolioSummaryActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
