package model;

import model.gods.God;
import utils.Observer;
import java.util.List;

/**
 * This interface defines the methods implemented from all views to states change in the Game model
 */
public interface GameObserver extends Observer {
    /**
     * Notifies the GameObservers of the change of the GameState, send the state to the observers
     * @param state A Game.State enum type, identifies the new state of the Game
     * @param activePlayerName Contains the active player name, this player will be the next player to do
     *                         an action
     */
    void receiveGameState(Game.State state, Player activePlayerName);

    /**
     * Notifies all players of the updated player list status, containing also information about Gods and Builder positions
     * @param list A map with keys all player names and values the name of the god pick from other players,
     *            if the player has not picked a god the value will be null
     */
    void receivePlayerList(List<Player> list);

    /**
     *  Notifies all player of the list of all gods
     * @param gods A list of all gods available in this game
     */
    void receiveAvailableGodList(List<God> gods);

    /**
     * Receive the current status of the board
     * @param board The board object
     */
    void receiveBoard(Board board);

    /**
     * Notifies all observer that the update is done, and they could safely display the content
     */
    void receiveUpdateDone();

    void receiveDisconnect();

}
