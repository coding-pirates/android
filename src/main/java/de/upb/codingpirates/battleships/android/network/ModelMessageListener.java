package de.upb.codingpirates.battleships.android.network;

import de.upb.codingpirates.battleships.client.listener.*;

public interface ModelMessageListener extends GameInitNotificationListener, FinishNotificationListener, GameJoinSpectatorResponseListener, GameStartNotificationListener, LobbyResponseListener, SpectatorUpdateNotificationListener, PointsResponseListener, RoundStartNotificationListener, ServerJoinResponseListener, SpectatorGameStateResponseListener {}