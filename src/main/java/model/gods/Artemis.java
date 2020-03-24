package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.DoubleNoBackMove;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Artemis extends God {

    public Artemis(){
        super("Artemis", new StandardWinCondition(), new NoStartTurn(), new DoubleNoBackMove(new StandardMove()), new StandardBuild());
    }

}
