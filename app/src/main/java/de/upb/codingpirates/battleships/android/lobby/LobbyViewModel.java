package de.upb.codingpirates.battleships.android.lobby;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class LobbyViewModel extends BaseObservable {
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_spectatorWaitingFragment);
    }

    /**
     * Change the Fragment when the Settingsbutton is clicked
     * @param view
     */
    public void settingsButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_lobbyFragment_to_settingsFragment);
    }

}
