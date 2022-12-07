package com.example.cotriage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Walk_test_start_re extends AppCompatActivity {
    Dialog myDialog;

    private VideoView videoView_walk;
    private Button start_stop_button,skip_button,restert;
    MediaPlayer musicplay;

    static public String ID_USER,_SPO2be ,Check_loop;

    public static int min_get, sec_get;

    private long backPressedTime;
    private Toast backToast;

    static public boolean isPlaying = false;

    static public boolean time_ZERO_loop = true;
    static public boolean blink_VDO_loop = true;

    //---------------------- TIME ----------------------
    private static final long START_TIME_IN_MILLIS = 180000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning = false;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    //------------------------------------------------- />

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_test_start_re);

        myDialog = new Dialog(this);

        //get id----
        Intent _intent = getIntent();
        ID_USER = _intent.getStringExtra("user_id");
        //รับข้อมูล
        Intent _inboundIndex = getIntent();
        _SPO2be = _inboundIndex.getStringExtra("SPO2");

        //รับ tread
        Intent tread = getIntent();
        Check_loop = tread.getStringExtra("loop");
        if(Check_loop.equals("loop"))
        {
            time_ZERO_loop=true;
        }


        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        videoView_walk = (VideoView) findViewById(R.id.videoView);
        String URIpath1 = "android.resource://" + getPackageName() + "/" + R.raw.walktest;
        Uri uri1 = Uri.parse(URIpath1);
        videoView_walk.setVideoURI(uri1);
        videoView_walk.requestFocus();

//        //--------------------------  Thread start VDO __MEW --------------------------------//
//        Thread thread_a = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (blink_VDO_loop)
//                {
//                    try {
//                        videoView_walk.start();
//                        blink_stop_VOD();
//                        Thread.sleep(500);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread_a.start();
        musicplay = MediaPlayer.create(this,R.raw.sound_walk_test);
        musicplay.setLooping(true);
        musicplay.seekTo(0);
        musicplay.setVolume(0.5f,0.5f);
        musicplay.start();
        musicplay.pause();


        restert = findViewById(R.id.restsrt);
        restert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowPopup_Re_start();
            }
        });


        mButtonStartPause  = (Button) findViewById(R.id.start_stop);
        mButtonStartPause .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning)
                {
                    Log.d("11111","puase");
                    pauseTimer();
                    musicplay.pause();
                    videoView_walk.pause();
                    mButtonStartPause.setText("เริ่ม");
                    mButtonStartPause.setBackgroundTintList(mButtonStartPause.getResources().getColorStateList(R.color.green_old));
                }
                else
                {
                    Log.d("11111","Start");
                    startTimer();
                    musicplay.start();
                    videoView_walk.setVisibility(View.VISIBLE);
                    videoView_walk.start();
                    mButtonStartPause.setText("หยุด");
                    mButtonStartPause.setBackgroundTintList(mButtonStartPause.getResources().getColorStateList(R.color.gray));

                }
//                mTimerRunning = !mTimerRunning;
            }
        });

        updateCountDownText();

        //--------------------------  Thread  get time ZERO --------------------------------//
        Thread thread_b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (time_ZERO_loop)
                {
                    try {
                        get_time_zero();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_b.start();

    }

    private void blink_stop_VOD() {
        new CountDownTimer(500, 500) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
            }

            public void onFinish() {
                blink_VDO_loop = false;
            }
        }.start();
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
//                mTimerRunning = false;
            }
        }.start();

        mTimerRunning = true;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        min_get = minutes;
        sec_get = seconds;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        Log.d("12354648",Integer.toString(minutes)+"\n");
        Log.d("12354648",Integer.toString(seconds)+"\n");

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void get_time_zero() {
        if(min_get==0&&sec_get==0)
        {
            musicplay.stop();
            videoView_walk.stopPlayback();
            time_ZERO_loop = false;

            Intent intent = new Intent(getApplicationContext(),Fillout_after.class);
            intent.putExtra("SPO2",_SPO2be);
            intent.putExtra("user_id",ID_USER);
            startActivity(intent);
            finish();
        }
    }



    public void ShowPopup_Re_start ()
    {
        myDialog.setContentView(R.layout.popup_replay);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        musicplay.stop();
        videoView_walk.stopPlayback();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Walk_test_start_re.this, Home.class);
                i.putExtra("user_id",ID_USER);
                startActivity(i);
                finish();

                TextView close = (TextView) myDialog.findViewById(R.id.closeq);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Walk_test_start_re.this, Home.class);
                        i.putExtra("user_id",ID_USER);
                        startActivity(i);
                        myDialog.dismiss();
                        return ;
                    }
                });
            }
        },2500);
        myDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            musicplay.stop();
            videoView_walk.stopPlayback();

            Intent intent = new Intent(getApplicationContext(),Home.class);
            intent.putExtra("user_id",ID_USER);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "กดอีกครั้ง เพื่อยกเลิกการทดสอบ", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}