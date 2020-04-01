package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.List;
import java.util.Set;


//TODO
public class StandardMove implements MoveBehavior {

    public StandardMove(){}


    /**
     * @param src is the starting point of a builder
     * @return the set of square of a standard neighborhood
     * (squares adjacent to src, not occupied and a level <= level of src + 1
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     */
    public boolean move(Builder b, Square dest) {
    }

}
