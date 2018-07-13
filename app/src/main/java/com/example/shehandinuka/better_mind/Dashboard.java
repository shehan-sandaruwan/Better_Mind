package com.example.shehandinuka.better_mind;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Dashboard extends AppCompatActivity {

    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        graphView = findViewById(R.id.graph);
        final Button button = findViewById(R.id.Ebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    showGraph(graphView);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showGraph( GraphView view) throws ParseException {

        ArrayList<Double> sadVal = (ArrayList <Double>) MyserviceClass.sadVal;
        ArrayList<Double> happyVal = (ArrayList <Double>) MyserviceClass.happyVal;
        ArrayList<Double> neutralVal = (ArrayList <Double>) MyserviceClass.neutralVal;
        ArrayList<String> time = (ArrayList <String>) MyserviceClass.time;
        DataPoint[] dataPoints1 = new DataPoint[time.size()];
        DataPoint[] dataPoints2 = new DataPoint[time.size()];
        DataPoint[] dataPoints3 = new DataPoint[time.size()];
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        Date date;
        for (int i = 0; i < time.size(); i++) {
            dataPoints1[i] = new DataPoint(i,sadVal.get(i)+0.25);
            //dataPoints2[i] = new DataPoint(i,happyVal.get(i)+0.25);
            dataPoints3[i] = new DataPoint(i,neutralVal.get(i)+0.25);

        }
        view.setTitle("Emotions variation");
        view.getViewport().setScrollable(true); // enables horizontal scrolling
        view.getViewport().setScalable(true); //

        view.getViewport().setMaxY(2.000);
        view.getViewport().setMaxY(0.000);

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(dataPoints1);
        view.addSeries(series1);
       // LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(dataPoints2);
       // view.addSeries(series2);
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(dataPoints3);
        view.addSeries(series3);

        series1.setTitle("Sad");
        //series2.setTitle("Happy");
        series3.setTitle("Neutral");

        series1.setColor(Color.rgb(230, 0, 0));
        //series2.setColor(Color.rgb(0, 204, 255));
        series3.setColor(Color.rgb(255, 255, 102));
        view.getLegendRenderer().setVisible(true);
        view.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

    }
    }