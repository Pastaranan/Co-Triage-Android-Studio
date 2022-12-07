package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class result_normal_2 extends AppCompatActivity {

    Button viewmore,save;
    static public String ID_USER;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_normal_2);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        viewmore = findViewById(R.id.viewmore);
        save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backPressedTime + 2000 > System.currentTimeMillis())
                {
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    intent.putExtra("user_id",ID_USER);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อกลับหน้าหลัก", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                backPressedTime = System.currentTimeMillis();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            intent.putExtra("user_id",ID_USER);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อกลับหน้าหลัก", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}