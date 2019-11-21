package de.upb.codingpirates.battleships.android.game;

import android.view.View;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import de.upb.codingpirates.battleships.android.Model;
import de.upb.codingpirates.battleships.logic.util.*;

public class GameViewModel extends ViewModel {
    private Model model = new Model();

    private int fieldWidth = model.getFieldWidth();
    private int fieldHeight = model.getFieldHeigth();

    private Collection<Client> players = model.getPlayers();

    private ArrayList<Point2D> pointsOfShips; //already converted for our GameView

    public ArrayList<Point2D> getPointsOfShips() {
        return pointsOfShips;
    }



    private Client currentPlayer;

    public Client getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Client currentPlayer) {
        this.currentPlayer = currentPlayer;
        refreshShipPositions();
    }



    public Collection<Client> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Client> players) {
        this.players = players;
    }

    public int getFieldWidth(){
        return fieldWidth;
    }
    public int getFieldHeight(){
        return fieldHeight;
    }

    public void setFieldWidth(int fieldWidth){
        this.fieldWidth = fieldWidth;
    }
    public void setFieldHeight(int fieldHeight){
        this.fieldHeight = fieldHeight;
    }


    private void refreshShipPositions(){
       Map<Integer,PlacementInfo> placementOfPlayer = model.getShipPlacementOfPlayer(currentPlayer.getId());
       Map<Integer, ShipType> shipTypes = model.getShipTypes();

       ArrayList<Point2D> newPointsOfShips = new ArrayList<>();

       for(int shipID: placementOfPlayer.keySet()){

           ShipType currentShip = shipTypes.get(shipID);
           for(Point2D point: currentShip.getPosition()){ //TODO dont create a new point for every result
               Point2D editedPoint = new Point2D(point.getX(),point.getY());
               //rotate ship
               editedPoint = rotatePoint(editedPoint,placementOfPlayer.get(shipID).getRotation().ordinal());
               //position ship
               editedPoint = new Point2D(editedPoint.getX()+placementOfPlayer.get(shipID).getPosition().getX(), editedPoint.getY()+placementOfPlayer.get(shipID).getPosition().getY());
               //bring point in sector 4 of the coordinatesystem
               editedPoint = new Point2D(editedPoint.getX(), (editedPoint.getY()- (fieldHeight-1))*(-1));
               newPointsOfShips.add(editedPoint);
           }
           //positionate ship
           //calculate points on View
       }
       this.pointsOfShips = newPointsOfShips;
    }

    public Point2D rotatePoint(Point2D point, int rotationEnum){
       //rotation Matix
        double rotation = (4-rotationEnum)*Math.PI/2;
        int x = (int) Math.round(point.getX()*Math.cos(rotation)-point.getY()*Math.sin(rotation));
        int y = (int)Math.round(point.getX()*Math.sin(rotation)+point.getY()*Math.cos(rotation));
        return new Point2D(x,y);
    }



    public void exitGameButtonClicked(View view){
        //TODO exit Game and go Back to lobby
    }

}
