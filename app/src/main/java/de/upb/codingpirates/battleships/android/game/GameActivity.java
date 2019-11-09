package de.upb.codingpirates.battleships.android.game;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import de.upb.codingpirates.battleships.android.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
    }

}
