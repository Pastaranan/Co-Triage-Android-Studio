package com.example.cotriage;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class walk_3_min extends Activity{

    private VideoView videoView_walk;
    private Button start_button,skip_button;
    MediaPlayer musicplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_3_min);

        videoView_walk = (VideoView) findViewById(R.id.videoView_walk);
        start_button = (Button) findViewById(R.id.start_button);

        musicplay = MediaPlayer.create(this,R.raw.sound_walk_test);
        musicplay.setLooping(true);
        musicplay.seekTo(0);
        musicplay.setVolume(0.5f,0.5f);

        String URIpath1 = "android.resource://" + getPackageName() + "/" + R.raw.walktest;
        Uri uri1 = Uri.parse(URIpath1);
        videoView_walk.setVideoURI(uri1);
        videoView_walk.requestFocus();

        //รับข้อมูล
        Intent _inboundIndex = getIntent();
        String _SPO2be_2 = _inboundIndex.getStringExtra("SPO2_1");
        //Toast.makeText(getApplicationContext(),_SPO2be_2,Toast.LENGTH_SHORT).show();

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //videoView_walk.setBackground(false);
                videoView_walk.start();
                musicplay.start();
                start_button.setText("เริ่มใหม่");
            }
        });


        skip_button = findViewById(R.id.skip);
        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), com.example.cotriage.Fillout_after.class);
                musicplay.stop();

                i.putExtra("SPO2_2",_SPO2be_2);
                startActivity(i);
                finish();
            }
        });

        //กดเริ่มใหม่และหยุด -- ลอง
//        video.setOnCompletionListener (new OnCompletionListener() {
//
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//
//                mp.release();
//                this.finish();//kills current Activity
//            }
//        });


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Intent i = new Intent(walk_3_min.this,Fillout_after.class);
//                musicplay.stop();
//                startActivity(i);
//                finish();
//            }
//        },180000);
    }
}
