package model.movebehaviors;

import model.Board;
import model.Builder;
import model.ErrorMessage;
import model.Square;
import utils.Coordinate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//TODO
public class OpponentPushMove extends MoveDecorator {

    public OpponentPushMove(MoveBehavior moveBehavior) {
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood + special neighborhood
     * (we also consider reachable squares occupied by an opponent builder)
     */
    public Set<Square> neighborhood(Square src) {
        Set<Square> adjacent = src.getNeighbors();
        Set<Square> neighborhood = new HashSet<>();

        Builder currBuilder = src.getOccupant().orElseThrow(() -> new UnknownError(ErrorMessage.MALFORMED_BOARD));
        /*
         *  Check for each neighbor if
         *  - There is a builder controlled from another player
         *  - The builder could be pushed:
         *      - Is not in an edge
         *      - The square's height is at most 1 level higher compared to the neighbor
         */
        for(Square neighbor : adjacent){
            Optional<Builder> builderOpt = neighbor.getOccupant();
            if(!builderOpt.isPresent()) continue;
            if(neighbor.getBuildLevel() - src.getBuildLevel() <= 1
                    && !builderOpt.get().getOwner().equals(currBuilder.getOwner())) {
                Optional<Coordinate> optPushCoordinate = getPushDestination(src, neighbor);
                if(!optPushCoordinate.isPresent()) continue;
                Square pushSquare = neighbor.getBoard().squareAt(optPushCoordinate.get());
                if (pushSquare.getBuildLevel() - neighbor.getBuildLevel() <= 1 && !pushSquare.getOccupant().isPresent() && !pushSquare.isDomed())
                    neighborhood.add(neighbor);
                }
            }
        neighborhood.addAll(wrappedMoveBehavior.neighborhood(src));
        return neighborhood;
        }



    /**
     * @param b    is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     * <p>
     * if b goes to a square occupied by an opponent builder, he will push him in the same direction
     */
    public boolean move(Builder b, Square dest) {

        if (dest.getOccupant().isPresent()) {
            Square src = b.getPosition();                       //starting position

            Board board = dest.getBoard();

            Square push = board.squareAt(getPushDestination(src, dest)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ILLEGAL_MOVE)));
            Builder enemy = dest.getOccupant().get();

            push.setOccupant(dest.getOccupant().get());
            enemy.setPosition(push);
            dest.setOccupant(b);
            b.setPosition(dest);
            src.setEmptySquare();

            return false; //Override the standard behavior
        } else {
            return wrappedMoveBehavior.move(b, dest);
        }
    }

    private Optional<Coordinate> getPushDestination(Square src, Square neighbor) {
        int srcX = src.getCoordinate().getX();
        int srcY = src.getCoordinate().getY();
        int squareX = neighbor.getCoordinate().getX();
        int squareY = neighbor.getCoordinate().getY();
        int dirX = squareX - srcX;
        int dirY = squareY - srcY;
        Coordinate result = new Coordinate(dirX+squareX, dirY+squareY);
        return Board.checkValidCoordinate(result) ? Optional.of(result) : Optional.empty();
    }

}
