package de.upb.codingpirates.battleships.android.gameEnd;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Map;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.android.R;

/**
 * This class holds all the data for the GameEndFragment.
 */
public class GameEndViewModel extends ViewModel {
    private Model model;
    private String[][] threeBestPlayers;
    private int numberOfPlayersConnected;

    /**
     * Constructor for the GameEndViewModel.
     */
    public GameEndViewModel(){
        model = Model.getInstance();
        numberOfPlayersConnected = model.getPlayers().getValue().size();
        model.setGoToGameEnd(false);
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
     * Gets the three best players from the model
     * @return threeBsetPlayers a two dimensional array including each of the tree best players with their points
     */
    public String[][] getThreeBestPlayers(){
        String[][] allPlayersAndPoints = this.getAllPlayersAndPoints();
        threeBestPlayers = new String[3][2];
        //check if there is on player
        try {
            threeBestPlayers[0][0] = allPlayersAndPoints[0][0];
            threeBestPlayers[0][1] = allPlayersAndPoints[0][1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            threeBestPlayers[0][0] = "";
            threeBestPlayers[0][1] = "";
        }
        //check if there is a second player
        try {
            threeBestPlayers[1][0] = allPlayersAndPoints[1][0];
            threeBestPlayers[1][1] = allPlayersAndPoints[1][1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            threeBestPlayers[1][0] = "";
            threeBestPlayers[1][1] = "";
        }
        //check if there even is a third player
        try {
            if (allPlayersAndPoints[2][0] == null) {
                threeBestPlayers[2][0] = "";
                threeBestPlayers[2][1] = "";
            } else {
                threeBestPlayers[2][0] = allPlayersAndPoints[2][0];
                threeBestPlayers[2][1] = allPlayersAndPoints[2][1];

            }
        }
        catch (IndexOutOfBoundsException e) {
            threeBestPlayers[2][0] = "";
            threeBestPlayers[2][1] = "";
        }
        return threeBestPlayers;
    }

    /**
     * get all players and their points in descending order
     * @return 2-dimensional array which contains every player and their points in descending order
     */
    public String[][] getAllPlayersAndPoints() {
        return model.getAllPlayerNamesAndPoints();
    }

    /**
     * returns the amount of players connected in a current game
     * @return number of players connected
     */
    public int getNumberOfPlayersConnected() {
        return numberOfPlayersConnected;
    }
}
