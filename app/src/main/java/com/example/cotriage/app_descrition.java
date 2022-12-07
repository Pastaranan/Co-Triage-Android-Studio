package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class app_descrition extends AppCompatActivity {
    Button backlogin;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_descrition);
        backlogin = findViewById(R.id.backlogin);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(app_descrition.this,login1.class);
                startActivity(i);
                return ;
            }
        });
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(getApplicationContext(),login1.class);
            startActivity(intent);
            finish();
    }
}