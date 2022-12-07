package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Fillout_after extends AppCompatActivity {
    Button save2,hintb,hintc;
    EditText SPO2_af,PR_af,Temp_af,up_af,down_af;
    int num1,num2,num3;
    static public String SPO2_s ,PR_s ,up_s ,down_s ,Temp_s;

    static public String ID_USER,_SPO2be;

    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillout_after);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        //รับข้อมูล
        Intent _inboundIndex = getIntent();
        _SPO2be = _inboundIndex.getStringExtra("SPO2");
        //Toast.makeText(getApplicationContext(),_SPO2be,Toast.LENGTH_SHORT).show();
        //----------------------------------------------------

        SPO2_af = (EditText) findViewById(R.id.SPO2_af);
        PR_af = (EditText) findViewById(R.id.PR_af);
        up_af = (EditText) findViewById(R.id.up_af);
        down_af = (EditText) findViewById(R.id.down_af);
        Temp_af = (EditText) findViewById(R.id.Temp_af);



        ///------------------------------- Save ---------------------------------------///
        save2 = findViewById(R.id.save2);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //กรอกไม่ครบ
                if (TextUtils.isEmpty(SPO2_af.getText().toString())){
                    Toast.makeText(Fillout_after.this, "กรุณาบันทึก ระดับออกซิเจนในเลือด", Toast.LENGTH_LONG).show();
                }

                else
                {
                    double KeySPO2_af = (double) Double.parseDouble(SPO2_af.getText().toString());
                    double KeySPO2_be = (double) Double.parseDouble(_SPO2be);
                    double Compare =  KeySPO2_be-KeySPO2_af;
                    String Compare_string =  String.valueOf(Compare);


                    SPO2_s = SPO2_af.getText().toString();
                    PR_s = PR_af.getText().toString();
                    up_s = up_af.getText().toString();
                    down_s = down_af.getText().toString();
                    Temp_s = Temp_af.getText().toString();

                    if(TextUtils.isEmpty(PR_af.getText().toString()))
                    {
                        PR_s = "0";
                    }

                    if(TextUtils.isEmpty(up_af.getText().toString()))
                    {
                        up_s = "0";
                    }

                    if(TextUtils.isEmpty(down_af.getText().toString()))
                    {
                        down_s = "0";
                    }

                    if(TextUtils.isEmpty(Temp_af.getText().toString()))
                    {
                        Temp_s = "36";
                    }


                    // ผล แดง
                    if (Compare >= 3 )
                    {
                        post_AB_NORMAL_data_After();

                        Intent i = new Intent(Fillout_after.this, result_Abnormal_1.class);
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        finish();
                    }

                    // ผล เขียว
                    else if(Compare < 3 )
                    {
                        post_NORMAL_data_After();

                        Intent i = new Intent(Fillout_after.this, result_normal_1.class);
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        ///------------------------------- HINT ---------------------------------------///
        hintb = findViewById(R.id.hintb);
        hintb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),hintb.class);
                intent.putExtra("SPO2",_SPO2be);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        hintc = findViewById(R.id.hintc);
        hintc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),hintc.class);
                intent.putExtra("SPO2",_SPO2be);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });
    }

    private  void post_NORMAL_data_After()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/vitalsign",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { Log.i("Response", response);
                        System.out.println(response);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Log.d("Error.Response", "Unsuccess");
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User_ID", ID_USER);              //this is user_id
                params.put("SpO2",SPO2_s);
                params.put("Heart_rate",PR_s);
                params.put("Blood_pressure_up",up_s);
                params.put("Blood_pressure_down",down_s);
                params.put("Temperature",Temp_s);
                params.put("Test_Status","ปกติ");
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    private  void post_AB_NORMAL_data_After()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/vitalsign",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { Log.i("Response", response);
                        System.out.println(response);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Log.d("Error.Response", "Unsuccess");
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User_ID", ID_USER);              //this is user_id
                params.put("SpO2",SPO2_s);
                params.put("Heart_rate",PR_s);
                params.put("Blood_pressure_up",up_s);
                params.put("Blood_pressure_down",down_s);
                params.put("Temperature",Temp_s);
                params.put("Test_Status","ผิดปกติ");
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            intent.putExtra("user_id",ID_USER);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อกลับหน้าหลัก", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
//
//        SPO2_af.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num1 = Integer.parseInt(SPO2_af.getText().toString());
//                if (num1 >= 95 && num1 < 100 ){
//                    SPO2_af.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//                else {
//                    SPO2_af.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//            }
//        });
//        PR_af.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num2 = Integer.parseInt(PR_af.getText().toString());
//                if (num2 < 120 ){
//                    PR_af.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//                else {
//                    PR_af.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//            }
//        });
//        Temp_af.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num3 = Integer.parseInt(Temp_af.getText().toString());
//                if (num3 > 37.8 ){
//                    Temp_af.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//                else {
//                    Temp_af.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//            }
//        });