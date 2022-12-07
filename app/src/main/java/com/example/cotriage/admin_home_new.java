package com.example.cotriage;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class admin_home_new extends AppCompatActivity {
    Button data,logout;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_new);

        data = findViewById(R.id.button8);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), data_addmin_home_new.class);
                startActivity(intent);
                finish();
            }
        });

        logout = findViewById(R.id.button9);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login1.class);
                startActivity(intent);
                finish();
            }
        });
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
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อออกจากระบบ", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}