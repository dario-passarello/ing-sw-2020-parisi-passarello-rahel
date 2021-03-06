package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import model.gods.God;

import java.util.Optional;

/**
 * It's a Win Condition decorator that allows to win when there are five complete towers (3 build level + dome) on the board
 */
public class FiveTowerWinCondition extends WinConditionDecorator {

    private God chronus;

    /**
     * The constructor method. It decorates the parameter with this class
     * @param winCondition The Win Condition target
     * @param chronus The player who owns Chronus as a god
     */
    public FiveTowerWinCondition(WinCondition winCondition, God chronus){
        wrappedWinCondition = winCondition;
        this.chronus = chronus;
    }


    @Override
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        return wrappedWinCondition.checkWinCondition(start, builder);
    }

    @Override
    public Optional<Player> checkSpecialWinCondition(Board board) {
        int counter = 0;
        for(int i = 0; i <= Board.BOARD_SIZE-1; i++){
            for(int j = 0; j <= Board.BOARD_SIZE-1; j++){
                if(board.squareAt(i,j).getBuildLevel() == 3 && board.squareAt(i,j).isDomed()){
                    counter++;
                }
            }
        }
        if(counter >= 5){
            return Optional.of(chronus.getPlayer());
        }
        else {
            return Optional.empty();
        }

        //To wrap
    }
}
