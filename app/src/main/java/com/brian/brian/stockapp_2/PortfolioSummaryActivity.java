package com.brian.brian.stockapp_2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PortfolioSummaryActivity extends AppCompatActivity {

    final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();

        if(extras != null){


            double cashHoldings = extras.getDouble("cashHoldings", 1);
            double stockHoldings = extras.getDouble("stockHoldings", 1);
            double dailyGrowth = extras.getDouble("dailyGrowth", 1) / 100;
            double careerGrowth = extras.getDouble("careerGrowth", 1) / 1000;

            PieChart pieChart = (PieChart) findViewById(R.id.chart);

            Legend legend = pieChart.getLegend();
            legend.setEnabled(true);
            legend.setTextSize(20);
            legend.setXEntrySpace(15);


            List<PieEntry> entries = new ArrayList<>();

            float cash = (float) cashHoldings;
            float stock = (float) stockHoldings;


            entries.add(new PieEntry(cash, "Cash"));
            entries.add(new PieEntry(stock, "Stocks"));

            int[] colors = new int[2];
            colors[0] = R.color.cash;
            colors[1] = R.color.stock;
            PieDataSet set = new PieDataSet(entries, "");
            set.setValueTextSize(15f);
            set.setColors(colors, this);
            PieData data = new PieData(set);
            pieChart.setData(data);
            pieChart.setDrawSlicesUnderHole(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setEntryLabelTextSize(15f);
            pieChart.invalidate(); // refresh

            TextView dailyGrowthAmount = (TextView) findViewById(R.id.dailyGrowthAmount);
            TextView careerGrowthAmount = (TextView) findViewById(R.id.careerGrowthAmount);

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
