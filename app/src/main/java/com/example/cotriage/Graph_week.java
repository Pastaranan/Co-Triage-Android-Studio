package com.example.cotriage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class Graph_week extends AppCompatActivity {
    GraphView graph,graph1,graph2;
    private int mSize = 10;
    Button swithday,backhome;

    static public String ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_week);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");


        View activity;
        GraphView graph = (GraphView) findViewById(R.id.graph);
        initGraph(graph);

        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        initGraph1(graph1);
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        initGraph2(graph2);

        swithday = findViewById(R.id.swithday);
        swithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Graph_week.this, Graph.class);
                i.putExtra("user_id",ID_USER);
                startActivity(i);
                finish();
            }
        });

        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Graph_week.this, Home.class);
                i.putExtra("user_id",ID_USER);
                startActivity(i);
                finish();
            }
        });
    }

    private void initGraph(GraphView graph) {

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 98),
                new DataPoint(2, 95),
                new DataPoint(3, 97),
                new DataPoint(4, 95.8),
                new DataPoint(5, 96.5),
                new DataPoint(6, 95),
                new DataPoint(7, 99),
                new DataPoint(8, 91)
        });

        series.setAnimated(true);
        graph.addSeries(series);
        series.setSpacing(20);
        series.setColor(Color.argb(255, 86, 191, 197));



    }


    private void initGraph1(GraphView graph) {

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 72),
                new DataPoint(2, 80),
                new DataPoint(3, 67),
                new DataPoint(4, 75),
                new DataPoint(5, 78),
                new DataPoint(6, 82),
                new DataPoint(7, 74),
                new DataPoint(8, 65)
        });

        series.setAnimated(true);
        graph.addSeries(series);
        series.setSpacing(20);
        series.setColor(Color.argb(255, 86, 191, 197));



    }

    private void initGraph2(GraphView graph) {

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 120),
                new DataPoint(1, 130),
                new DataPoint(2, 90),
                new DataPoint(3, 120),
                new DataPoint(4, 130),
                new DataPoint(5, 120),
                new DataPoint(6, 110),
                new DataPoint(7, 135),
                new DataPoint(8, 75)
        });
        graph.addSeries(series);
        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setSize(mSize);

        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 75),
                new DataPoint(1, 100),
                new DataPoint(2, 50),
                new DataPoint(3, 75),
                new DataPoint(4, 90),
                new DataPoint(5, 75),
                new DataPoint(6, 70),
                new DataPoint(7, 100),
                new DataPoint(8, 75)
        });
        graph.addSeries(series2);
        series2.setShape(PointsGraphSeries.Shape.RECTANGLE);
        series2.setColor(Color.RED);
        series2.setSize(mSize);



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Graph.class);
        intent.putExtra("user_id",ID_USER);
        startActivity(intent);
        finish();
    }
}