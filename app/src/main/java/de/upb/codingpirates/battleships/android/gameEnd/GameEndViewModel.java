package de.upb.codingpirates.battleships.android.gameEnd;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Map;

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.android.game.GameViewModel;
import de.upb.codingpirates.battleships.logic.Client;

public class GameEndViewModel extends ViewModel {
    Model model;
    Object[] threeBestPlayers;
    public GameEndViewModel(){
        model = Model.getInstance();
    }
    /**
     * exits the game and returns to the LobbyView, when the button is clicked
     * @param view
     */
    public void exitGameEndButtonClicked(View view) {
        model.sendLobbyRequest();
        Navigation.findNavController(view).navigate(R.id.action_gameEndFragment_to_lobbyFragment);
    }

    public Object[] getSortedPlayers(){
        threeBestPlayers = model.getThreeBestPlayers();
        return threeBestPlayers;
    }

}
