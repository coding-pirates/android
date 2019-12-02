package de.upb.codingpirates.battleships.android;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import de.upb.codingpirates.battleships.android.logger.Log4jConfiguration;


public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log4jConfiguration.init();

        player = MediaPlayer.create(this, R.raw.sot_soundtrack);
        player.start(); 
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        if(player != null && !player.isPlaying())
            player.start();
        super.onResume();
    }
}
