package de.upb.codingpirates.battleships.android.model;

import androidx.lifecycle.MutableLiveData;
import de.upb.codingpirates.battleships.android.network.AndroidReader;
import de.upb.codingpirates.battleships.android.network.ClientConnectorAndroid;
import de.upb.codingpirates.battleships.android.network.ModelMessageListener;
import de.upb.codingpirates.battleships.client.ListenerHandler;
import de.upb.codingpirates.battleships.client.network.ClientApplication;
import de.upb.codingpirates.battleships.client.network.ClientModule;
import de.upb.codingpirates.battleships.logic.*;
import de.upb.codingpirates.battleships.network.message.notification.*;
import de.upb.codingpirates.battleships.network.message.request.RequestBuilder;
import de.upb.codingpirates.battleships.network.message.response.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * This class holds and gets the data form the Server
 * @author Lukas Kröger
 */
public class Model implements ModelMessageListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Model globalModel = new Model();

    private String clientName;
    private String serverIP;
    private ClientType clientType;

    private int clientId;

    //for Server communication
    private ClientConnectorAndroid connector;

    private MutableLiveData<Boolean> serverJoinRequestSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> getServerJoinRequestSuccess(){
        return serverJoinRequestSuccess;
    }
    public void setServerJoinRequestSuccess(Boolean serverJoinSuccess) {
        this.serverJoinRequestSuccess.postValue(serverJoinSuccess);
    }


    public void setConnected(Boolean connected){
        connector.sendMessageToServer(RequestBuilder.serverJoinRequest(clientName, clientType));
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
    private Game joinedGame;

    /**
     * LivaData for Players in Game
     */
    private MutableLiveData<Collection<Client>> players = new MutableLiveData<>();
    public MutableLiveData<Collection<Client>> getPlayers(){
        return players;
    }

    /**
     * LiveData for a new Round
     */
    private MutableLiveData<Boolean> newRound = new MutableLiveData<>();
    public MutableLiveData<Boolean> getNewRound(){
        return newRound;
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
    private Configuration gameConfig;
    private MutableLiveData<Map<Integer, Integer>> pointsOfPlayers = new MutableLiveData<>();
    public MutableLiveData<Map<Integer, Integer>> getPointsOfPlayers(){
        return pointsOfPlayers;
    }


    /**
     * Live Data and timer for checking if the connection took too long
     */
    private MutableLiveData<Boolean> connectionTookTooLong = new MutableLiveData<>();
    public MutableLiveData<Boolean> getConnectionTookTooLong() {
        return connectionTookTooLong;
    }
    public void setConnectionTookTooLong(Boolean value) {
        connectionTookTooLong.postValue(value);
    }

    /**
     * Consturctor for the Model.
     * Instatiates the ClientConnectorAndroid
     */
    public Model() {
        new Thread(() -> connector = ClientApplication.create(new ClientModule<>(ClientConnectorAndroid.class, AndroidReader.class))).start();
        ListenerHandler.registerListener(this);
    }

    public static Model getInstance(){
        return globalModel;
    }

    /**
     * Returns the ship placements info of one given player
     * @param id The id of the player you want to get the placement info from
     * @return Placement Info of one Player
     */
    public Map<Integer, PlacementInfo> getShipPlacementOfPlayer(int id) {
        return ships.get(id);
    }

    /**
     * Returns the shots of one given player
     * @param playerId The id of the player you want to get the Shots from
     * @return The Shots of the selected player
     */
    public Collection<Shot> getShotsOfPlayer(int playerId){
        Collection<Shot> shotsOfPlayer = new ArrayList<Shot>();
        for(Shot shot : shots.getValue()){
            if(shot.getClientId() == playerId){
                shotsOfPlayer.add(shot);
            }
        }
        return shotsOfPlayer;
    }
    public long getRoundTime(){
            return gameConfig.getRoundTime();
    }
    /**
     * Returns the ship types of the current game
     * @return Map with ship types
     */
    public Map<Integer, ShipType> getShipTypes() {
        return gameConfig.getShips();
    }

    /**
     * Returns the field width of the current game
     * @return field width
     */
    public int getFieldWidth() {
        return gameConfig.getWidth();
    }

    /**
     * Returns the field height of the current game
     * @return field height
     */
    public int getFieldHeight() {
        return gameConfig.getHeight();
    }

    /**
     * Connects to a Server
     * @param ipAddress The IP adress of the server
     * @param name  The Player name
     * @param clientType The type of the client
     * @param port The Port of the server
     */
    public void connectToServer(String ipAddress, String name, ClientType clientType,int port )  {
        this.clientType =clientType;
        this.clientName = name;
        this.serverIP = ipAddress;
        connector.connect(ipAddress, port);
    }


    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setGamesOnServer(Collection<Game> gamesOnServer) {
        this.gamesOnServer.postValue(gamesOnServer);
    }

    private Collection<Game> sortGamesOnServer(Collection<Game> gamesOnServer) {
        try {
            ArrayList<Game> sortedGames = new ArrayList<>( gamesOnServer);
            sortedGames.sort(Comparator.comparing(Game::getState).thenComparing(Game::getName));
            gamesOnServer = sortedGames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gamesOnServer;
    }

    /**
     * Sends a SpectatorGameStateRequest to the connected Server
     */
    public void sendSpectatorGameStateRequest(){
        connector.sendMessageToServer(RequestBuilder.spectatorGameStateRequest());
    }

    public void setPlayers(Collection<Client> players){
        this.players.postValue(players);
    }


    public void setShips(Map<Integer, Map<Integer, PlacementInfo>> ships) {
        this.ships = ships;
    }

    public void setGameConfig(Configuration gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void setPointsOfPlayers(Map<Integer, Integer> pointsOfPlayers) {
        this.pointsOfPlayers.setValue(pointsOfPlayers);
    }

    /**
     * Removes a player from the Points Map and form the player List
     * @param playerId
     */
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

    /**
     * Adds shots to the shots List
     * @param newShots List with shots to add
     */
    public void addShots(Collection<Shot> newShots){
        Collection<Shot> oldShots = this.shots.getValue();
        oldShots.addAll(newShots);
       this.shots.setValue(oldShots);
    }

    public void setShots(Collection<Shot> newShots){
        this.shots.setValue(newShots);
    }

    /**
     * Updates the points map
     * @param newPoints
     */
    public void updatePoints(Map<Integer, Integer> newPoints){
        Map<Integer, Integer> newPointsOfPlayers = this.pointsOfPlayers.getValue();
        if(newPointsOfPlayers == null){
            newPointsOfPlayers = newPoints;
        }
        newPointsOfPlayers.putAll(newPoints);

        this.pointsOfPlayers.setValue(newPointsOfPlayers);
    }

    /**
     * Sets the current Game with a given Id
     * @param gameId The game which should be set
     */
    public void setJoinedGameWithId(int gameId){
        for(Game game: gamesOnServer.getValue()){
            if(game.getId() == gameId){
                joinedGame = game;
                setGameConfig(game.getConfig());
                break;
            }
        }
    }

    public void setGoToSpectatorWaiting(Boolean state){
        this.goToSpectatorWaiting.setValue(state);
    }
    public void disconnectFromServer(){
        // connector.disconnect();
    }

    /**
     * Sends a LobbyRequest to the connected server
     */
    public void sendLobbyRequest() {
        connector.sendMessageToServer(RequestBuilder.lobbyRequest());
    }

    /**
     * Sends a GameJoinSpectatorRequest to the connected server
     * @param gameId The id of the game you want to join
     */
    public void sendGameJoinSpectatorRequest(int gameId){
        connector.sendMessageToServer(RequestBuilder.gameJoinSpectatorRequest(gameId));
    }

    /**
     * Sets the Boolean goToGameView to true, if it was null or false before
     */
    public void goToGameView(){
        if(this.goToGameView.getValue()==null || !this.goToGameView.getValue()) { //TODO could be unneccecary because its set to false every time the navigation  was completed
            this.goToGameView.setValue(true);
        }
        this.goToGameView.setValue(false);
    }
    public void setGoToGameView(Boolean state){
        this.goToGameView.setValue(state);
    }

    public void goToGameEnd(){
        if(this.goToGameEnd.getValue()==null || !this.goToGameEnd.getValue()) {//TODO could be unneccecary because its set to false every time the navigation  was completed
            this.goToGameEnd.setValue(true);
        }
    }

    public void setNewRound(Boolean newState){
        this.newRound.setValue(newState);
    }
   /* public void setPaused(){ //TODO paused
        this.state = GameState.PAUSED;
    }
    public void setContinued(){ //TODO continued
        this.state = GameState.IN_PROGRESS;
    } */

    /**
     * Returns the three best players
     * @return A 2-dimensional String Array with gameID and points in ordered sequence
     */
    public String[][] getThreeBestPlayers(){ //TODO replace gameID with player name
        String[][] sortedPoints = new String[3][2];
        Map<Integer,Integer> localPointsOfPlayers= pointsOfPlayers.getValue();
        for(int i =0 ; i<3 && localPointsOfPlayers.size()>0; i++) {
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

    @Override
    public void onFinishNotification(FinishNotification message, int clientId) {
        this.setPointsOfPlayers(message.getPoints());
        this.goToGameEnd();
    }

    @Override
    public void onGameInitNotification(GameInitNotification message, int clientId) {
        this.setPlayers(message.getClientList());
        this.setGameConfig(message.getConfiguration());
    }

    @Override
    public void onGameJoinSpectatorResponse(GameJoinSpectatorResponse message, int clientId) {
        this.setJoinedGameWithId(message.getGameId());
        this.setGoToSpectatorWaiting(true);
    }

    @Override
    public void onGameStartNotification(GameStartNotification message, int clientId) {
        this.sendSpectatorGameStateRequest();
    }

    @Override
    public void onLobbyResponse(LobbyResponse message, int clientId) {
        this.setGamesOnServer(message.getGames());
    }

    @Override
    public void onPointsResponse(PointsResponse message, int clientId) {
        this.setPointsOfPlayers(message.getPoints());
    }

    @Override
    public void onRoundStartNotification(RoundStartNotification message, int clientId) {
        this.setNewRound(true);
    }

    @Override
    public void onServerJoinResponse(ServerJoinResponse message, int clientId) {
        this.setClientId(message.getClientId());
        this.setServerJoinRequestSuccess(true);
    }

    @Override
    public void onSpectatorGameStateResponse(SpectatorGameStateResponse message, int clientId) {
        this.setPlayers(message.getPlayers());
        this.setShots(message.getShots());
        this.setShips(message.getShips());
        this.goToGameView();
    }

    @Override
    public void onSpectatorUpdateNotification(SpectatorUpdateNotification message, int clientId) {
        Collection<Shot> shots =  message.getHits();
        shots.addAll(message.getMissed());
        this.addShots(shots);
        this.updatePoints(message.getPoints());
    }
}
