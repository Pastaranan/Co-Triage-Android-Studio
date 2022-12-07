package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class result_normal_1 extends AppCompatActivity {

    static public String ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_normal_1);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(result_normal_1.this, result_normal_2.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    public void onBackPressed() {

    }
}