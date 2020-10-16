package com.nvlp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.nvlp.R;
import com.nvlp.databinding.ActivityChartBinding;
import com.nvlp.model.response.ChartDatum;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

public class ChartActivity extends AppCompatActivity {
    ActivityChartBinding binding;
    String demodata = "[{\"name\":\"BTC\",\"key\":\"btc\",\"value\":70},{\"name\":\"ETH\",\"key\":\"eth\",\"value\":80},{\"name\":\"LTE\",\"key\":\"lte\",\"value\":79},{\"name\":\"RPL\",\"key\":\"rpl\",\"value\":23},{\"name\":\"PLN\",\"key\":\"pln\",\"value\":38},{\"name\":\"USD\",\"key\":\"usd\",\"value\":85},{\"name\":\"EUR\",\"key\":\"eur\",\"value\":67},{\"name\":\"THB\",\"key\":\"thb\",\"value\":23}]";

    List<ChartDatum> datumList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_chart);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle(getString(R.string.cryptochart));

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        parseJSON();

        binding.chart.setDescription("");
        binding.chart.animateXY(2000, 2000);
        binding.chart.invalidate();

        final int min = 10;
        final int max = 200;


        //Declare the timer
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      List<ChartDatum> datalist = new ArrayList<>();
                                      for (ChartDatum chartDatum : datumList) {
                                          int random = new Random().nextInt((max - min) + 1) + min;
                                          chartDatum.setValue(random);
                                          datalist.add(chartDatum);
                                      }
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              addchatData(datalist);
                                              binding.chart.notifyDataSetChanged();
                                              binding.chart.invalidate();
                                          }//public void run() {
                                      });

                                  }

                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                10000);


    }

    private void parseJSON() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ChartDatum>>() {
        }.getType();
        datumList = gson.fromJson(demodata, type);
        addchatData(datumList);

    }

    private void addchatData(List<ChartDatum> list) {
        ArrayList<String> xAxisVal = new ArrayList<>();
        ArrayList barEntries = new ArrayList();
        int i = 0;
        for (ChartDatum data : list) {
            xAxisVal.add(data.getName());
            barEntries.add(new BarEntry((float) data.getValue(), i));
            i++;
        }

        BarDataSet barDataSet1 = new BarDataSet(barEntries, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        ArrayList dataSets = new ArrayList();
        dataSets.add(barDataSet1);


        BarData data = new BarData(xAxisVal, dataSets);
        binding.chart.setData(data);

    }

}
