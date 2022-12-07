package com.example.cotriage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Register extends AppCompatActivity {
    EditText name,surname,phonenum,password,repassword,namehospital,hospitalnum;
    static public String NAME_s ,SURNAME_s ,PHONENUM_s ,PASSWORD_s ,BRITHDAY_s,SEX_s,NAMEHOSPITAL_s,HOSPITALNUM_s;
    TextView brithday;
    int year;
    int month;
    int day;
    String sex;
    Spinner spinner;
    ArrayList<String> arrayList_Spinner;
    ArrayAdapter<String> arrayAdapter_Spinner;

    RadioGroup homisolation;
    RadioButton yes ,no;
    Button confirm,cancle;

    private long backPressedTime;
    private Toast backToast;

    public static int year_s;
    public static int month_s;
    public static int day_s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //------- text --------
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        phonenum = findViewById(R.id.phonenum);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        namehospital = findViewById(R.id.namehospital);
        hospitalnum = findViewById(R.id.hospitalnum);

        no = findViewById(R.id.no);
        yes = findViewById(R.id.yes);


        brithday = findViewById(R.id.brithday);
        final Calendar calendar = Calendar.getInstance();
        brithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month,int  dayOfMonth)
                    {
                        year_s = year;
                        month_s = month;
                        day_s = dayOfMonth;

                        String date1 = "  "+ dayOfMonth + "/" + month + "/" + year;
                        brithday.setText(date1);

                        //post

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //-------   Spinner SEX  ------
        Spinner Spinner = findViewById(R.id.spinner);
        arrayList_Spinner = new ArrayList<>();
        arrayList_Spinner.add("ชาย");
        arrayList_Spinner.add("หญิง");
        arrayList_Spinner.add("อื่นๆ");
        arrayAdapter_Spinner = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_Spinner);
        Spinner.setAdapter(arrayAdapter_Spinner);



        //-------   hide  ------
        namehospital = findViewById(R.id.namehospital);
        hospitalnum  = findViewById(R.id.hospitalnum);
        homisolation = findViewById(R.id.homisolation);
        homisolation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.no:
                        namehospital.setVisibility(View.VISIBLE);
                        hospitalnum.setVisibility(View.VISIBLE);
                        break;
                    case R.id.yes:
                        namehospital.setVisibility(View.INVISIBLE);
                        hospitalnum.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        cancle = findViewById(R.id.back);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backPressedTime + 2000 > System.currentTimeMillis())
                {
                    Intent intent = new Intent(getApplicationContext(),login1.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อยกเลิกการลงทะเบียน", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                backPressedTime = System.currentTimeMillis();
            }
        });

        //------- checkdata -----
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(surname.getText().toString()) || TextUtils.isEmpty(password.getText().toString())
                        || TextUtils.isEmpty(repassword.getText().toString()) || TextUtils.isEmpty(phonenum.getText().toString()) || TextUtils.isEmpty(brithday.getText().toString())
                        || (!no.isChecked() && !yes.isChecked()))
                {
                    Toast.makeText(Register.this, "โปรดใส่ข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }

                else{

                    NAME_s = name.getText().toString();
                    SURNAME_s = surname.getText().toString();
                    PHONENUM_s = phonenum.getText().toString();
                    PASSWORD_s = password.getText().toString();
                    NAMEHOSPITAL_s  = namehospital.getText().toString();
                    HOSPITALNUM_s = hospitalnum.getText().toString();
                    BRITHDAY_s = brithday.getText().toString();
                    SEX_s = Spinner.getSelectedItem().toString();

                    Log.d("12345", NAME_s+"\n");
                    Log.d("12345", SURNAME_s+"\n");
                    Log.d("12345", PHONENUM_s+"\n");
                    Log.d("12345", PASSWORD_s+"\n");
                    Log.d("12345", NAMEHOSPITAL_s+"\n");
                    Log.d("12345", HOSPITALNUM_s+"\n");
                    Log.d("12345", SEX_s+"\n");
                    Log.d("12345", BRITHDAY_s+"\n");

                    if (no.isChecked()){
                        if (PASSWORD_s.equals(repassword.getText().toString())){
                            post_resgiter_hospital();
                            Intent i = new Intent(Register.this, login1.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else if (yes.isChecked()){
                        if (PASSWORD_s.equals(repassword.getText().toString())){
                            post_resgiter();
                            Intent i = new Intent(Register.this, login1.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });




    }

    private void post_resgiter() {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/co_user",
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
                        Log.d("Error.Response1", "Unsuccess");
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
                params.put("User_Phonenum", PHONENUM_s);
                params.put("User_password",PASSWORD_s);
                params.put("User_Fname",NAME_s);
                params.put("User_Lname",SURNAME_s);
                params.put("User_sex",SEX_s);
                params.put("User_birth",BRITHDAY_s);
                params.put("User_Home_Isolation","ใช่");
                params.put("User_Hosname","-");
                params.put("User_HN","0");
                params.put("User_status","User");
                return params;

            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);
    }

    private void post_resgiter_hospital() {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://iiec2312.trueddns.com:19744/co_user",
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
                params.put("User_Phonenum", PHONENUM_s);
                params.put("User_password",PASSWORD_s);
                params.put("User_Fname",NAME_s);
                params.put("User_Lname",SURNAME_s);
                params.put("User_sex",SEX_s);
                params.put("User_birth",BRITHDAY_s);
                params.put("User_Home_Isolation","ไม่ใช่");
                params.put("User_Hosname",NAMEHOSPITAL_s);
                params.put("User_HN",HOSPITALNUM_s);
                params.put("User_status","User");
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
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง ยกเลิกการลงทะเบียน", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}