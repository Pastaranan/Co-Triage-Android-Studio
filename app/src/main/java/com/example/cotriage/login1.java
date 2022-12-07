package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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

public class login1 extends AppCompatActivity {
    EditText Username,Password;
    Button login;
    Button regis,sugg;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        regis = findViewById(R.id.regis);
        sugg = findViewById(R.id.sugg);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login1.this, Register.class);
                startActivity(i);
                finish(); } });
        sugg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(login1.this, app_descrition.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(login1.this, R.anim.fadein_half);
                login.startAnimation(animation);

                if (TextUtils.isEmpty(Username.getText().toString()) || TextUtils.isEmpty(Password.getText().toString()))
                {
                    // displaying a toast message if the edit text is empty.
                    Toast.makeText(login1.this, "โปรดใส่บัญชีผู้ใช้และรหัสผ่านให้ครบ", Toast.LENGTH_SHORT).show();
                }

                //admin_1 => HOS-A
                if (Username.getText().toString().equals("1") && Password.getText().toString().equals("123"))
                {
                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                    finish();
                }

                //admin_B => HOS-B
                if (Username.getText().toString().equals("2") && Password.getText().toString().equals("123"))
                {
                    Intent intent = new Intent(getApplicationContext(), admin_b_new.class);
                    startActivity(intent);
                    finish();
                }

                //admin_Home => HOME-ISOLATION
                if (Username.getText().toString().equals("3") && Password.getText().toString().equals("123"))
                {
                    Intent intent = new Intent(getApplicationContext(), admin_home_new.class);
                    startActivity(intent);
                    finish();
                }

                if (Username.getText().toString().equals("1111") && Password.getText().toString().equals("123456"))
                {
                    Intent intent = new Intent(getApplicationContext(), Questionnaire.class);
                    startActivity(intent);
                    finish();
                }
                else
                    {
                    jsonLogin();
//                    Toast.makeText(Login.this, "Account not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void jsonLogin() {
        final String url = "http://iiec2312.trueddns.com:19744/co_users";
        RequestQueue queue = Volley.newRequestQueue(this);

        String user = Username.getText().toString();
        String password = Password.getText().toString();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("78945642134", response.toString());
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String ID = employee.getString("User_ID");
                                String user_phone_get = employee.getString("User_Phonenum");
                                String password_get = employee.getString("User_password");
                                String User_status_get = employee.getString("User_status");

                                if (user.equals(user_phone_get) && password.equals(password_get) && User_status_get.equals("Admin"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                                    intent.putExtra("user_id",ID);  //send data

                                    startActivity(intent);
                                    finish();
                                }

                                else if (user.equals(user_phone_get) && password.equals(password_get) && User_status_get.equals("User"))
                                {
//                                    ID_user_check_order = ID;
//                                    Log.d("8888", ID_user_check_order);
//
//                                    check_status_order_user();
//                                    check_status_task_user();
                                      Intent intent = new Intent(getApplicationContext(),Questionnaire.class);
                                      intent.putExtra("user_id",ID);  //send data
                                      startActivity(intent);
                                      finish();
                                }
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
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
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้งเพื่อออก", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}