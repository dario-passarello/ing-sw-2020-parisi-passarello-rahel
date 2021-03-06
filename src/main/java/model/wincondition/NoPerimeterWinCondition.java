package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

/**
 * It's a Win Condition decorator that not allows to win with a builder on a perimeter square
 */
public class NoPerimeterWinCondition extends WinConditionDecorator {

    /**
     * The constructor method. It decorates the parameter with this class
     * @param winCondition The Win Condition target
     */
    public NoPerimeterWinCondition(WinCondition winCondition){
        wrappedWinCondition = winCondition;
    }

    @Override
    public Optional<Player> checkWinCondition(Square start, Builder builder){
        if(builder.getSquare().isPerimetral()){
            return Optional.empty();
        } else {
            return wrappedWinCondition.checkWinCondition(start, builder);
        }
    }

    @Override
    public Optional<Player> checkSpecialWinCondition(Board board) {
        return wrappedWinCondition.checkSpecialWinCondition(board);
    }
}
