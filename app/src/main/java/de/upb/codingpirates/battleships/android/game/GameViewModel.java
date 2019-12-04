package de.upb.codingpirates.battleships.android.game;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import de.upb.codingpirates.battleships.android.Model.Model;
import de.upb.codingpirates.battleships.android.R;
import de.upb.codingpirates.battleships.logic.*;

/**
 * This class holds all the data for the GameFragment.
 *
 * @author Lukas Kröger
 */
public class GameViewModel extends ViewModel {

    /**
     * Is the Model which manages the GameLogic and Server communication
     */
    private Model model;

    /**
     * The game field width specified in the configuration
     */
    private int fieldWidth;

    /**
     * The game field heigth specified in the configuration
     */
    private int fieldHeight;

    /**
     * Contains all players of the current game
     */
    private ArrayList<Client> players; //TODO Mutable live data so that it changes when Player leaves

    /**
     * Contains all points of currently displayed ships
     */
    private ArrayList<Point2D> pointsOfShips; //already converted for the GridLayout of the GameView

    public ArrayList<Point2D> getPointsOfShips() {
        return pointsOfShips;
    }

    /**
     * Represents the currently selected player
     */
    private Client currentPlayer;

    private Collection<Shot> missed;
    private MutableLiveData<ArrayList<Point2D>> pointsOfShots = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Point2D>> getPointsOfShots(){
        return pointsOfShots;
    }

   public GameViewModel(){
       model = Model.getInstance();
       fieldWidth = model.getFieldWidth();
       fieldHeight = model.getFieldHeight();

       /**
        * Observer for Players in Game
        */
       final Observer<Collection<Client>> playersObserver = new Observer<Collection<Client>>(){
           public void onChanged(@Nullable final Collection<Client> newPlayers) {
               ArrayList<Client> newPlayersList = (ArrayList<Client>) newPlayers;
               if(currentPlayer == null){
                   currentPlayer = newPlayersList.get(0);
               }
               players = newPlayersList;
           }
       };
       model.getPlayers().observeForever(playersObserver);

       /**
        * Observer for shots
        */
       final Observer<Collection<Shot>> shotsObserver = new Observer<Collection<Shot>>(){
           public void onChanged(@Nullable final Collection<Shot> newShots) {
               if(currentPlayer!=null) {
                   pointsOfShots.setValue((ArrayList<Point2D>) createShotPointsForUI(model.getShotsOfPlayer(currentPlayer.getId())));
               }
           }
       };
        model.getShots().observeForever(shotsObserver);


    }

    public Client getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Client currentPlayer) {
        this.currentPlayer = currentPlayer;
        refreshShipPoints();
    }


    public ArrayList<Client> getPlayers() {
        return players;
    }


    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    /**
     * Calculates/Converts the points of the ships for the game field
     */
    private void refreshShipPoints() {
        Map<Integer, PlacementInfo> placementOfPlayer = model.getShipPlacementOfPlayer(currentPlayer.getId());
        Map<Integer, ShipType> shipTypes = model.getShipTypes();
        if(!shipTypes.isEmpty()) {
            ArrayList<Point2D> newPointsOfShips = new ArrayList<>();

            for (int shipID : placementOfPlayer.keySet()) {

                ShipType currentShip = shipTypes.get(shipID);
                for (Point2D point : currentShip.getPositions()) {
                    Point2D editedPoint = new Point2D(point.getX(), point.getY());
                    //rotate ship
                    editedPoint = rotatePoint(editedPoint, placementOfPlayer.get(shipID).getRotation().ordinal());
                    //position ship
                    editedPoint = new Point2D(editedPoint.getX() + placementOfPlayer.get(shipID).getPosition().getX(), editedPoint.getY() + placementOfPlayer.get(shipID).getPosition().getY());
                    //bring point in sector 4 of the coordinatesystem
                    editedPoint = new Point2D(editedPoint.getX(), (editedPoint.getY() - (fieldHeight - 1)) * (-1));
                    newPointsOfShips.add(editedPoint);
                }
            }
            this.pointsOfShips= newPointsOfShips;
        }
    }

    private Collection<Point2D> createShotPointsForUI(Collection<Shot> shotsOfPlayer){
        Collection<Point2D> createdPoints = new ArrayList<>();
        for(Shot shot: shotsOfPlayer){
            Point2D newPointOfShot = new Point2D(shot.getTargetField().getX(), (shot.getTargetField().getY()-(fieldHeight-1))*(-1));
            createdPoints.add(newPointOfShot);
        }
        return createdPoints;
    }

    /**
     * rotates a point by the given rotation Enum
     *
     * @param point        contains the point that should be rotated
     * @param rotationEnum Contains the rotation Enum
     * @return The rotated point
     */
    public Point2D rotatePoint(Point2D point, int rotationEnum) {
        //rotation Matix
        double rotation = (4 - rotationEnum) * Math.PI / 2;
        int x = (int) Math.round(point.getX() * Math.cos(rotation) - point.getY() * Math.sin(rotation));
        int y = (int) Math.round(point.getX() * Math.sin(rotation) + point.getY() * Math.cos(rotation));
        return new Point2D(x, y);
    }

    public void nextButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_gameEndFragment);
    }

    /**
     * Navigates to the game exit dialog, in which the user can select, if he really wants to exit.
     * @param view
     */
    public void exitGameButtonClicked(View view){
        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_exitGameFragment);
    }
}
