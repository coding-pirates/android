package de.upb.codingpirates.battleships.android.game;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class GameViewModel extends BaseObservable {
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_endScreenFragment);
    }
}
