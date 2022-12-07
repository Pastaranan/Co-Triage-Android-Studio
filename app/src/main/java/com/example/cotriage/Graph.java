package com.example.cotriage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Graph extends AppCompatActivity {
    //    GraphView graph,graph1;
    private int mSize = 10;
    Button swithweek,backhome;

    TextView date, lasttime, before_heart, after_heart, be_spo2, af_spo2,temp, mmHg_up_be, mmHg_down_be, mmHg_up_af, mmHg_down_af;
    static public Integer before_heart_num, after_heart_num, be_spo2_num, af_spo2_num;

    static public Number before_heart_N, after_heart_N;

    static public String before_heart_S, after_heart_S, be_spo2_S, af_spo2_S, temp_S, mmHg_up_be_S, mmHg_down_be_S, mmHg_up_af_S, mmHg_down_af_S;
    static public String date_S, lasttime_S;


    private long backPressedTime;
    private Toast backToast;

    static public String ID_USER;

    ProgressBar hartratebefore,hartrateafter,oxigenbefore,oxigenafter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");




        //-----------------------------------------------------------


        date = findViewById(R.id.date);
        lasttime = findViewById(R.id.lasttime);

        before_heart = findViewById(R.id.before_heart);
        after_heart = findViewById(R.id.after_heart);

        be_spo2 = findViewById(R.id.be_spo2);
        af_spo2 = findViewById(R.id.af_spo2);

        temp = findViewById(R.id.temp);

        mmHg_up_be = findViewById(R.id.mmHg_up_be);
        mmHg_down_be = findViewById(R.id.mmHg_down_be);

        mmHg_up_af = findViewById(R.id.mmHg_up_af);
        mmHg_down_af = findViewById(R.id.mmHg_down_af);



        //- - GET
        get_data_vital_user();



        swithweek = findViewById(R.id.swithweek);
        swithweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Graph_week.class);
                intent.putExtra("user_id", ID_USER);
                startActivity(intent);
                finish();
            }
        });

        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("user_id", ID_USER);
                startActivity(intent);
                finish();
            }
        });
    }

    private void get_data_vital_user(){

        Log.d("12345689", "in");

        final String url = "http://iiec2312.trueddns.com:19744/vitalsign/"+ID_USER;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            JSONArray jsonArray_test = response.getJSONArray("data");

                            if ( jsonArray_test.length() == 0 ) {

                                date.setText("วันที่ --");
                                lasttime.setText("ครั้งล่าสุด : --");

                                before_heart.setText("--");
                                after_heart.setText("--");

                                be_spo2.setText("--");
                                af_spo2.setText("--");

                                temp.setText("--");

                                mmHg_up_be.setText("--");
                                mmHg_down_be.setText("--");

                                mmHg_up_af.setText("--");
                                mmHg_down_af.setText("--");

                            }

                            else if ( jsonArray_test.length() >= 1 )
                            {

                                //ก่อน----------------------------------------------------------------------
                                JSONObject object_index_1 = jsonArray_test.getJSONObject(1);

                                before_heart_S = object_index_1.getString("Heart_rate");

                                be_spo2_S = object_index_1.getString("SpO2");

                                temp_S = object_index_1.getString("Temperature");

                                mmHg_up_be_S = object_index_1.getString("Blood_pressure_up");
                                mmHg_down_be_S  = object_index_1.getString("Blood_pressure_down");


                                //หลัง------------------------------------------------------------------------
                                JSONObject object_index_0 = jsonArray_test.getJSONObject(0);

                                after_heart_S = object_index_0.getString("Heart_rate");

                                af_spo2_S = object_index_0.getString("SpO2");

                                temp_S = object_index_0.getString("Temperature");

                                mmHg_up_af_S = object_index_0.getString("Blood_pressure_up");
                                mmHg_down_af_S = object_index_0.getString("Blood_pressure_down");

                                String last_test = object_index_0.getString("Create_at");
                                date_S = last_test.substring(0,10);
                                lasttime_S = last_test.substring(11,16);

                                //หลัง--------------------------------------------------------------

                                before_heart.setText(before_heart_S);
                                after_heart.setText(after_heart_S);

                                be_spo2.setText(be_spo2_S);
                                af_spo2.setText(af_spo2_S);

                                temp.setText(temp_S);


                                mmHg_up_af.setText(mmHg_up_af_S);
                                mmHg_down_af.setText(mmHg_down_af_S);


                                if(after_heart_S.equals("0")){
                                    after_heart.setText("-");
                                }

                                if(mmHg_up_af_S.equals("0")){
                                    mmHg_up_af.setText("-");
                                }

                                if(mmHg_down_af_S.equals("0")){
                                    mmHg_down_af.setText("-");
                                }



                                mmHg_up_be.setText(mmHg_up_be_S);
                                mmHg_down_be.setText(mmHg_down_be_S);



                                date.setText("วันที่ "+date_S);
                                lasttime.setText("ครั้งล่าสุด : เวลา "+lasttime_S+" น.");


                                //----num get---
                                before_heart_num = object_index_1.getInt("Heart_rate");
                                after_heart_num  = object_index_0.getInt("Heart_rate");
                                be_spo2_num = object_index_1.getInt("SpO2");
                                af_spo2_num = object_index_0.getInt("SpO2");

                                graph_plot();

                            }
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        Log.d("12345689", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue.add(getRequest);
    }


    private void graph_plot() {
        //----------------------------------------------------------------------graph

        int a = before_heart_num-20;
        int b = after_heart_num-20;

        hartratebefore = findViewById(R.id.hartratebefore);
        hartratebefore.setProgress(a);
        hartrateafter = findViewById(R.id.hartrateafter);
        hartrateafter.setProgress(b);


        oxigenbefore = findViewById(R.id.oxigenbefore);
        oxigenbefore.setProgress(be_spo2_num);
        oxigenafter = findViewById(R.id.oxigenafter);
        oxigenafter.setProgress(af_spo2_num);

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        intent.putExtra("user_id",ID_USER);
        startActivity(intent);
        finish();
    }

}