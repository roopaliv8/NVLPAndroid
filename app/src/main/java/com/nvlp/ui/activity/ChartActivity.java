package com.nvlp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;
import com.nvlp.R;
import com.nvlp.databinding.ActivityChartBinding;
import com.nvlp.model.response.ChartDatum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.nvlp.utils.Constants.DEMOURL;

public class ChartActivity extends BaseActivity {
    ActivityChartBinding binding;
    String demodata = "[{\"name\":\"BTC\",\"key\":\"btc\",\"value\":70},{\"name\":\"ETH\",\"key\":\"eth\",\"value\":80},{\"name\":\"LTE\",\"key\":\"lte\",\"value\":79},{\"name\":\"RPL\",\"key\":\"rpl\",\"value\":23},{\"name\":\"PLN\",\"key\":\"pln\",\"value\":38},{\"name\":\"USD\",\"key\":\"usd\",\"value\":85},{\"name\":\"EUR\",\"key\":\"eur\",\"value\":67},{\"name\":\"THB\",\"key\":\"thb\",\"value\":23}]";

    List<ChartDatum> datumList;
    private ServerSentEvent sse;

    private final int min = 1;
    private final int max = 5;
    private boolean isPlus = true; //TODO need to remove
    private static final String TAG = "ChartActivity";


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

        callSSE(getIntent().getStringExtra("token"));


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

    private void callSSE(final String token) {
        final Request request = new Request.Builder().addHeader("Content-Type", "application/json")
                .addHeader("X-JWT", token).url(DEMOURL).build();

        final OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.SECONDS).build();
        OkSse oksse = new OkSse(client);
        enableLoader(true);
        sse = oksse.newServerSentEvent(request, new ServerSentEvent.Listener() {
            @Override
            public void onOpen(ServerSentEvent sse, Response response) {
                // When the channel is opened
                Log.d(TAG, "onOpen: ");

            }

            @Override
            public void onMessage(ServerSentEvent sse, String id, String event, String message) {
                Log.d(TAG, "onMessage: ");
                // When a message is received
                if (datumList == null) {
                    parseJSON();
                    runOnUiThread(() -> {
                        binding.chart.setDescription("");
                        binding.chart.animateXY(2000, 2000);
                        binding.chart.invalidate();
                    });
                    enableLoader(false);
                } else {
                    List<ChartDatum> datalist = new ArrayList<>();
                    for (ChartDatum chartDatum : datumList) {
                        int random = new Random().nextInt((max - min) + 1) + min;
                        if (isPlus)
                            chartDatum.setValue(chartDatum.getValue() - random);
                        else
                            chartDatum.setValue(chartDatum.getValue() + random);
                        datalist.add(chartDatum);
                    }
                    isPlus = !isPlus;
                    runOnUiThread(() -> {
                        addchatData(datalist);
                        binding.chart.notifyDataSetChanged();
                        binding.chart.invalidate();
                    });
                }


            }

            @WorkerThread
            @Override
            public void onComment(ServerSentEvent sse, String comment) {
                Log.d(TAG, "onComment: ");
                // When a comment is received
            }

            @WorkerThread
            @Override
            public boolean onRetryTime(ServerSentEvent sse, long milliseconds) {
                Log.d(TAG, "onRetryTime: ");
                return true; // True to use the new retry time received by SSE
            }

            @WorkerThread
            @Override
            public boolean onRetryError(ServerSentEvent sse, Throwable throwable, Response response) {
                Log.d(TAG, "onRetryError: ");
                return true; // True to retry, false otherwise
            }

            @WorkerThread
            @Override
            public void onClosed(ServerSentEvent sse) {
                Log.d(TAG, "onClosed: ");
                // Channel closed
                startLogin();
            }

            @Override
            public Request onPreRetry(ServerSentEvent sse, Request originalRequest) {
                Log.d(TAG, "onPreRetry: ");
                return null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sse.close();
    }

    private void startLogin() {
        runOnUiThread(() -> {
            Toast.makeText(this, getString(R.string.sessionexpired), Toast.LENGTH_LONG).show();
        });


        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
