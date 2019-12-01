package de.upb.codingpirates.battleships.android.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import de.upb.codingpirates.battleships.client.network.ClientApplication;
import de.upb.codingpirates.battleships.logic.*;
import de.upb.codingpirates.battleships.network.Properties;
import de.upb.codingpirates.battleships.network.message.request.GameJoinPlayerRequest;
import de.upb.codingpirates.battleships.network.message.request.GameJoinSpectatorRequest;
import de.upb.codingpirates.battleships.network.message.request.LobbyRequest;
import de.upb.codingpirates.battleships.network.message.request.ServerJoinRequest;
import de.upb.codingpirates.battleships.network.message.request.SpectatorGameStateRequest;

/**
 * This class holds and gets the data form the Server
 * @author Lukas Kröger
 */
public class Model {
    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());
    private static final Model globalModel = new Model();

    private String clientName;
    private String serverIP;
    private ClientType clientType;

    private int fieldWidth; //TODO delete because its conained in config
    private int fieldHeigth;

    private int clientId;

    //for Server communication
    private ClientConnector connector;
    private MutableLiveData<Boolean> serverJoinRequestSuccess;

    public MutableLiveData<Boolean> getServerJoinRequestSuccess(){
        if(serverJoinRequestSuccess == null){
            serverJoinRequestSuccess = new MutableLiveData<Boolean>();
        }
        return serverJoinRequestSuccess;
    }
    public void setServerJoinRequestSuccess(Boolean serverJoinSuccess) {
        this.serverJoinRequestSuccess.postValue(serverJoinSuccess);
    }


    public void setConnected(Boolean connected){
        try {
            connector.sendMessageToServer(new ServerJoinRequest(clientName, clientType));
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not send ServerJoinRequest to Server",e);
        }

    }

    //data for LobbyView
    Collection<Game> gamesOnServer;

    //data for GameView
    private Game joinedGame; //When changed go to next page
    private Collection<de.upb.codingpirates.battleships.logic.Client> players;
    private Collection<Shot> shots;
    private Collection<Shot> hits;
    private Collection<Shot> missed;
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


    public void connectToServer(String ipAddress, String name, ClientType clientType)  {
        this.clientType =clientType;
        this.clientName = name;
        try {
            connector.connect(ipAddress, Properties.PORT);
            //connector.sendMessageToServer(new ServerJoinRequest(name, clientType));
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not connect to Server",e);
        }
    }




    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setGamesOnServer(Collection<Game> gamesOnServer) {
        this.gamesOnServer = gamesOnServer;
    }

    public void sendSpectatorGameStateRequest(){
        try {
            connector.sendMessageToServer(new SpectatorGameStateRequest());
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not send SpectatorGameStateRequest to Server",e);
        }
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

    public void removePlayer(int playerId){
       this.pointsOfPlayers.remove(playerId);
       for(Client player: players){
           if(player.getId() == playerId){
               players.remove(player);
               break;
           }
       }
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void addHits(Collection<Shot> hits){
        this.hits.addAll(hits);
    }

    public void addMissed(Collection<Shot> missed){
        this.missed.addAll(missed);
    }

    public void updatePoints(Map<Integer, Integer> newPoints){
        for(Integer key: newPoints.keySet()){
            this.pointsOfPlayers.put(key,newPoints.get(key));
        }
    }

    public void handlegameJoinSpectatorResponse(int gameId){
        //TODO init all game related variables
        for(Game game: gamesOnServer){
            if(game.getId() == gameId){
                joinedGame = game;
                break;
            }
        }
       this.state = joinedGame.getState();
    }
    public void disconnectFromServer(){
        //        //connector.disconnect();
    }

    public void sendLobbyRequest() {
        try {
            connector.sendMessageToServer(new LobbyRequest());
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not send LobbyRequest to Server",e);
        }
    }

    public void sendGameJoinSpectatorRequest(int gameId){
        try {
            connector.sendMessageToServer(new GameJoinSpectatorRequest(gameId));
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not send GameJoinSpectatorRequest to Server",e);
        }
    }


    public void setGameStart(){
        //TODO change view when game starts
    }

    public void setPaused(){
        this.state = GameState.PAUSED;
    }
    public void setContinued(){
        this.state = GameState.IN_PROGRESS;
    }

}
