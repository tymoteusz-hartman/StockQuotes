package com.experiment.stockquotes;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chart {

    private String name;
    private CandleStickChart chart;
    private RequestQueue requestQueue;
    private CandleData data;
    private TextView quoteDate;
    private TextView quoteOpen;
    private TextView quoteHigh;
    private TextView quoteLow;
    private TextView quoteClose;
    private TextView quoteVolume;

    public Chart(Context context, String name, CandleStickChart chart, TextView quoteDate, TextView quoteOpen,
                 TextView quoteHigh, TextView quoteLow, TextView quoteClose, TextView quoteVolume) {
        this.name = name;
        this.quoteDate = quoteDate;
        this.quoteOpen = quoteOpen;
        this.quoteHigh = quoteHigh;
        this.quoteLow = quoteLow;
        this.quoteClose = quoteClose;
        this.quoteVolume = quoteVolume;
        this.chart = chart;
        chart.getLegend().setEnabled(false);
        chart.setDescription(null);
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getData(String startDate, String endDate) {

        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22"
                + name + "%22%20and%20startDate%20%3D%20%22" + startDate + "%22%20and%20endDate%20%3D%20%22"
                + endDate + "%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject query = response.getJSONObject("query");
                            int quoteNumber = query.getInt("count");
                            JSONArray dailyQuotes = new JSONArray(query.getJSONObject("results").getJSONArray("quote").toString());
                            ArrayList<CandleEntry> quoteList = new ArrayList<>();
                            ArrayList<String> xVals = new ArrayList<>();
                            for(int i = 0; i < quoteNumber; i++) {
                                JSONObject quote = dailyQuotes.getJSONObject(i);
                                xVals.add(quote.getString("Date"));
                                quoteList.add(new CandleEntry(i, (float) quote.getDouble("Low"), (float) quote.getDouble("High"), (float) quote.getDouble("Open"), (float) quote.getDouble("Close")));
                            }
                            if(quoteNumber > 0) {
                                JSONObject quote = dailyQuotes.getJSONObject(0);
                                quoteDate.setText(quote.getString("Date"));
                                quoteOpen.setText(quote.getString("Open"));
                                quoteHigh.setText(quote.getString("High"));
                                quoteLow.setText(quote.getString("Low"));
                                quoteClose.setText(quote.getString("Close"));
                                quoteVolume.setText(quote.getString("Volume"));
                            }

                            data = new CandleData(xVals, new CandleDataSet(quoteList, name));
                            chart.setData(data);
                            chart.setMaxVisibleValueCount(10);
                            chart.invalidate();
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }
}
