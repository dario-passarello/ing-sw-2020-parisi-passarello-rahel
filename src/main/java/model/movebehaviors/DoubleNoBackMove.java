package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;


/**
 * It's a Move Behavior decorator that adds the possibility to move twice
 * (the second move can't be to the initial square)
 */
public class DoubleNoBackMove extends MoveDecorator {

    Square previous;

    /**
     * The constructor method. It decorates the parameter with this class
     * @param moveBehavior The Move Behavior target
     */
    public DoubleNoBackMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
        previous = null;
    }

    /**
     * @param src is the starting point of a builder
     * @return the special neighborhood
     * (if this is the first move, we will have just a standard one,
     * if this is the second move we cannot go in the starting square of the first move)
     */
    public Set<Square> neighborhood(Square src) {

        Set<Square> neighborhood = wrappedMoveBehavior.neighborhood(src);
        if (previous != null) {                                                    // second move --> remove previous
            neighborhood.remove(previous);
        }
        return neighborhood;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return true in the first move, false in the second one
     */
    public boolean move(Builder b, Square dest) {
        Square save = b.getSquare();
        wrappedMoveBehavior.move(b, dest);
        if(previous == null){
            previous = save;
            return true;
        } else {
            previous = null;
            return false;
        }
    }

    public MoveBehavior copyBehavior(){
        return new DoubleNoBackMove(wrappedMoveBehavior.copyBehavior());
    }

    public void reset(){
        previous = null;
        wrappedMoveBehavior.reset();
    }
}

