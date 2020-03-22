package model.movebehaviors;

import model.Square;

import java.util.List;

public class UnlimitedPerimetralMove implements MoveDecorator {

    public UnlimitedPerimetralMove(MoveBehavior moveBehavior){
        setWrappedBehavior(moveBehavior);
    }

    public List<Square> neighborhood(Square src) {
        return null;
    }

    public void move(Square dest) {

    }

    public boolean endMove() {
        return false;
    }
}
