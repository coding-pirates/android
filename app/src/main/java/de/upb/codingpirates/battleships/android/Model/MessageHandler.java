package de.upb.codingpirates.battleships.android.Model;

import java.util.logging.Logger;

import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.network.exceptions.BattleshipException;
import de.upb.codingpirates.battleships.network.message.notification.*;
import de.upb.codingpirates.battleships.network.message.report.ConnectionClosedReport;
import de.upb.codingpirates.battleships.network.message.response.*;

public class MessageHandler implements Handler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());


    @Override
    public void handleGameInitNotification(GameInitNotification message, int clientId) {
        Model.getInstance().setPlayers(message.getClientList());
        Model.getInstance().setGameConfig(message.getConfiguration());
    }

    @Override
    public void handleContinueNotification(ContinueNotification message, int clientId) {
        Model.getInstance().setContinued();
    }

    @Override
    public void handleConnectionClosedReport(ConnectionClosedReport message, int clientId) {
        //TODO show Connection Lost error
        //TODO set Connection handler to unconnected
    }

    @Override
    public void handleErrorNotification(ErrorNotification message, int clientId) {
        //TODO show Error message
    }

    @Override
    public void handleFinishNotification(FinishNotification message, int clientId) {
        Model.getInstance().setPointsOfPlayers(message.getPoints());
    }

    @Override
    public void handleGameJoinPlayer(GameJoinPlayerResponse message, int clientId) {

    }

    @Override
    public void handleGameJoinSpectator(GameJoinSpectatorResponse message, int clientId) {
        Model.getInstance().handlegameJoinSpectatorResponse(message.getGameId());
    }

    @Override
    public void handleGameStartNotification(GameStartNotification message, int clientId) {
        System.out.println("GameStart received");
        Model.getInstance().sendSpectatorGameStateRequest();
    }

    @Override
    public void handleLeaveNotification(LeaveNotification message, int clientId) {
       Model.getInstance().removePlayer(message.getPlayerId());
    }

    @Override
    public void handleLobbyResponse(LobbyResponse message, int clientId) {
        Model.getInstance().setGamesOnServer(message.getGames());
        //TODO NAvigate to LobbyView
    }

    @Override
    public void handlePauseNotification(PauseNotification message, int clientId) {
        Model.getInstance().setPaused();
    }

    @Override
    public void handlePlaceShipsResponse(PlaceShipsResponse message, int clientId) {

    }

    @Override
    public void handlePlayerUpdateNotification(PlayerUpdateNotification message, int clientId) {

    }

    @Override
    public void handleSpectatorUpdateNotification(SpectatorUpdateNotification message, int clientId) {
        Model model  = Model.getInstance();
        model.addShots(message.getHits());
        model.updatePoints(message.getPoints());
        model.addShots(message.getMissed());
        //TODO sunk points update
    }

    @Override
    public void handlePointsResponse(PointsResponse message, int clientId) {
        Model.getInstance().setPointsOfPlayers(message.getPoints());
    }

    @Override
    public void handleRemainingTimeResponse(RemainingTimeResponse message, int clientId) {
        //TODO remaining time for specator???
    }

    @Override
    public void handleRoundStartNotification(RoundStartNotification message, int clientId) {

    }

    @Override
    public void handleServerJoinResponse(ServerJoinResponse message, int clientId) {
        Model.getInstance().setClientId(message.getClientId());
        Model.getInstance().setServerJoinRequestSuccess(true);
    }

    @Override
    public void handleShotsResponse(ShotsResponse message, int clientId) {

    }

    @Override
    public void handleSpectatorGameStateResponse(SpectatorGameStateResponse message, int clientId) {
        Model.getInstance().setPlayers(message.getPlayers());
        Model.getInstance().setShots(message.getShots());
        Model.getInstance().setShips(message.getShips());
        Model.getInstance().setState(message.getState());
        Model.getInstance().goToGameView();
    }

    @Override
    public void handlePlayerGameStateResponse(PlayerGameStateResponse message, int clientId) {

    }

    @Override
    public void handleBattleshipException(BattleshipException exception, int clientId) {

    }
}
