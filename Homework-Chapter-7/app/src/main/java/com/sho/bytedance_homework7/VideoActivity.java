package com.sho.bytedance_homework7;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VideoActivity";

    private VideoView videoView;
    private SeekBar seekBar;
    private Button buttonPlay;
    private Button buttonResume;
    private TextView textViewTime;
    private TextView textViewCurrentPosition;
    private TextView textViewStatus;

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            if (videoView.isPlaying()) {
                int current = videoView.getCurrentPosition();
                seekBar.setProgress(current);
                textViewCurrentPosition.setText(time(videoView.getCurrentPosition()));
            }
            handler.postDelayed(runnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //final Uri uri = Uri.parse("https://www.bilibili.com/video/BV1hN411o7Lm?share_source=copy_web");
        videoView = (VideoView) this.findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.big_buck_bunny));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                textViewTime.setText(time(videoView.getDuration()));
                textViewStatus.setText("ready to play");
                buttonPlay.setEnabled(true);

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                play();
                Toast.makeText(VideoActivity.this, "播放出错", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewStatus.setText("loading");

        textViewTime = (TextView) findViewById(R.id.textViewTime);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        textViewCurrentPosition = (TextView) findViewById(R.id.textViewCurrentPosition);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonResume = (Button) findViewById(R.id.buttonResume);


        buttonPlay.setEnabled(false);

        buttonPlay.setOnClickListener(this);
        buttonResume.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlay:
                play();
                break;
            case R.id.buttonResume:
                resume();
                break;
            default:
                break;
        }
    }

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            if (videoView.isPlaying()) {
                videoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    protected void play() {

        if (buttonPlay.getText().equals("play")) {
            buttonPlay.setText("stop");
            handler.postDelayed(runnable, 0);
            videoView.start();
            seekBar.setMax(videoView.getDuration());
        } else

        {
            buttonPlay.setText("play");
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        }

    }

    protected void resume(){
        Log.d(TAG, "resume: ");
        videoView.resume();
    }

    protected String time(long millionSeconds) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

}
