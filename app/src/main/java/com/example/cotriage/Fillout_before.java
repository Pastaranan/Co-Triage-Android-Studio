package com.example.cotriage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Fillout_before extends Activity {
    Button save,hint,hinta;
    EditText SPO2;
    EditText PR;
    EditText up;
    EditText down;
    EditText Temp;

    private long backPressedTime;
    private Toast backToast;

    static public String ID_USER;

    static public String SPO2_s ,PR_s ,up_s ,down_s ,Temp_s;

    double levelSPO2 = 95.00;
    double levelPR = 120;

    double up_rate = 160;
    double down_rate = 100;

    double Temp_degree = 38.5;
    int num1,num2,num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filout_before);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");
        //----------------------------------------------------

        SPO2 = (EditText) findViewById(R.id.SPO2);
        PR = (EditText) findViewById(R.id.PR);
        up = (EditText) findViewById(R.id.up);
        down = (EditText) findViewById(R.id.down);
        Temp = (EditText) findViewById(R.id.Temp);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //กรอกไม่ครบ
                if (TextUtils.isEmpty(SPO2.getText().toString()) || TextUtils.isEmpty(PR.getText().toString()) || TextUtils.isEmpty(up.getText().toString()) || TextUtils.isEmpty(down.getText().toString()) || TextUtils.isEmpty(Temp.getText().toString()) ){
                    Toast.makeText(Fillout_before.this, "กรุณาบันทึกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                }

                else
                {
                    double KeySPO2 = (double) Double.parseDouble(SPO2.getText().toString());
                    double KeyPR = (double) Double.parseDouble(PR.getText().toString());
                    double Keyup = (double) Double.parseDouble(up.getText().toString());
                    double Keydown = (double) Double.parseDouble(down.getText().toString());
                    double KeyTemp = (double) Double.parseDouble(Temp.getText().toString());

                    SPO2_s = SPO2.getText().toString();
                    PR_s = PR.getText().toString();
                    up_s = up.getText().toString();
                    down_s = down.getText().toString();
                    Temp_s = Temp.getText().toString();

                    //ไม่เหมาะกับการทดสอบ
                    if (KeySPO2 <= levelSPO2 || KeyPR >= levelPR || Keyup >= up_rate || Keydown >= down_rate || KeyTemp >= Temp_degree)
                    {
                        //post เมื่อ Abnormal
                        post_data_before();
                        Intent i = new Intent(Fillout_before.this, result_Abnormal_1.class);
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        finish();
                    }

                    // ทดสอบได้
                    else if (KeySPO2 > levelSPO2 && KeyPR < levelPR && Keyup < up_rate && Keydown < down_rate && KeyTemp < Temp_degree)
                    {
                        //post เมื่อ Normal
                        post_data_NORMAL_before();

                        //Toast.makeText(before.this, "เหมาะ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Fillout_before.this, Guide_pre_walk.class);

                        //ส่งข้อมูล
                        EditText _SPO2be = (EditText)findViewById(R.id.SPO2);
                        i.putExtra("SPO2",_SPO2be.getText().toString());
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        ///------------------------------- HINT ---------------------------------------///
        hint = findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), hint.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        hinta = findViewById(R.id.hinta);
        hinta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),hinta.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });
    }

    private  void post_data_before()
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

    private  void post_data_NORMAL_before()
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


//        SPO2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num1 = (int) Integer.parseInt(SPO2.getText().toString());
//                if (num1 >= 95 && num1 < 100 ){
//                    SPO2.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//                else  {
//                    SPO2.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//            }
//        });
//        PR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num2 = (int) Integer.parseInt(PR.getText().toString());
//                if (num2 < 120 ){
//                    PR.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//                else {
//                    PR.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//            }
//        });
//        Temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                num3 = (int) Integer.parseInt(Temp.getText().toString());
//                if (num3 > 37.8 ){
//                    Temp.setTextColor(Color.parseColor("#FF0000"));
//                    finish();
//                }
//                else {
//                    Temp.setTextColor(Color.parseColor("#86B049"));
//                    finish();
//                }
//            }
//        });