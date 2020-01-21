package de.upb.codingpirates.battleships.android;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    ArrayList<Integer> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Create a playlist with all the songs from the raw folder in the order the songs are added
        playlist = new ArrayList<>();
        playlist.add(R.raw.sea_of_thieves_theme_song);
        playlist.add(R.raw.becalmed_concertina_hurdygurdy);
        playlist.add(R.raw.maiden_voyage);
        playlist.add(R.raw.grogg_mayler_concertina_hurdygurdy);
        playlist.add(R.raw.buson_bill_concertina_hurdygurdy);
        playlist.add(R.raw.rise_of_the_valkyries_concertina);

        player = MediaPlayer.create(this, playlist.get(0));
        addCompletionListener(player, 0);
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

    /**
     * This method creates a new mediaPlayer with the next song in the playlist. After creating the new mediaPlayer mp is released
     * @param mp mediaPlayer that the onCompletionListener is being added to
     * @param index index that is played from the playlist by th new mediaPlayer
     */
    public void addCompletionListener(MediaPlayer mp, Integer index) {
        //to access the index from inside the lambda statement
        AtomicInteger _index = new AtomicInteger(index);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (_index.get() == playlist.size()) {
                    //for reloading playlist
                    _index.set(0);
                }
                //create new MediaPlayer with the next song
                if (playlist.size()>1) {
                    MediaPlayer mp2 = MediaPlayer.create(MainActivity.this, playlist.get(_index.get() + 1));
                    mp2.start();
                    //set the variable player to the new created MediaPlayer mp2 so the onPause and onResume work properly
                    player = mp2;
                    mp.release();
                    addCompletionListener(mp2, _index.get() + 1);
                }
                else {
                    //reset the player and start the first song again if the playlist only contains one song
                    player.reset();
                    player = MediaPlayer.create(MainActivity.this, playlist.get(0));
                    addCompletionListener(player, 0);
                    player.start();
                }
            }
        });

    }
}

