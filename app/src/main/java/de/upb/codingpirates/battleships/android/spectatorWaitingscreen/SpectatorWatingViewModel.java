package de.upb.codingpirates.battleships.android.spectatorWaitingscreen;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class SpectatorWatingViewModel extends BaseObservable {
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_spectatorWaitingFragment_to_gameFragment);
    }
}
