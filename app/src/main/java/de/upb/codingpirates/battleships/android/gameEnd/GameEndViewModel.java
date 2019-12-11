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
    String[][] threeBestPlayers;
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

    /**
     * Gets the tree best players form the model
     * @return two dimensional array including each of the tree best players with his points
     */
    public String[][] getThreeBestPlayers(){
        return model.getThreeBestPlayers();
    }
}
