/*
package de.upb.codingpirates.battleships.android.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

import de.upb.codingpirates.battleships.android.model.Model;
import de.upb.codingpirates.battleships.client.Handler;
import de.upb.codingpirates.battleships.logic.Shot;
import de.upb.codingpirates.battleships.network.exceptions.BattleshipException;
import de.upb.codingpirates.battleships.network.message.notification.*;
import de.upb.codingpirates.battleships.network.message.report.ConnectionClosedReport;
import de.upb.codingpirates.battleships.network.message.response.*;

public class MessageHandler implements Handler {
    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void handleContinueNotification(ContinueNotification message, int clientId) {
       // Model.getInstance().setContinued(); //TODO
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
    public void handleGameJoinPlayer(GameJoinPlayerResponse message, int clientId) {

    }

    @Override
    public void handleLeaveNotification(LeaveNotification message, int clientId) {
      // Model.getInstance().removePlayer(message.getPlayerId());
    }

    @Override
    public void handlePauseNotification(PauseNotification message, int clientId) {
       // Model.getInstance().setPaused();
    }

    @Override
    public void handlePlaceShipsResponse(PlaceShipsResponse message, int clientId) {

    }

    @Override
    public void handlePlayerUpdateNotification(PlayerUpdateNotification message, int clientId) {

    }

    @Override
    public void handleRemainingTimeResponse(RemainingTimeResponse message, int clientId) {
        //TODO remaining time for specator???
    }

    @Override
    public void handleShotsResponse(ShotsResponse message, int clientId) {

    }

    @Override
    public void handlePlayerGameStateResponse(PlayerGameStateResponse message, int clientId) {

    }

    @Override
    public void handleBattleshipException(BattleshipException exception, int clientId) {

    }
}
*/
