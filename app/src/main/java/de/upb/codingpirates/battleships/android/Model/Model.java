package de.upb.codingpirates.battleships.android.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    private int clientId;

    //for Server communication
    private ClientConnector connector;
    private MutableLiveData<Boolean> serverJoinRequestSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> getServerJoinRequestSuccess(){
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
    private MutableLiveData<Collection<Game>> gamesOnServer = new MutableLiveData<>();
    public MutableLiveData<Collection<Game>> getGamesOnServer(){
        return gamesOnServer;
    }

    private MutableLiveData<Boolean> goToSpectatorWaiting = new MutableLiveData<>();;
    public MutableLiveData<Boolean> getGoToSpectatorWaiting(){
        return goToSpectatorWaiting;
    }

    //data for Spectator Waiting
    private MutableLiveData<Boolean> goToGameView = new MutableLiveData<>();
    public MutableLiveData<Boolean> getGoToGameView(){
        return goToGameView;
    }

    //data for GameView
    private Game joinedGame; //When changed go to next page

    /**
     * LivaData for Players in Game
     */
    private MutableLiveData<Collection<Client>> players = new MutableLiveData<>();
    public MutableLiveData<Collection<Client>> getPlayers(){
        return players;
    }

    /**
     * LiveData for all shots
     */
    private MutableLiveData<Collection<Shot>> shots = new MutableLiveData<>();
    public MutableLiveData<Collection<Shot>> getShots(){
        return shots;
    }

    /**
     * LiveData for going to GameEnd View
     */
    private MutableLiveData<Boolean> goToGameEnd = new MutableLiveData<>();
    public MutableLiveData<Boolean> getGoToGameEnd(){
        return goToGameEnd;
    }

    private Map<Integer, Map<Integer, PlacementInfo>> ships;
    private GameState state; //TODO make LiveData
    private Configuration gameConfig;
    private Map<Integer, Integer> pointsOfPlayers; //is no Live Data because the ViewModel only needs the points of one player


    //TODO remaining Time ??

    //data for GameEndView
    private int winner;


    public Model() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                    connector = (ClientConnector) ClientApplication.create(ClientModule.class);
            }});
        thread.start();

        gameConfig = Configuration.DEFAULT;

        //currently sets a hard coded state of a game for testing
        //set gamesOnServer:
       /* gamesOnServer = new MutableLiveData<>();
        ArrayList<Game> testList= new ArrayList<>();
        testList.add(new Game("testGame", 3, GameState.IN_PROGRESS, Configuration.DEFAULT,false));
        gamesOnServer.setValue(testList);
        //set Field size


        players = new MutableLiveData<>();
        ArrayList <Client> playersList = new ArrayList<de.upb.codingpirates.battleships.logic.Client>();
        playersList.add(new de.upb.codingpirates.battleships.logic.Client(0, "Roman"));
        playersList.add(new de.upb.codingpirates.battleships.logic.Client(1, "Raphael"));
        playersList.add(new de.upb.codingpirates.battleships.logic.Client(2, "Fynn"));
        players.setValue(playersList);

        shots = new MutableLiveData<>();
        ArrayList<Shot> shotsList = new ArrayList<Shot>();
        shotsList.add(new Shot(0, new Point2D(2, 1)));
        shotsList.add(new Shot(1, new Point2D(1, 3)));
        shotsList.add(new Shot(2, new Point2D(2, 3)));
        shots.setValue(shotsList);
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

     /*   //create initial Positions of Ships
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

        state = GameState.IN_PROGRESS; */

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

    public Collection<Shot> getShotsOfPlayer(int playerId){
        Collection<Shot> shotsOfPlayer = new ArrayList<Shot>();
        for(Shot shot : shots.getValue()){
            if(shot.getClientId() == playerId){
                shotsOfPlayer.add(shot);
            }
        }
        return shotsOfPlayer;
    }

    public Map<Integer, ShipType> getShipTypes() {
        return gameConfig.getShipTypes();
    }

    public int getFieldWidth() {
        return gameConfig.getWidth();
    }

    public int getFieldHeight() {
        return gameConfig.getHeight();
    }


    public void connectToServer(String ipAddress, String name, ClientType clientType,int port )  {
        this.clientType =clientType;
        this.clientName = name;
        this.serverIP = ipAddress;
        try {
            connector.connect(ipAddress, port);
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,"Could not connect to Server",e);
        }
    }




    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setGamesOnServer(Collection<Game> gamesOnServer) {
        this.gamesOnServer.postValue(gamesOnServer);
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
        this.players.postValue(players);
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
       Collection<Client> oldPlayers = players.getValue();
       for(Client player: oldPlayers){
           if(player.getId() == playerId){
               oldPlayers.remove(player);
               break;
           }
       }
       players.postValue(oldPlayers);
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void addShots(Collection<Shot> newShots){
        Collection<Shot> oldShots = this.shots.getValue();
        oldShots.addAll(newShots);
       this.shots.setValue(oldShots);
    }

    public void setShots(Collection<Shot> newShots){
        this.shots.setValue(newShots);
    }

    public void updatePoints(Map<Integer, Integer> newPoints){
        for(Integer key: newPoints.keySet()){
            this.pointsOfPlayers.put(key,newPoints.get(key));
        }
    }

    public void handlegameJoinSpectatorResponse(int gameId){
        //TODO init all game related variables
        for(Game game: gamesOnServer.getValue()){
            if(game.getId() == gameId){
                joinedGame = game;
                break;
            }
        }
       this.state = joinedGame.getState();
        this.goToSpectatorWaiting.postValue(true);
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


    public void goToGameView(){
        this.goToGameView.setValue(true);
    }
    public void goToGameEnd(){
        this.goToGameEnd.setValue(true);
    }

    public void setPaused(){
        this.state = GameState.PAUSED;
    }
    public void setContinued(){
        this.state = GameState.IN_PROGRESS;
    }

    public ArrayList<Map.Entry<Integer,Integer>> getSortedPoints(){
         //TODO sort
        ArrayList<Map.Entry<Integer,Integer>> sortedPoints = new ArrayList<>();
        for(Map.Entry<Integer,Integer> pointEntry: pointsOfPlayers.entrySet()){
            for(int i = sortedPoints.size()-1; i>0;i--) {
                if (pointEntry.getValue() < sortedPoints.get(i).getValue()) {
                    sortedPoints.set(i+1,sortedPoints.get(i));
                }
                else{
                    sortedPoints.set(i+1, pointEntry);
                }
            }
        }
        return sortedPoints;
    }
}
