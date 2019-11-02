package de.upb.codingpirates.battleships.android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import de.upb.codingpirates.battleships.network.message.ServerJoinRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
