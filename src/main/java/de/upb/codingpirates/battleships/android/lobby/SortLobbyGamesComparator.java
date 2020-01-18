package de.upb.codingpirates.battleships.android.lobby;

import java.util.Comparator;

import de.upb.codingpirates.battleships.logic.Game;

/**
 * this comparatorClass is used for comparing the name of the LobbyGames
 */
public class SortLobbyGamesComparator implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
