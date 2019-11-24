package de.upb.codingpirates.battleships.android.gameEnd;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.R;

public class GameEndViewModel extends BaseObservable {
    public void nextButtonClicked(View view){
        //do something
        Navigation.findNavController(view).navigate(R.id.action_gameEndFragment_to_lobbyFragment);
    }

    /**
     * exits the game and returns to the LobbyView, when the button is clicked
     * @param view
     */
    public void exitGameEndButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_gameEndFragment_to_exitGameFragment);
    }
}
