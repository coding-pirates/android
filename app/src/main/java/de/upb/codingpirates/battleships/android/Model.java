package de.upb.codingpirates.battleships.android;

import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.upb.codingpirates.battleships.logic.util.*;

public class Model {

    private int fieldWidth;
    private int fieldHeigth;

    private Collection<Client> players;
    private Collection<Shot> shots;
    private Map<Integer, Map<Integer,PlacementInfo>> shipPlacement;
    private GameState state;



    private Map<Integer, ShipType> shipsInitial;

    public Model(){

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

    //all points of the players ships
    public Collection<Point2D> getPlayerShips(int playerID){
        Map<Integer, PlacementInfo> shipsOfPlayer = shipPlacement.get(playerID);
        Collection<Point2D> pointsOfShips = new ArrayList<Point2D>();
        Point2D rootPosition;
        for(int i: shipsOfPlayer.keySet()){
            rootPosition = shipsOfPlayer.get(i).getPosition();
            switch(shipsOfPlayer.get(i).getRotation()){
                case NONE:
                    for(Point2D point: shipsInitial.get(i).getPosition())
                    {
                        pointsOfShips.add(new Point2D(rootPosition.getX()+point.getX(),rootPosition.getY()+point.getY()));
                    }
                    pointsOfShips.add(shipsOfPlayer.get(i).getPosition());

                case CLOCKWISE_90:


            }
        }

        return pointsOfShips;
    }

    //get all Ship placements of one Player
    public Map<Integer, PlacementInfo> getShipPlacementOfPlayer(int id){
        return shipPlacement.get(id);
    }
    //get ships from Config
    public Map<Integer, ShipType> getShipTypes() {
        return shipsInitial;
    }
    //set Ships from Config
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
