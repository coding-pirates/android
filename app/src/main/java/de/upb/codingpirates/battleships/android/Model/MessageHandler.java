package de.upb.codingpirates.battleships.android.Model;

import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.network.message.notification.*;
import de.upb.codingpirates.battleships.network.message.report.ConnectionClosedReport;
import de.upb.codingpirates.battleships.network.message.response.*;

public class MessageHandler implements Handler {


    @Override
    public void handleGameInitNotification(GameInitNotification message) {
        Model.getInstance().setPlayers(message.getClientList());
        Model.getInstance().setGameConfig(message.getConfiguration());
    }

    @Override
    public void handleContinueNotification(ContinueNotification message) {
        Model.getInstance().setContinued();
    }

    @Override
    public void handleConnectionClosedReport(ConnectionClosedReport message) {
        //TODO show Connection Lost error
        //TODO set Connection handler to unconnected
    }

    @Override
    public void handleErrorNotification(ErrorNotification message) {
        //TODO show Error message
    }

    @Override
    public void handleFinishNotification(FinishNotification message) {
        Model.getInstance().setPointsOfPlayers(message.getPoints());
        //Model.getInstance().setWinner(message.getWinner()); //TODO
    }

    @Override
    public void handleGameJoinPlayer(GameJoinPlayerResponse message) {

    }

    @Override
    public void handleGameJoinSpectator(GameJoinSpectatorResponse message) {
        Model.getInstance().handlegameJoinSpectatorResponse(message.getGameId());
    }

    @Override
    public void handleGameStartNotification(GameStartNotification message) {
        System.out.println("GameStart received");
        Model.getInstance().setGameStart();
        Model.getInstance().sendSpectatorGameStateRequest();
    }

    @Override
    public void handleLeaveNotification(LeaveNotification message) {
       Model.getInstance().removePlayer(message.getPlayerId());
    }

    @Override
    public void handleLobbyResponse(LobbyResponse message) {
        Model.getInstance().setGamesOnServer(message.getGames());
        //TODO NAvigate to LobbyView
    }

    @Override
    public void handlePauseNotification(PauseNotification message) {
        Model.getInstance().setPaused();
    }

    @Override
    public void handlePlaceShipsResponse(PlaceShipsResponse message) {

    }

    @Override
    public void handlePlayerUpdateNotification(PlayerUpdateNotification message) {

    }

    @Override
    public void handleSpectatorUpdateNotification(SpectatorUpdateNotification message) {
        Model model  = Model.getInstance();
        model.addShots(message.getHits());
        model.updatePoints(message.getPoints());
        model.addShots(message.getMissed());
        //TODO sunk points update
    }

    @Override
    public void handlePointsResponse(PointsResponse message) {
        Model.getInstance().setPointsOfPlayers(message.getPoints());
    }

    @Override
    public void handleRemainingTimeResponse(RemainingTimeResponse message) {
        //TODO remaining time for specator???
    }

    @Override
    public void handleRoundStartNotification(RoundStartNotification message) {

    }

    @Override
    public void handleServerJoinResponse(ServerJoinResponse message) {
        Model.getInstance().setClientId(message.getClientId());
        Model.getInstance().setServerJoinRequestSuccess(true);
    }

    @Override
    public void handleShotsResponse(ShotsResponse message) {

    }

    @Override
    public void handleSpectatorGameStateResponse(SpectatorGameStateResponse message) {
        Model.getInstance().setPlayers(message.getPlayers());
        Model.getInstance().setShots(message.getShots());
        Model.getInstance().setShips(message.getShips());
        Model.getInstance().setState(message.getState());
    }

    @Override
    public void handlePlayerGameStateResponse(PlayerGameStateResponse message) {

    }
}
