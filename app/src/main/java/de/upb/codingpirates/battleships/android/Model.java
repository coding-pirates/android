package de.upb.codingpirates.battleships.android;

import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.upb.codingpirates.battleships.logic.util.*;

/**
 * Is the class does the communication and the business logic
 * @author Lukas Kröger
 */
public class Model {

    private int fieldWidth;
    private int fieldHeigth;

    private Collection<Client> players;
    private Collection<Shot> shots;
    private Map<Integer, Map<Integer,PlacementInfo>> shipPlacement;
    private GameState state;

    //name of the current User, which the user entered in the loginFragment
    private String userName;

    private Map<Integer, ShipType> shipsInitial;

    public Model(){

        //currently sets a hard coded state of a game for testing

        //set Field size
        fieldHeigth= 6;
        fieldWidth = 6;

        players = new ArrayList<Client>();
        players.add(new Client(0,"Roman"));
        players.add(new Client(1,"Raphael"));
        players.add(new Client(2,"Fynn"));


        shots = new ArrayList<Shot>();
        shots.add(new Shot(0,new Point2D(2,1)));
        shots.add(new Shot(1,new Point2D(1,3)));
        shots.add(new Shot(2,new Point2D(2,3)));

        //add Ships of Player 0 (Roman)
        shipPlacement = new HashMap<Integer, Map<Integer, PlacementInfo>>();
        Map <Integer,PlacementInfo> shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0,new PlacementInfo(new Point2D(1,1),Rotation.CLOCKWISE_90));
        shipsOfPlayer.put(1,new PlacementInfo(new Point2D(4,1),Rotation.COUNTERCLOCKWISE_90));
        shipsOfPlayer.put(2,new PlacementInfo(new Point2D(2,4),Rotation.NONE));
        shipPlacement.put(0,shipsOfPlayer);

        //add Ships of Player 1(Raphael)
        shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0,new PlacementInfo(new Point2D(2,3),Rotation.NONE));
        shipsOfPlayer.put(1,new PlacementInfo(new Point2D(3,1),Rotation.NONE));
        shipsOfPlayer.put(2,new PlacementInfo(new Point2D(0,0),Rotation.COUNTERCLOCKWISE_90));
        shipPlacement.put(1,shipsOfPlayer);

        //add Ships of Player 2(Fynn)
        shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0,new PlacementInfo(new Point2D(0,5),Rotation.CLOCKWISE_90));
        shipsOfPlayer.put(1,new PlacementInfo(new Point2D(2,3),Rotation.NONE));
        shipsOfPlayer.put(2,new PlacementInfo(new Point2D(1,1),Rotation.NONE));
        shipPlacement.put(2,shipsOfPlayer);

        //create initial Positions of Ships
        shipsInitial = new HashMap<Integer, ShipType>();
        //Ship 0
        Collection<Point2D> shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(0,1));
        shipPoints.add(new Point2D(1,0));
        shipsInitial.put(0,new ShipType(shipPoints));

        //Ship 1
        shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(1,0));
        shipsInitial.put(1,new ShipType(shipPoints));

        //Ship 2
        shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(1,0));
        shipPoints.add(new Point2D(2,0));
        shipsInitial.put(2,new ShipType(shipPoints));

        state = GameState.IN_PROGRESS;

    }



    public Map<Integer, PlacementInfo> getShipPlacementOfPlayer(int id){
        return shipPlacement.get(id);
    }

    public Map<Integer, ShipType> getShipTypes() {
        return shipsInitial;
    }

    public void setShipsInitial(Map<Integer, ShipType> shipsInitial) {
        this.shipsInitial = shipsInitial;
    }

    public Collection<Client> getPlayers(){
        return players;
    }

    public int getFieldWidth(){
        return fieldWidth;
    }

    public int getFieldHeigth(){
        return fieldHeigth;
    }
}
