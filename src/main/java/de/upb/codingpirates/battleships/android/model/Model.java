package de.upb.codingpirates.battleships.android.model;

import android.os.Build;
import androidx.lifecycle.MutableLiveData;
import com.google.common.collect.Lists;
import de.upb.codingpirates.battleships.android.network.AndroidReader;
import de.upb.codingpirates.battleships.android.network.ModelMessageListener;
import de.upb.codingpirates.battleships.client.ListenerHandler;
import de.upb.codingpirates.battleships.client.network.ClientApplication;
import de.upb.codingpirates.battleships.client.network.ClientConnector;
import de.upb.codingpirates.battleships.logic.*;
import de.upb.codingpirates.battleships.network.message.notification.*;
import de.upb.codingpirates.battleships.network.message.request.RequestBuilder;
import de.upb.codingpirates.battleships.network.message.response.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

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
    private ClientConnector connector;

    private MutableLiveData<Boolean> serverJoinRequestSuccess = new MutableLiveData<>();

    /**
     * returns whether ServerJoinRequest was successful
     * @return serverJoinRequestSuccess
     */
    public MutableLiveData<Boolean> getServerJoinRequestSuccess(){
        return serverJoinRequestSuccess;
    }

    /**
     * sets the success of the ServerJoinRequest
     * @param serverJoinSuccess
     */
    public void setServerJoinRequestSuccess(Boolean serverJoinSuccess) {
        this.serverJoinRequestSuccess.postValue(serverJoinSuccess);
    }


    /**
     * sends ServerJoinRequest
     * @param connected
     */
    public void setConnected(Boolean connected){
        connector.sendMessageToServer(RequestBuilder.serverJoinRequest(clientName, clientType));
    }

    //data for LobbyView
    private MutableLiveData<Collection<Game>> gamesOnServer = new MutableLiveData<>();

    /**
     * returns the games that currently run on the server
     * @return gamesOnSever
     */
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

    /**
     * Returns the players
     * @return players
     */
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
     * Constructor for the Model.
     * Instantiates the ClientConnectorAndroid
     */
    public Model() {
        connector = ClientApplication.create(AndroidReader.class);
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
        this.connector.connect(ipAddress, port, ()->this.setConnected(true),()-> this.setConnectionTookTooLong(true));
    }

    /**
     * sets the clientId for the player
     * @param clientId
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * sets games on server and sorts the games by name
     * @param gamesOnServer
     */
    public void setGamesOnServer(Collection<Game> gamesOnServer) {
        gamesOnServer = this.sortGamesOnServer(gamesOnServer);
        this.gamesOnServer.postValue(gamesOnServer);
    }

    /**
     * this method sorts the games on the server by name using the SortLobbyGamesComparator class
     * @author Fynn Ruppel
     * @param gamesOnServer collection of the current games on the server
     * @return sorted collection with the games
     */
    private Collection<Game> sortGamesOnServer(Collection<Game> gamesOnServer) {
        try {
            List<Game> sortedGames = Lists.newArrayList(gamesOnServer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sortedGames.sort(Comparator.comparing(Game::getState).thenComparing(Game::getName));
            }else {
                Iterator<Game> games = sortedGames.iterator();
                List<Game> finished = Lists.newArrayList();
                List<Game> inProgress = Lists.newArrayList();
                while (games.hasNext()){
                    Game game = games.next();
                    switch (game.getState()){
                        case LOBBY:
                            break;
                        case PAUSED:
                        case IN_PROGRESS:
                            inProgress.add(game);
                            sortedGames.remove(game);
                            break;
                        case FINISHED:
                            finished.add(game);
                            sortedGames.remove(game);
                            break;
                    }
                }
                Collections.sort(sortedGames, this::sortGameByName);
                Collections.sort(finished, this::sortGameByName);
                Collections.sort(inProgress, this::sortGameByName);
                sortedGames.addAll(inProgress);
                sortedGames.addAll(finished);
            }
            gamesOnServer = sortedGames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gamesOnServer;
    }

    private int sortGameByName(Game first, Game second){
        return first.getName().compareTo(second.getName());
    }

    /**
     * Sends a SpectatorGameStateRequest to the connected Server
     */
    public void sendSpectatorGameStateRequest(){
        connector.sendMessageToServer(RequestBuilder.spectatorGameStateRequest());
    }

    /**
     * sends a leave request to the server to leave current game
     */
    public void sendGameLeaveRequest() {
        connector.sendMessageToServer(RequestBuilder.gameLeaveRequest());
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
        if(oldShots != null)
            newShots.addAll(oldShots);
        this.shots.setValue(newShots);
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

    /**
     * Sets the boolean goToGameView to the value of state
     * @param state
     */
    public void setGoToGameView(Boolean state){
        this.goToGameView.setValue(state);
    }

    /**
     * Sets goToGameEnd to true if it was null or false before
     */
    public void goToGameEnd(){
        if(this.goToGameEnd.getValue()==null || !this.goToGameEnd.getValue()) {//TODO could be unneccecary because its set to false every time the navigation  was completed
            this.goToGameEnd.setValue(true);
        }
    }

    /**
     * sets the boolean goTheGameEnd to the value of status
     * @param status
     */
    public void setGoToGameEnd(Boolean status) {
        this.goToGameEnd.setValue(status);
    }


    /**
     * sets the boolean newRound to the value og newState
     * @param newState
     */
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
     * return a 2 dimensional String array which contains the players and their points in descending order
     * @return sortedPoints a 2 dimensional String array which contains the players and their points in descending order
     */
    public String[][] getAllPlayerNamesAndPoints() {
        int playersConnected = players.getValue().size();
        String[][] sortedPoints = new String[playersConnected][2];

        Map<Integer,Integer> localPointsOfPlayers= new HashMap(pointsOfPlayers.getValue());

        for(int i =0 ; i<playersConnected; i++) {
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

    /**
     * sets points of players and sets gotToGameEnd on true if FinishNotification is recieved
     * @param message
     * @param clientId
     */
    @Override
    public void onFinishNotification(FinishNotification message, int clientId) {
        this.setPointsOfPlayers(message.getPoints());
        this.goToGameEnd();
    }

    /**
     *
     * sets players and game configuration if GameInitNotification is received
     * @param message
     * @param clientId
     */
    @Override
    public void onGameInitNotification(GameInitNotification message, int clientId) {
        this.setPlayers(message.getClientList());
        this.setGameConfig(message.getConfiguration());
    }

    /**
     *
     * @param message
     * @param clientId
     */
    @Override
    public void onGameJoinSpectatorResponse(GameJoinSpectatorResponse message, int clientId) {
        this.setJoinedGameWithId(message.getGameId());
        this.sendSpectatorGameStateRequest();
    }

    /**
     * sends SpectatorGameStateRequest if GameStartNotification is received
     * @param message
     * @param clientId
     */
    @Override
    public void onGameStartNotification(GameStartNotification message, int clientId) {
        this.sendSpectatorGameStateRequest();
    }

    /**
     * sets games on server if LobbyResponse is received
     * @param message
     * @param clientId
     */
    @Override
    public void onLobbyResponse(LobbyResponse message, int clientId) {
        this.setGamesOnServer(message.getGames());
    }

    /**
     * Sets points of a player if PointsResponse is received
     * @param message
     * @param clientId
     */
    @Override
    public void onPointsResponse(PointsResponse message, int clientId) {
        this.setPointsOfPlayers(message.getPoints());
    }

    /**
     * sets newRound to true if roundStartNotification is received
     * @param message
     * @param clientId
     */
    @Override
    public void onRoundStartNotification(RoundStartNotification message, int clientId) {
        this.setNewRound(true);
    }

    /**
     * sets serverJoinRequestSuccess to true clienId if serverJoinResponse is received
     * @param message
     * @param clientId
     */
    @Override
    public void onServerJoinResponse(ServerJoinResponse message, int clientId) {
        this.setClientId(message.getClientId());
        this.setServerJoinRequestSuccess(true);
    }

    /**
     * sets goToSpectatorWaiting to true if SpectatorFameStateResponse is received and ships are not placed yet,
     * else sets players, shots and ships and set gotToGameView to true
     * @param message
     * @param clientId
     */
    @Override
    public void onSpectatorGameStateResponse(SpectatorGameStateResponse message, int clientId) {
        if(message.getShips().size() == 0){
            this.goToSpectatorWaiting.setValue(true);
        }
        else {
            this.setPlayers(message.getPlayers());
            this.setShots(message.getShots());
            this.setShips(message.getShips());
            this.goToGameView();
        }
    }

    /**
     * adds all successfull and unsuccessful new shots and updates points if a SpectatorUpdateNotification is received
     * @param message
     * @param clientId
     */
    @Override
    public void onSpectatorUpdateNotification(SpectatorUpdateNotification message, int clientId) {
        Collection<Shot> shots =  message.getHits();
        shots.addAll(message.getMissed());
        this.addShots(shots);
        this.updatePoints(message.getPoints());
    }

    /**
     * returns the connector of a client
     * @return connector
     */
    public ClientConnector getClientConnector() {
        return this.connector;
    }
}
