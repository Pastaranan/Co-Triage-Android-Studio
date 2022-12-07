package com.example.cotriage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Switch_Profile extends AppCompatActivity {
    Button backprofile,button_sw_1,button_sw_2,button_sw_3;
    ImageView profile;
    FloatingActionButton chang;
    LinearLayout addprogild,logout;
    Dialog myDialog;

    TextView name_user_TxVi ;

    static public String F_name_user_s, L_name_user_s, sex_user_s;

    private long backPressedTime;
    private Toast backToast;

    static public String ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_profile);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        myDialog = new Dialog(this);

        name_user_TxVi = findViewById(R.id.user_1_name);
        get_data_user();

        switch_button();

        backprofile = findViewById(R.id.backprofile);
        backprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Switch_Profile.this, User_Profile.class);
                i.putExtra("user_id",ID_USER);
                startActivity(i);
                return;
            }
        });

        addprogild = findViewById(R.id.addprogild);
        addprogild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    popup_construction();

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
    }

    private void switch_button()
    {
        button_sw_1 = findViewById(R.id.button_sw_1);
        button_sw_2 = findViewById(R.id.button_sw_2);
        button_sw_3 = findViewById(R.id.button_sw_3);
        button_sw_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_construction();
            }
        });
        button_sw_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_construction();
            }
        });
        button_sw_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_construction();
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
                            sex_user_s = object_user.getString("User_sex");

                            F_name_user_s = object_user.getString("User_Fname");
                            L_name_user_s = object_user.getString("User_Lname");

                            name_user_TxVi.setText(F_name_user_s+" "+L_name_user_s);

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
            Intent intent = new Intent(getApplicationContext(), User_Profile.class);
            intent.putExtra("user_id",ID_USER);
            startActivity(intent);
            finish();
    }
}
