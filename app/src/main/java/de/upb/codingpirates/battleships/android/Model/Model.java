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
    private MutableLiveData<Map<Integer, Integer>> pointsOfPlayers = new MutableLiveData<>();
    public MutableLiveData<Map<Integer, Integer>> getPointsOfPlayers(){
        return pointsOfPlayers;
    }


    //TODO remaining Time ??

    //data for GameEndView
    private int winner;


    public Model() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                    connector = (ClientConnector) ClientApplication.create(ClientModule.class);
            }});
        thread.start();
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
        this.pointsOfPlayers.setValue(pointsOfPlayers);
    }


    public void removePlayer(int playerId){
        Map<Integer, Integer> newPointsOfPlayer = this.pointsOfPlayers.getValue();
        newPointsOfPlayer.remove(playerId);
       this.pointsOfPlayers.setValue(newPointsOfPlayer);
       Collection<Client> newPlayers = players.getValue();
       for(Client player: newPlayers){
           if(player.getId() == playerId){
               newPlayers.remove(player);
               break;
           }
       }
       players.postValue(newPlayers);
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
        Map<Integer, Integer> newPointsOfPlayers = this.pointsOfPlayers.getValue();
        if(newPointsOfPlayers == null){
            newPointsOfPlayers = newPoints;
        }
        for(Integer key: newPoints.keySet()){
            newPointsOfPlayers.put(key,newPoints.get(key));
        }
        this.pointsOfPlayers.setValue(newPointsOfPlayers);
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
        if(this.goToSpectatorWaiting.getValue()==null||!this.goToSpectatorWaiting.getValue()) {
            this.goToSpectatorWaiting.postValue(true);
        }
    }
    public void setGoToSpectatorWaiting(Boolean state){
        this.goToSpectatorWaiting.setValue(state);
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
        if(this.goToGameView.getValue()==null || !this.goToGameView.getValue()) {
            this.goToGameView.setValue(true);
        }
        this.goToGameView.setValue(false);
    }
    public void goToGameEnd(){
        if(this.goToGameEnd.getValue()==null || !this.goToGameEnd.getValue()) {
            this.goToGameEnd.setValue(true);
        }
    }

    public void setPaused(){
        this.state = GameState.PAUSED;
    }
    public void setContinued(){
        this.state = GameState.IN_PROGRESS;
    }

    public Object[] getThreeBestPlayers(){
         //TODO sort
        String[][] sortedPoints = new String[3][2];
        Map<Integer,Integer> localPointsOfPlayers= pointsOfPlayers.getValue();
        for(int i =0 ; i<3 && i<pointsOfPlayers.getValue().size(); i++) {
            Map.Entry<Integer,Integer> currentBest = null;
            for (Map.Entry<Integer, Integer> pointEntry : localPointsOfPlayers.entrySet()) {
                if(currentBest == null || pointEntry.getValue().compareTo(currentBest.getValue())>0){
                    currentBest = pointEntry;
                }
            }
            for(Client player: players.getValue()){
                if(currentBest.getKey() == player.getId()){
                    sortedPoints[i][0]=player.getName();
                    break;
                }
            }
            sortedPoints[i][1]= Integer.toString(currentBest.getValue());
            localPointsOfPlayers.remove(currentBest.getKey());
        }
        return sortedPoints;
    }
}
