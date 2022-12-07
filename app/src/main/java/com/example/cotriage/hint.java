package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class hint extends AppCompatActivity {
    Button closehint;
    static public String ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        closehint = findViewById(R.id.closehint);
        closehint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Fillout_before.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent _intent = getIntent();
        String ID_USER = _intent.getStringExtra("user_id");

        Intent intent = new Intent(hint.this, Fillout_before.class);
        intent.putExtra("user_id",ID_USER);
        startActivity(intent);
        finish();
    }

}