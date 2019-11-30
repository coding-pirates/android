package de.upb.codingpirates.battleships.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        player = MediaPlayer.create(this, R.raw.sot_soundtrack);
        player.start(); //TODO Musik muss beim verlassen der App stoppen und danach an gleicher Stelle fortgesetzt werden
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
}
