package de.upb.codingpirates.battleships.android.gameEnd;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.android.R;

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

    public Object[] getSortedPlayers(){
        threeBestPlayers = model.getThreeBestPlayers();
        return threeBestPlayers;
    }

    public String[][] getThreeBestPlayers(){
        return model.getThreeBestPlayers();
    }
}
