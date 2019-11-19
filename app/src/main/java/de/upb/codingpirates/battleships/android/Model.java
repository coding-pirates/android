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
    private Collection<Client> players;
    private Collection<Shot> shots;
    private Map<Integer, Map<Integer,PlacementInfo>> shipPlacement;
    private GameState state;

    Map<Integer, ShipType> shipsInitial;

    public Model(){
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
        shipsOfPlayer.clear();
        shipsOfPlayer.put(0,new PlacementInfo(new Point2D(2,3),Rotation.NONE));
        shipsOfPlayer.put(1,new PlacementInfo(new Point2D(3,1),Rotation.NONE));
        shipsOfPlayer.put(2,new PlacementInfo(new Point2D(0,0),Rotation.COUNTERCLOCKWISE_90));
        shipPlacement.put(1,shipsOfPlayer);

        //add Ships of Player 2(Fynn)
        shipsOfPlayer.clear();
        shipsOfPlayer.put(0,new PlacementInfo(new Point2D(0,5),Rotation.CLOCKWISE_90));
        shipsOfPlayer.put(1,new PlacementInfo(new Point2D(3,1),Rotation.NONE));
        shipsOfPlayer.put(2,new PlacementInfo(new Point2D(1,1),Rotation.NONE));
        shipPlacement.put(2,shipsOfPlayer);

        //create initial Positions of Ships
        //Ship 0
        Collection<Point2D> shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(0,1));
        shipPoints.add(new Point2D(1,0));
        shipsInitial.put(0,new ShipType(shipPoints));

        //Ship 1
        shipPoints.clear();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(1,0));
        shipsInitial.put(1,new ShipType(shipPoints));

        //Ship 2
        shipPoints.clear();
        shipPoints.add(new Point2D(0,0));
        shipPoints.add(new Point2D(1,0));
        shipPoints.add(new Point2D(2,0));
        shipsInitial.put(2,new ShipType(shipPoints));

        state = GameState.IN_PROGRESS;

    }

    //all points of the players ships
    public Collection<Point2D> getPlayerShips(Client player){
        Map<Integer, PlacementInfo> shipsOfPlayer = shipPlacement.get(player.getId());
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
}
