package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class hintb extends AppCompatActivity {
    Button closehintb;
    static public String ID_USER,_SPO2be;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hintb);

        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        //รับข้อมูล
        Intent _inboundIndex = getIntent();
        _SPO2be = _inboundIndex.getStringExtra("SPO2");

        closehintb = findViewById(R.id.closehintb);
        closehintb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Fillout_after.class);
                intent.putExtra("SPO2",_SPO2be);
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

        Intent intent = new Intent(hintb.this, Fillout_before.class);
        intent.putExtra("SPO2",_SPO2be);
        intent.putExtra("user_id",ID_USER);
        startActivity(intent);
        finish();
    }
}