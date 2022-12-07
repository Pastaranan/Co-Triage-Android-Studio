package com.example.cotriage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Guide_pre_walk extends AppCompatActivity {
    Button start1;
    static public String ID_USER;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_pre);
        start1 = findViewById(R.id.start1);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");

        //รับข้อมูล
        Intent _inboundIndex = getIntent();
        String _SPO2be = _inboundIndex.getStringExtra("SPO2");
        //Toast.makeText(getApplicationContext(),_SPO2be_1,Toast.LENGTH_SHORT).show();

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(Guide_pre_walk.this,R.anim.fadein_half);
                start1.startAnimation(animation);

                String tread = "loop";
                Intent i = new Intent(getApplicationContext(), Walk_test_start_re.class);

                //ส่งข้อมูล
                //EditText _SPO2be_1 = (EditText)findViewById(R.id.SPO2);
                i.putExtra("SPO2",_SPO2be);
                i.putExtra("user_id",ID_USER);
                i.putExtra("loop",tread);
                startActivity(i);
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