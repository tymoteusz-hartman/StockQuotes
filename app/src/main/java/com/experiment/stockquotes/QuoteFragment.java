package com.experiment.stockquotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QuoteFragment extends Fragment {
    private static final String ARG_PARAM1 = "name";

    private String name;
    private Chart chart;

    public QuoteFragment() {
        // Required empty public constructor
    }

    public static QuoteFragment newInstance(String name) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);

        TextView title = (TextView) view.findViewById(R.id.chart_title);
        title.setText(name);
        chart = new Chart(getActivity(), name, (CandleStickChart) view.findViewById(R.id.chart), (TextView) view.findViewById(R.id.quote_date),
                (TextView) view.findViewById(R.id.quote_open), (TextView) view.findViewById(R.id.quote_high), (TextView) view.findViewById(R.id.quote_low),
                (TextView) view.findViewById(R.id.quote_close), (TextView) view.findViewById(R.id.quote_volume));
        setChartData(ChartPeriod.p1M);

        return view;
    }

    public class ChartPeriod {
        static final int p1Y = 1;
        static final int p6M = 2;
        static final int p3M = 3;
        static final int p1M = 4;
        static final int p2W = 5;
        static final int p1W = 6;
        static final int p5D = 7;
        static final int p3D = 8;
    }

    public void setChartData(int chartPeriod) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String currentDate = format.format(calendar.getTime());
        switch(chartPeriod) {
            case ChartPeriod.p1Y:
                calendar.add(Calendar.YEAR, -1);
                break;
            case ChartPeriod.p6M:
                calendar.add(Calendar.MONTH, -6);
                break;
            case ChartPeriod.p3M:
                calendar.add(Calendar.MONTH, -3);
                break;
            case ChartPeriod.p1M:
                calendar.add(Calendar.MONTH, -1);
                break;
            case ChartPeriod.p2W:
                calendar.add(Calendar.WEEK_OF_YEAR, -2);
                break;
            case ChartPeriod.p1W:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case ChartPeriod.p5D:
                calendar.add(Calendar.DAY_OF_YEAR, -5);
                break;
            case ChartPeriod.p3D:
                calendar.add(Calendar.DAY_OF_YEAR, -3);
                break;
        }
        String startDate = format.format(calendar.getTime());
        chart.getData(startDate, currentDate);
    }

    public String getName() {
        return name;
    }
}
