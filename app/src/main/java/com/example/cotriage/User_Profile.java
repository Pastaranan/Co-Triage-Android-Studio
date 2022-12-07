package com.example.cotriage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class User_Profile extends AppCompatActivity {
    Button backhome;
    LinearLayout add_profile,switch_profile,logout;
    Dialog myDialog;

    static public String ID_USER;

    TextView name_user_TxVi, sex_age_birth_TxVi, phonenum_user_TxVi, Hos_user_TxVi, HN_user_TxVi ;

    static public String F_name_user_s, L_name_user_s, sex_user_s, age_user_s, birth_user_s, phonenum_user_s, Hos_name_user_s,  HN_user_s;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        myDialog = new Dialog(this);

        name_user_TxVi = findViewById(R.id.name_user);
        sex_age_birth_TxVi = findViewById(R.id.sex_age_birth);
        phonenum_user_TxVi = findViewById(R.id.user_phone);
        Hos_user_TxVi = findViewById(R.id.hos_name);
        HN_user_TxVi = findViewById(R.id.HN_user);

        get_data_user();

        add_profile = findViewById(R.id.add_profile);
        add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_construction();
            }
        });

        switch_profile = findViewById(R.id.switch_profile);
        switch_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Switch_Profile.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });

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

                            F_name_user_s = object_user.getString("User_Fname");
                            L_name_user_s = object_user.getString("User_Lname");

                            sex_user_s = object_user.getString("User_sex");
                            birth_user_s = object_user.getString("User_birth");
                            phonenum_user_s = object_user.getString("User_Phonenum");
                            Hos_name_user_s = object_user.getString("User_Hosname");
                            HN_user_s = object_user.getString("User_HN");


                            name_user_TxVi.setText(F_name_user_s+" "+L_name_user_s);
                            sex_age_birth_TxVi.setText(sex_user_s+" | "+birth_user_s);
                            phonenum_user_TxVi.setText(phonenum_user_s);

                            if(Hos_name_user_s.equals("-")||HN_user_s.equals("0"))
                            {
                                Hos_user_TxVi.setText("Home Isolation");
                                HN_user_TxVi.setText("ไม่มีหมายเลขผู้ป่วยนอก");
                            }
                            else
                            {
                                Hos_user_TxVi.setText("Community Isolation");
                                HN_user_TxVi.setText(HN_user_s);
                            }

//                            age_user_s = object_user.getString("User_Lname");
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        intent.putExtra("user_id",ID_USER);
        startActivity(intent);
        finish();
    }
}