package de.upb.codingpirates.battleships.android.game;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.logic.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    private ArrayList<Client> players; //TODO Make mutable live data so that it changes when Player leaves

    private long roundTime;

    /**
     * Contains all points of currently displayed ships
     */
    private ArrayList<Point2D> pointsOfShips; //already converted for the GridLayout of the GameView

    /**
     * returns all the positions of currently displayed ships
     * @return pointsOfShips
     */
    public ArrayList<Point2D> getPointsOfShips() {
        return pointsOfShips;
    }

    /**
     * Represents the currently selected player
     */
    private Client currentPlayer;

    private MutableLiveData<ArrayList<Point2D>> pointsOfShots = new MutableLiveData<>();
    /**
     * returns the positions of currently placed shots
     * @return pointsOfShots
     */
    public MutableLiveData<ArrayList<Point2D>> getPointsOfShots(){
        return pointsOfShots;
    }

    private MutableLiveData<Boolean> goToGameEnd = new MutableLiveData<>();

    /**
     * returns the state of goToGameEnd
     * @return goToGameEnd
     */
    public MutableLiveData<Boolean> getGoToGameEnd(){
        return goToGameEnd;
    }

    private MutableLiveData<Boolean> newRound = new MutableLiveData<>();

    /**
     * returns the value of the boolean newRound
     * @return newRound
     */
    public MutableLiveData<Boolean> getNewRound(){
        return newRound;
    }

    private MutableLiveData<Integer> pointsOfCurrentPlayer = new MutableLiveData<>();

    /**
     * returns the points of the current player
     * @return pointsOfCurrentPlayer
     */
    public MutableLiveData<Integer> getPointsOfCurrentPlayer() {
        return pointsOfCurrentPlayer;
    }

    /**
     * Constructor of GameViewModel. Binds the model and sets heigth and width of the field, as well as round time and creates observers
     */
    public GameViewModel(){
       model = Model.getInstance();
       fieldWidth = model.getFieldWidth();
       fieldHeight = model.getFieldHeight();
       roundTime = model.getRoundTime();

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

       /**
        * Observer for going to End View
        */
       final Observer<Boolean> goToGameEndObserver = new Observer<Boolean>(){
           public void onChanged(@Nullable final Boolean newGoToGameEnd) {
               if (newGoToGameEnd) {
                   goToGameEnd.setValue(newGoToGameEnd);
               }
           }
       };
       model.getGoToGameEnd().observeForever(goToGameEndObserver);

       /**
        * Observer for pointsOfPlayers
        */
       final Observer<Map<Integer,Integer>> pointsOfPlayersObserver = new Observer<Map<Integer,Integer>>(){
           public void onChanged(@Nullable final Map<Integer,Integer> newPointsOfPlayers) {
               pointsOfCurrentPlayer.setValue(newPointsOfPlayers.get(currentPlayer.getId()));
           }
       };
       model.getPointsOfPlayers().observeForever(pointsOfPlayersObserver);

        /**
         * Observer for newRound
         */
        final Observer<Boolean> newRoundObserver = new Observer<Boolean>(){
            public void onChanged(@Nullable final Boolean newNewRound) {
                newRound.setValue(newNewRound);
            }
        };
        model.getNewRound().observeForever(newRoundObserver);

    }

    /**
     * returns the currentPlayer
     * @return currentPlayer
     */
    public Client getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method sets the current player, the pointsOfShots of the player and the pointsOfCurrentPlayer. It also refreshs the Ship positions
     * @param currentPlayer The client to set
     */
    public void setCurrentPlayer(Client currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.pointsOfShots.setValue((ArrayList<Point2D>) createShotPointsForUI(model.getShotsOfPlayer(currentPlayer.getId())));
        if(model.getPointsOfPlayers().getValue()!=null) {
            this.pointsOfCurrentPlayer.setValue(model.getPointsOfPlayers().getValue().get(currentPlayer.getId()));
        }
        refreshShipPoints();
    }


    /**
     * returns all the players
     * @return players
     */
    public ArrayList<Client> getPlayers() {
        return players;
    }

    /**
     * returns the round time
     * @return roundTime
     */
    public long getRoundTime(){
        return roundTime;
    }

    /**
     * returns the width of the field
     * @return fieldWidth
     */
    public int getFieldWidth() {
        return fieldWidth;
    }

    /**
     * returns the height of the field
     * @return filedHeight
     */
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

    /**
     * Turns the points to the right coordinate system quadrant
     * @param shotsOfPlayer points of shots in the first quadrant
     * @return points of shots in the fourth quadrant
     */
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

    /**
     * calls the sendGameLeaveRequest method of the model
     */
    public void sendLeaveRequest() {
        model.sendGameLeaveRequest();
    }

}
