package de.upb.codingpirates.battleships.android.endscreen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import de.upb.codingpirates.battleships.android.R;

public class EndScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameend_activity);
    }

}
