package de.upb.codingpirates.battleships.android.Model;

import androidx.navigation.Navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.upb.codingpirates.battleships.client.network.ClientApplication;
import de.upb.codingpirates.battleships.logic.*;
import de.upb.codingpirates.battleships.network.Properties;
import de.upb.codingpirates.battleships.network.message.request.GameJoinPlayerRequest;
import de.upb.codingpirates.battleships.network.message.request.ServerJoinRequest;

/**
 * This class holds and gets the data form the Server
 * @author Lukas Kröger
 */
public class Model {

    private static final Model globalModel = new Model();

    private int fieldWidth; //TODO get width and heigth from config
    private int fieldHeigth;

    private int clientId;

    //for Server communication
    private ClientConnector connector;

    //data for LobbyView
    Collection<Game> gamesOnServer;

    //data for GameView
    private Collection<de.upb.codingpirates.battleships.logic.Client> players;
    private Collection<Shot> shots;
    private Map<Integer, Map<Integer, PlacementInfo>> ships;
    private GameState state;
    private Configuration gameConfig;
    private Map<Integer, Integer> pointsOfPlayers;
    //TODO remaining Time ??

    //data for GameEndView
    private int winner;

    private Map<Integer, ShipType> shipsInitial; //TODO delete because shipsInitial is contained in config


    public Model() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                connector = (ClientConnector) ClientApplication.create(ClientModule.class);
            }});
        thread.start();

        //currently sets a hard coded state of a game for testing

        //set Field size
        fieldHeigth = 6;
        fieldWidth = 6;

        players = new ArrayList<de.upb.codingpirates.battleships.logic.Client>();
        players.add(new de.upb.codingpirates.battleships.logic.Client(0, "Roman"));
        players.add(new de.upb.codingpirates.battleships.logic.Client(1, "Raphael"));
        players.add(new de.upb.codingpirates.battleships.logic.Client(2, "Fynn"));


        shots = new ArrayList<Shot>();
        shots.add(new Shot(0, new Point2D(2, 1)));
        shots.add(new Shot(1, new Point2D(1, 3)));
        shots.add(new Shot(2, new Point2D(2, 3)));

        //add Ships of Player 0 (Roman)
        ships = new HashMap<Integer, Map<Integer, PlacementInfo>>();
        Map<Integer, PlacementInfo> shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0, new PlacementInfo(new Point2D(1, 1), Rotation.CLOCKWISE_90));
        shipsOfPlayer.put(1, new PlacementInfo(new Point2D(4, 1), Rotation.COUNTERCLOCKWISE_90));
        shipsOfPlayer.put(2, new PlacementInfo(new Point2D(2, 4), Rotation.NONE));
        ships.put(0, shipsOfPlayer);

        //add Ships of Player 1(Raphael)
        shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0, new PlacementInfo(new Point2D(2, 3), Rotation.NONE));
        shipsOfPlayer.put(1, new PlacementInfo(new Point2D(3, 1), Rotation.NONE));
        shipsOfPlayer.put(2, new PlacementInfo(new Point2D(0, 0), Rotation.COUNTERCLOCKWISE_90));
        ships.put(1, shipsOfPlayer);

        //add Ships of Player 2(Fynn)
        shipsOfPlayer = new HashMap<Integer, PlacementInfo>();
        shipsOfPlayer.put(0, new PlacementInfo(new Point2D(0, 5), Rotation.CLOCKWISE_90));
        shipsOfPlayer.put(1, new PlacementInfo(new Point2D(2, 3), Rotation.NONE));
        shipsOfPlayer.put(2, new PlacementInfo(new Point2D(1, 1), Rotation.NONE));
        ships.put(2, shipsOfPlayer);

        //create initial Positions of Ships
        shipsInitial = new HashMap<Integer, ShipType>();
        //Ship 0
        Collection<Point2D> shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0, 0));
        shipPoints.add(new Point2D(0, 1));
        shipPoints.add(new Point2D(1, 0));
        shipsInitial.put(0, new ShipType(shipPoints));

        //Ship 1
        shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0, 0));
        shipPoints.add(new Point2D(1, 0));
        shipsInitial.put(1, new ShipType(shipPoints));

        //Ship 2
        shipPoints = new ArrayList<Point2D>();
        shipPoints.add(new Point2D(0, 0));
        shipPoints.add(new Point2D(1, 0));
        shipPoints.add(new Point2D(2, 0));
        shipsInitial.put(2, new ShipType(shipPoints));

        state = GameState.IN_PROGRESS;
    }

    public static Model getInstance(){
        return globalModel;
    }

    public void test() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    //connector = ClientApplication.create(ClientModule.class);
                    //connector.connect("192.168.178.42", Properties.PORT);
                    //connector.sendMessageToServer(new ServerJoinRequest("peter", ClientType.SPECTATOR));
                    //connector.sendMessageToServer(new GameJoinPlayerRequest(0));

                } catch (Exception e) { }
            }
        });
        thread.start();


       try {
           // connector.sendMessageToServer(new ServerJoinRequest("peter", ClientType.SPECTATOR));
            //connector.sendMessageToServer(new GameJoinPlayerRequest(0));
        }
        catch(Exception e){}
    }

    public Map<Integer, PlacementInfo> getShipPlacementOfPlayer(int id) {
        return ships.get(id);
    }

    public Map<Integer, ShipType> getShipTypes() {
        return shipsInitial;
    }

    public void setShipsInitial(Map<Integer, ShipType> shipsInitial) {
        this.shipsInitial = shipsInitial;
    }

    public Collection<de.upb.codingpirates.battleships.logic.Client> getPlayers() {
        return players;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeigth() {
        return fieldHeigth;
    }


    public void connectToServer(String ipAddress, String name, ClientType clientType) throws IOException {
        //connector.sendMessageToServer(new ServerJoinRequest(name, clientType));
        connector.connect(ipAddress, Properties.PORT);
        connector.sendMessageToServer(new ServerJoinRequest(name, clientType));
    }



    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void sendLobbyRequest() {
        //connector.sendMessageToServer(new LobbyRequest());
    }

    public void setGamesOnServer(Collection<Game> gamesOnServer) {
        this.gamesOnServer = gamesOnServer;
    }

    public void sendSpectatorGameStateRequest(){
        //connector.sendMessageToServer(new SpectatorGameStateRequest());
    }

    public void setPlayers(Collection<Client> players){
        this.players = players;
    }

    public void setShots(Collection<Shot> shots){
        this.shots = shots;
    }
    public void setShips(Map<Integer, Map<Integer, PlacementInfo>> ships) {
        this.ships = ships;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    public void setGameConfig(Configuration gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void setPointsOfPlayers(Map<Integer, Integer> pointsOfPlayers) {
        this.pointsOfPlayers = pointsOfPlayers;
    }
    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void disconnectFromServer(){
        //connector.disconnect();
    }


}
