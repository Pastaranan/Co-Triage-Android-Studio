package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Guide_2 extends AppCompatActivity {
    Button yespage2, backhome;

    private long backPressedTime;
    private Toast backToast;

    static public String ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_2);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        yespage2 = findViewById(R.id.yespage2);
        yespage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Guide_2.this,R.anim.fadein_half);
                yespage2.startAnimation(animation);
                Intent intent = new Intent(getApplicationContext(),Fillout_before.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
            }
        });
        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Guide_2.this,R.anim.fadein_half);
                backhome.startAnimation(animation);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("user_id",ID_USER);
                startActivity(intent);
                finish();
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