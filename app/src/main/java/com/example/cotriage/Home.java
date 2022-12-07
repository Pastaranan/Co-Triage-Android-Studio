package com.example.cotriage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    Button StartTest,his,callhelp,alarm;
    ImageView Profileicon;
    Dialog myDialog;

    static public String sex_user_s;
    TextView LAST_test_txtView, Status_test_txtView, count_test_txtView;

    static public String ID_USER;
    static public String lasttest_text_user, test_txt_status, count_text_user;

    private long backPressedTime;
    private Toast backToast;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");
        Log.d("12345689", "user id is "+ID_USER);
        //Toast.makeText(Home.this, ID_USER, Toast.LENGTH_SHORT).show();

        myDialog = new Dialog(this);

        LAST_test_txtView = findViewById(R.id.date_test);
        Status_test_txtView = findViewById(R.id.test_status);
        count_test_txtView = findViewById(R.id.count_test);

        //--------------------------------
        //get_status_User();
        get_test_count_user();
        get_data_user();


        StartTest = findViewById(R.id.test);
        StartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Home.this,R.anim.fadein_half);
                StartTest.startAnimation(animation);
                Intent intent = new Intent(getApplicationContext(), Guide_1.class);
                intent.putExtra("user_id", ID_USER);
                startActivity(intent);
                finish();
            }
        });

        Profileicon = findViewById(R.id.profileicon);
        Profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Home.this,R.anim.fadein_half);
                Profileicon.startAnimation(animation);

                Intent intent = new Intent(getApplicationContext(), User_Profile.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        callhelp = findViewById(R.id.callhelp);
        callhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Home.this,R.anim.fadein_half);
                callhelp.startAnimation(animation);

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode("1669")));
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Home.this,R.anim.fadein_half);
                alarm.startAnimation(animation);

                Intent intent = new Intent(getApplicationContext(),notification.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        his = findViewById(R.id.his);
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Home.this,R.anim.fadein_half);
                his.startAnimation(animation);

                Intent intent = new Intent(getApplicationContext(),Graph.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });
    }

    private void popup_construction(){
        myDialog.setContentView(R.layout.popup_construction);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView closeq = myDialog.findViewById(R.id.closeq);
        closeq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void get_data_user(){

        Log.d("12345689", "in");

        final String url = "http://iiec2312.trueddns.com:19744/co_user/"+ID_USER;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            JSONObject object_user = response.getJSONObject("data");

                            sex_user_s = object_user.getString("User_sex");

                            Log.d("7878", sex_user_s);

                            if(sex_user_s.equals("ชาย")||sex_user_s.equals(" ชาย"))
                            {
                                Profileicon.setBackgroundResource(R.drawable.profileim_male);
                                Log.d("4222222", "aaa");
                            }
//                            age_user_s = object_user.getString("User_Lname");
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        Log.d("7878", response.toString());
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

    private void get_test_count_user(){

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
                            count_text_user = Integer.toString(jsonArray_test.length());

                            if ( jsonArray_test.length() == 0 ) {

                                LAST_test_txtView.setText("ไม่มีการทดสอบ");
                                Status_test_txtView.setText("----");
                                count_test_txtView.setText("0 ครั้ง");
                            }

                            else if ( jsonArray_test.length() >= 1 )
                            {
                                JSONObject object = jsonArray_test.getJSONObject(0);

                                String last_test = object.getString("Create_at");
                                lasttest_text_user = last_test;
                                String trim = lasttest_text_user.substring(0,10);

                                Log.d("465456", count_text_user);
                                Log.d("456465464654646", object.toString());

                                LAST_test_txtView.setText(trim);
                                count_test_txtView.setText(count_text_user+" ครั้ง");
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