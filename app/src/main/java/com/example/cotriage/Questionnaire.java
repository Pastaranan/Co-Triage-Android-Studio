package com.example.cotriage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class Questionnaire extends AppCompatActivity {
    Button send;
    RadioButton yes1 ,no1,yes2,no2,yes3,no3,yes4,no4,yes5,no5;
    RadioGroup group1,group2,group3,group4,group5;
    String yes;
    Dialog myDialog;

    public static String ANS_Q1,ANS_Q2,ANS_Q3,ANS_Q4,ANS_Q5;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire);
        send = findViewById(R.id.send);
        yes1 = findViewById(R.id.yes1);
        yes2 = findViewById(R.id.yes2);
        yes3 = findViewById(R.id.yes3);
        yes4 = findViewById(R.id.yes4);
        yes5 = findViewById(R.id.yes5);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);
        no5 = findViewById(R.id.no5);
        group1 = findViewById(R.id.group1);
        group2 = findViewById(R.id.group2);
        group3 = findViewById(R.id.group3);
        group4 = findViewById(R.id.group4);
        group5 = findViewById(R.id.group5);

        myDialog = new Dialog(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(Questionnaire.this,R.anim.fadein_half);
                send.startAnimation(animation);

                if ( (!yes1.isChecked()&&!no1.isChecked()) || (!yes2.isChecked()&&!no2.isChecked()) || (!yes3.isChecked()&&!no3.isChecked()) || (!yes4.isChecked()&&!no4.isChecked()) || (!yes5.isChecked()&&!no5.isChecked()) )
                {
                    Toast.makeText(Questionnaire.this, "กรุณทำแบบสอบถามให้ครบ", Toast.LENGTH_SHORT).show();
                }


                else {
                    if (yes1.isChecked()||yes2.isChecked()||yes3.isChecked()||yes4.isChecked()||yes5.isChecked())
                    {
                        check_yes_no();
                        post_data_Questionnaire_not_pass();
                        popup_dont_pass();
                        //Toast.makeText(Questionnaire.this, "ท่านไม่ผ่านการประเมินความเสี่ยง", Toast.LENGTH_SHORT).show();
                    }

                    if (no1.isChecked()&& no2.isChecked() && no3.isChecked() && no4.isChecked() && no5.isChecked())
                    {
                        check_yes_no();
                        post_data_Questionnaire_pass();

                        Intent _intent = getIntent();
                        String ID_USER = _intent.getStringExtra("user_id");

                        Intent i = new Intent(Questionnaire.this, Home.class);
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        finish();
                    }
                }

            }
        });
    }

    private void popup_dont_pass(){
        myDialog.setContentView(R.layout.popup_dont_pass);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Questionnaire.this, login1.class);
                startActivity(intent);
                finish();
            }
        },2000);


        TextView closeq = myDialog.findViewById(R.id.closeq);
        closeq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Questionnaire.this, login1.class);
                startActivity(i);
                finish();
            }
        });

        myDialog.show();
    }

    private  void check_yes_no()
    {
        if(yes1.isChecked())
        {
            ANS_Q1="ใช่";
        }
        else
        {
            ANS_Q1="ไม่ใช่";
        }
        //-----------------------------
        if(yes2.isChecked())
        {
            ANS_Q2="ใช่";
        }
        else
        {
            ANS_Q2="ไม่ใช่";
        }
        //-----------------------------
        if(yes3.isChecked())
        {
            ANS_Q3="ใช่";
        }
        else
        {
            ANS_Q3="ไม่ใช่";
        }
        //-----------------------------
        if(yes4.isChecked())
        {
            ANS_Q4="ใช่";
        }
        else
        {
            ANS_Q4="ไม่ใช่";
        }
        //-----------------------------
        if(yes5.isChecked())
        {
            ANS_Q5="ใช่";
        }
        else
        {
            ANS_Q5="ไม่ใช่";
        }
    }

    private  void post_data_Questionnaire_pass()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/questionnaire",
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
                params.put("Question_1",ANS_Q1);
                params.put("Question_2",ANS_Q2);
                params.put("Question_3",ANS_Q3);
                params.put("Question_4",ANS_Q4);
                params.put("Question_5",ANS_Q5);
                params.put("Question_Status","ผ่าน");
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    private  void post_data_Questionnaire_not_pass()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/questionnaire",
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
                params.put("Question_1",ANS_Q1);
                params.put("Question_2",ANS_Q2);
                params.put("Question_3",ANS_Q3);
                params.put("Question_4",ANS_Q4);
                params.put("Question_5",ANS_Q5);
                params.put("Question_Status","ไม่ผ่าน");
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
            Intent intent = new Intent(getApplicationContext(),login1.class);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อออกจากระบบ", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}