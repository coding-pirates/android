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
        //TODO continue game
    }

    @Override
    public void handleConnectionClosedReport(ConnectionClosedReport message) {
        //TODO show Connection Lost error
    }

    @Override
    public void handleErrorNotification(ErrorNotification message) {
        //TODO show Error message
    }

    @Override
    public void handleFinishNotification(FinishNotification message) {
        Model.getInstance().setPointsOfPlayers(message.getPoints());
        //Model.getInstance().setWinner(message.getWinner());
    }

    @Override
    public void handleGameJoinPlayer(GameJoinPlayerResponse message) {

    }

    @Override
    public void handleGameJoinSpectator(GameJoinSpectatorResponse message) {
        //TODO Game joined. Goto next page
    }

    @Override
    public void handleGameStartNotification(GameStartNotification message) {

    }

    @Override
    public void handleLeaveNotification(LeaveNotification message) {
        //TODO delete Player from player list
    }

    @Override
    public void handleLobbyResponse(LobbyResponse message) {
        Model.getInstance().setGamesOnServer(message.getGames());
    }

    @Override
    public void handlePauseNotification(PauseNotification message) {
        //TODO pause game
    }

    @Override
    public void handlePlaceShipsResponse(PlaceShipsResponse message) {

    }

    @Override
    public void handlePlayerUpdateNotification(PlayerUpdateNotification message) {

    }

    @Override
    public void handleSpectatorUpdateNotification(SpectatorUpdateNotification message) {
        //TODO update Points
        //TODO update shots
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
