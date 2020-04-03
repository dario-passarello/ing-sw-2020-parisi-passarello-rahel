package model;

import model.gods.God;

import java.util.List;

public class Builder {
    private Square position;
    private Player owner;

    public Builder(Square position, Player owner) {
        this.position = position;
        this.owner = owner;
    }

    /**
     * @return the reference of the square where the player is located
     */
    public Square getPosition() {
        return position; //TODO
    }

    /**
     * @return the reference of the player who control this builder
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return a list containing the references to the squares where
     *         the builder from his position could be move according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getWalkableNeighborhood() {
        return null; //TODO

    }

    /**
     * @return a list containing the references to the squares where
     *         the builder could build from his position according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getBuildableNeighborhood() {
        return null; //TODO
    }

    /**
     * Moves the player
     * @param sq The square where the builder will be moved
     * @return true if the builder could be moved again after this move
     *          because of his power, false if his move phase is terminated
     *          or the builder move was forced
     */
    public boolean move(Square sq) {
        God playerGod = owner.getGod();
        return playerGod.move(this,sq);
    }


    /**
     * Builds in the square passed as parameter
     * @param sq The square where the builder will build a structure
     * @return true if the builder could build again after this move
     *          because of his power, false if his build phase is terminated
     *
     */
    public boolean build(Square sq) {
        God playerGod = owner.getGod();
        return playerGod.build(this,sq);
    }

}
